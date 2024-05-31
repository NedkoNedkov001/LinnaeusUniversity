import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class TFTPServer {
    public static final int TFTPPORT = 69;
    public static final int BUFSIZE = 516;
    public static final String READDIR = "./resources/read/";
    public static final String WRITEDIR = "./resources/write/";
    // OP codes
    public static final int OP_RRQ = 1;
    public static final int OP_WRQ = 2;
    public static final int OP_DAT = 3;
    public static final int OP_ACK = 4;
    public static final int OP_ERR = 5;

    // Added by me
    private static final int ACK_PACKET_SIZE = 4;
    private static final int RRQ_WRQ_HEADER_SIZE = 4;
    private static final int RRQ_WRQ_DATA_SIZE = 512;

    private static final int ERR_NOT_DEFINED = 0;
    private static final int ERR_FILE_NOT_FOUND = 1;
    private static final int ERR_ACCESS_VIOLATION = 2;
    private static final int ERR_FILE_EXISTS = 6;

    private static final int FILE_NAME_BEGIN_INDEX = 2;
    private static final int ERR_MESSAGE_BEGIN_INDEX = 4;
    private static final int DAT_DATA_BEGIN_INDEX = 4;

    public static void main(String[] args) {
        if (args.length > 0) {
            System.err.printf("[ERR] usage: java %s\n", TFTPServer.class.getCanonicalName());
            System.exit(1);
        }
        // Starting the server
        try {
            TFTPServer server = new TFTPServer();
            server.start();
        } catch (SocketException e) {
            System.err.println("[ERR] Port already in use");
        }
    }

    private void start() throws SocketException {
        byte[] buf = new byte[BUFSIZE];

        // Create socket
        DatagramSocket socket = new DatagramSocket(null);

        // Create local bind point
        SocketAddress localBindPoint = new InetSocketAddress(TFTPPORT);
        socket.bind(localBindPoint);

        System.out.printf("[INFO] Listening at port %d for new requests\n", TFTPPORT);

        // Loop to handle client requests
        while (true) {
            final InetSocketAddress clientAddress = receiveFrom(socket, buf);

            // If clientAddress is null, an error occurred in receiveFrom()
            if (clientAddress == null)
                continue;

            final StringBuffer requestedFile = new StringBuffer();
            final int reqtype = ParseRQ(buf, requestedFile);

            new Thread() {
                public void run() {
                    try {
                        DatagramSocket sendSocket = new DatagramSocket(0);

                        // Connect to client
                        sendSocket.connect(clientAddress);

                        System.out.printf("%s request for %s from %s using port %d\n",
                                (reqtype == OP_RRQ) ? "Read" : "Write", requestedFile.toString(),
                                clientAddress.getHostName(), clientAddress.getPort());

                        // Read request
                        if (reqtype == OP_RRQ) {
                            requestedFile.insert(0, READDIR);
                            HandleRQ(sendSocket, requestedFile.toString(), OP_RRQ);
                        }
                        // Write request
                        else {
                            requestedFile.insert(0, WRITEDIR);
                            HandleRQ(sendSocket, requestedFile.toString(), OP_WRQ);
                        }
                        sendSocket.close();
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    /**
     * Reads the first block of data, i.e., the request for an action (read or
     * write).
     * 
     * @param socket (socket to read from)
     * @param buf    (where to store the read data)
     * @return socketAddress (the socket address of the client)
     */
    private InetSocketAddress receiveFrom(DatagramSocket socket, byte[] buf) {

        // Create datagram packet
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);

        // Receive packet
        try {
            socket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get client address and port from the packet
        InetSocketAddress socketAddress = new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort());

        return socketAddress;
    }

    /**
     * Parses the request in buf to retrieve the type of request and requestedFile
     * 
     * @param buf           (received request)
     * @param requestedFile (name of file to read/write)
     * @return opcode (request type: RRQ or WRQ)
     */
    private int ParseRQ(byte[] buf, StringBuffer requestedFile) {
        // See "TFTP Formats" in TFTP specification for the RRQ/WRQ request contents

        ByteBuffer wrap = ByteBuffer.wrap(buf);
        short opcode = wrap.getShort();

        String fileName = readFileName(buf);
        requestedFile.append(fileName);

        String mode = readMode(buf);

        if (!isValidMode(mode)) {
            System.err.println("[ERR] Incorrect mode");
            System.exit(1);
            opcode = 0;
        }
        return opcode;
    }

    /**
     * Checks if the mode is set to octet
     * 
     * @param mode (reading mode)
     * @return
     */
    private boolean isValidMode(String mode) {
        return mode.equals("octet");
    }

    /**
     * Translates bytes to string from the request in order to get the mode
     * 
     * @param buf (the request)
     * @return
     */
    private String readMode(byte[] buf) {
        String mode = "incorrect";
        final int modeBeginIndex = getModeBeginIndex(buf);
        for (int i = modeBeginIndex; i < buf.length; i++) { // Search for mode
            if (buf[i] == 0) {
                mode = new String(buf, modeBeginIndex, i - (modeBeginIndex)).toLowerCase();
                return mode;
            }
        }
        System.err.println("[ERR] Could not read mode from request");
        System.exit(1);
        return mode;
    }

    private int getModeBeginIndex(byte[] buf) {

        return getFileNameEndIndex(buf) + 1;
    }

    private int getFileNameEndIndex(byte[] buf) {
        int fileNameEndIndex = -1;
        for (int i = FILE_NAME_BEGIN_INDEX; i < buf.length; i++) { // Search for file name
            if (buf[i] == 0) {
                fileNameEndIndex = i;
                break;
            }
        }
        return fileNameEndIndex;
    }

    private String readFileName(byte[] buf) {
        int fileNameEndIndex = getFileNameEndIndex(buf);

        if (fileNameEndIndex < 0) {
            System.err.println("[ERR] Could not read file name from request");
            System.exit(1);
        }

        String fileName = new String(buf, FILE_NAME_BEGIN_INDEX, fileNameEndIndex - FILE_NAME_BEGIN_INDEX); // Starts
                                                                                                            // from
        // begin, takes
        // end-begin chars

        return fileName;
    }

    /**
     * Handles RRQ and WRQ requests
     * 
     * @param sendSocket    (socket used to send/receive packets)
     * @param requestedFile (name of file to read/write)
     * @param opcode        (RRQ or WRQ)
     */
    private void HandleRQ(DatagramSocket sendSocket, String requestedFile, int opcode) // Partly given method
    {
        byte[] dataBuff = new byte[BUFSIZE - 4];

        File file = new File(requestedFile);

        if (opcode == OP_RRQ) {
            // See "TFTP Formats" in TFTP specification for the DATA and ACK packet contents

            FileInputStream fileInputStream = getFileInputStream(sendSocket, file);

            if (fileInputStream == null) {
                return;
            }

            int blockNum = 1;

            while (true) {
                int length;
                try {
                    length = fileInputStream.read(dataBuff);
                } catch (IOException e) {
                    System.err.println("[ERR] Error reading file");
                    send_ERR(sendSocket, ERR_NOT_DEFINED, "Error reading file");
                    return;
                }

                if (length == -1) { // data.put() takes non-negative number for length
                    length = 0;
                }

                DatagramPacket sendPacket = getDataDatagramPacket(dataBuff, blockNum, length);

                boolean result = send_DATA_receive_ACK(sendSocket, sendPacket, blockNum);
                if (result) {
                    System.out.println("[SUCCESS] Successfully sent block #" + blockNum);
                    blockNum++;
                } else {
                    System.err.println("[ERR] No ACK received");
                    send_ERR(sendSocket, ERR_NOT_DEFINED, "No ACK received");
                    return;
                }

                if (tryCloseInputStream(fileInputStream, length)) {
                    break;
                }
            }

        } else if (opcode == OP_WRQ) {
            if (!validateFileDoesNotExist(sendSocket, file)) {
                return;
            }

            FileOutputStream fileOutputStream = getFileOutputStream(sendSocket, file);
            if (fileOutputStream == null) {
                return;
            }

            int ackNum = 0;

            while (true) {
                DatagramPacket sendPacket = getAckDatagramPacket(ackNum);

                boolean result = receive_DATA_send_ACK(sendSocket, sendPacket, fileOutputStream, file);

                if (result) {
                    System.out.println("[SUCCESS] Successfully received block #" + ++ackNum);
                } else {
                    return;
                }
            }
        } else {
            System.err.println("[ERR] Invalid request");
            // See "TFTP Formats" in TFTP specification for the ERROR packet contents
            send_ERR(sendSocket, ERR_NOT_DEFINED, "Invalid request");
            return;
        }
    }

    private FileOutputStream getFileOutputStream(DatagramSocket sendSocket, File file) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            System.err.println("[ERR] Access violation");
            send_ERR(sendSocket, ERR_ACCESS_VIOLATION, " Access violation");
        }
        return fileOutputStream;

    }

    private boolean validateFileDoesNotExist(DatagramSocket sendSocket, File file) {
        if (file.exists()) {
            System.out.println("[ERR] File already exists");
            send_ERR(sendSocket, ERR_FILE_EXISTS, "File already exists");
            return false;
        }
        return true;
    }

    private boolean tryCloseInputStream(FileInputStream fileInputStream, int length) {
        if (length < RRQ_WRQ_DATA_SIZE) {
            try {
                fileInputStream.close();
                return true;
            } catch (IOException e) {
                System.err.println("[ERR] Could not close file");
            }
        }
        return false;
    }

    private FileInputStream getFileInputStream(DatagramSocket sendSocket, File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.err.println("[ERR] File not found");
            send_ERR(sendSocket, ERR_FILE_NOT_FOUND, "File not found");
            return null;
        }
    }

    private boolean send_DATA_receive_ACK(DatagramSocket sendSocket, DatagramPacket sendPacket, int blockNum) {
        byte[] receiveBuff = new byte[BUFSIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuff, BUFSIZE);

        int attempts = 0;
        while (attempts++ < 8) {
            try {
                sendSocket.send(sendPacket);
                System.out.println("[DATA] Sent block #" + blockNum);
                sendSocket.setSoTimeout(attempts * 1000);
                sendSocket.receive(receivePacket);

                int ackNum = getAckOrBlockNum(receivePacket);
                if (ackNum == blockNum) { // Packet contains ack for last data
                    System.out.println("[ACK] Received for block #" + blockNum);
                    return true;
                } else if (ackNum == -1) { // Packet is an error, not ack
                    return false;
                } else { // Ack for wrong data
                    System.err.println("[ERR] Wrong acknowledgment #" + ackNum + ". Resending now");
                    send_ERR(sendSocket, ERR_NOT_DEFINED, "Received wrong acknowledgment");
                }
            } catch (IOException e) {
                System.err.println("[ERR] Could not send packet. Resending now");
                send_ERR(sendSocket, ERR_NOT_DEFINED, "Could not send packet");
            }

            try {
                sendSocket.setSoTimeout(0);
            } catch (SocketException e) {
                System.err.println("[ERR] Could not reset timer");
                send_ERR(sendSocket, ERR_NOT_DEFINED, "Could not reset timer");
            }

        }
        System.err.println("[ERR] Too many attempts. Closing connection");
        send_ERR(sendSocket, ERR_NOT_DEFINED, "Too many attempts. Closing connection");
        return false;
    }

    private short getAckOrBlockNum(DatagramPacket receivePacket) {
        ByteBuffer wrap = ByteBuffer.wrap(receivePacket.getData());
        if (wrap.getShort() == OP_ERR) {
            System.err.println("[ERR] Packet is an error");
            printERR(wrap);
            return -1;
        }

        return wrap.getShort();
    }

    private void printERR(ByteBuffer wrap) {
        int errCode = wrap.getShort();

        byte[] buff = wrap.array();
        for (int i = ERR_MESSAGE_BEGIN_INDEX; i < buff.length; i++) {
            if (buff[i] == 0) {
                String msg = new String(buff, ERR_MESSAGE_BEGIN_INDEX, i - ERR_MESSAGE_BEGIN_INDEX);
                if (errCode > 7) {
                    errCode = 0;
                }

                String errMsg = "[ERR] ?: Msg: " + msg;
                switch (errCode) {
                    case 0:
                        errMsg = errMsg.replace("?", "Not Defined");
                        break;
                    case 1:
                        errMsg = errMsg.replace("?", "File Not Found");
                        break;
                    case 2:
                        errMsg = errMsg.replace("?", "Access Violation");
                        break;
                    case 6:
                        errMsg = errMsg.replace("?", "File Already Exists");
                        break;
                    default:
                        errMsg = errMsg.replace("?", "Unimplemented Error");
                        break;
                }
                System.err.println(errMsg);
                break;
            }
        }

    }

    private boolean receive_DATA_send_ACK(DatagramSocket sendSocket, DatagramPacket sendPacket,
            FileOutputStream fileOutputStream, File file) {
        byte[] receiveBuff = new byte[BUFSIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuff, receiveBuff.length);
        int ackNum = getAckOrBlockNum(sendPacket);

        int attempts = 0;
        while (attempts++ < 8) {
            try {
                sendSocket.send(sendPacket); // ACK
                System.out.println("[ACK] Sending for block #" + ackNum);
                sendSocket.setSoTimeout(attempts * 1000);
                sendSocket.receive(receivePacket); // DATA

                int blockNum = getAckOrBlockNum(receivePacket);
                if (ackNum + 1 == blockNum) { // Packet contains ack for last data
                    System.out.println("[DATA] Received block #" + blockNum);
                    byte[] data = receivePacket.getData();
                    fileOutputStream.write(data, RRQ_WRQ_HEADER_SIZE, getDataLength(data));
                    if (getDataLength(data) < RRQ_WRQ_DATA_SIZE) {
                        System.out.println("[SUCCESS] Successfully received last block #" + (ackNum + 1));
                        return false;
                    }

                    return true;
                } else if (blockNum == -1) { // Packet is an error, not data
                    System.err.println("[ERR] No DATA received");
                    send_ERR(sendSocket, ERR_NOT_DEFINED, "No DATA received");
                    file.delete();
                    return false;
                } else { // Ack for wrong data
                    System.err.println("[ERR] Wrong block #" + ackNum + ". Resending now");
                    send_ERR(sendSocket, ERR_NOT_DEFINED, "Received wrong block number");
                }
            } catch (IOException e) {
                System.err.println("[ERR] Could not send ack. Resending now");
                send_ERR(sendSocket, ERR_NOT_DEFINED, "Could not send acknowledgement");
            }
            try {
                sendSocket.setSoTimeout(0);
            } catch (SocketException e) {
                System.err.println("[ERR] Something happened");
                send_ERR(sendSocket, ERR_NOT_DEFINED, "");
            }

        }
        System.err.println("[ERR] Too many attempts. Closing connection");
        send_ERR(sendSocket, ERR_NOT_DEFINED, "Too many attempts. Closing connection");
        file.delete();
        return false;
    }

    private int getDataLength(byte[] data) {
        int length = RRQ_WRQ_DATA_SIZE;
        for (int i = DAT_DATA_BEGIN_INDEX; i < data.length; i++) { // Search for data end index
            if (data[i] == 0) {
                length = i;
                break;
            }
        }
        return length;
    }

    private void send_ERR(DatagramSocket sendSocket, int errCode, String errMsg) {
        DatagramPacket receivePacket = getErrorDatagramPacket(errCode, errMsg);
        try {
            sendSocket.send(receivePacket);
        } catch (IOException e) {
            System.err.println("[ERR] Packet not sent");
            send_ERR(sendSocket, ERR_NOT_DEFINED, "Packet not sent");
        }

    }

    private DatagramPacket getDataDatagramPacket(byte[] dataBuff, int blockNum, int length) {
        ByteBuffer wrap = ByteBuffer.allocate(length + DAT_DATA_BEGIN_INDEX);
        wrap.putShort((short) OP_DAT);
        wrap.putShort((short) blockNum);
        wrap.put(dataBuff, 0, length);
        byte[] array = wrap.array();

        return new DatagramPacket(array, array.length);
    }

    private DatagramPacket getAckDatagramPacket(int blockNum) {
        ByteBuffer wrap = ByteBuffer.allocate(ACK_PACKET_SIZE);
        wrap.putShort((short) OP_ACK);
        wrap.putShort((short) blockNum);

        byte[] bufferArray = wrap.array();
        return new DatagramPacket(bufferArray, bufferArray.length);
    }

    private DatagramPacket getErrorDatagramPacket(int errCode, String errMsg) {
        ByteBuffer wrap = ByteBuffer.allocate(BUFSIZE);
        wrap.putShort((short) OP_ERR);
        wrap.putShort((short) errCode);
        wrap.put(errMsg.getBytes());
        wrap.put((byte) 0);
        byte[] array = wrap.array();
        return new DatagramPacket(array, array.length);
    }

}