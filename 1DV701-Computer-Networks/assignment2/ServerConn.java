import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerConn implements Runnable {
    private String dir;
    private String dirCanPath;
    private Socket socket;
    OutputStream clientOutput;

    ServerConn(String dir, Socket socket) throws IOException {
        this.dir = dir;
        File dirFile = new File(dir);
        dirCanPath = dirFile.getCanonicalPath();
        this.socket = socket;
        this.clientOutput = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStrReader = new InputStreamReader(socket.getInputStream());
            BufferedReader buffReader = new BufferedReader(inputStrReader);
            StringBuilder requestInfo = new StringBuilder();

            String buffLine = null;
            do {
                buffLine = buffReader.readLine();
                requestInfo.append(buffLine + "\r\n");
            } while (!buffLine.isBlank());

            String method = requestInfo.toString().split("\n")[0].split(" ")[0];
            String fileName = getFileName(requestInfo);

            System.out.println(getConsoleInfo(requestInfo));

            if (method.equals("GET")) {
                getResponse(fileName);
            } else if (method.equals("POST")) {
                postResponse();
            }
        } catch (IOException e) {
            System.err.println("[Error] Something went wrong. Closing thread");
        }
    }

    private boolean validateFileName(String fileName) {
        File file = new File(dir, fileName);
        try {
            if (!file.getCanonicalPath().startsWith(dirCanPath)) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private String getConsoleInfo(StringBuilder requestInfo) {
        String method = requestInfo.toString().split("\n")[0].split(" ")[0];
        String fileName = getFileName(requestInfo);
        StringBuilder info = new StringBuilder();
        String version = requestInfo.toString().split("\n")[0].split(" ")[2];
        String host = requestInfo.toString().split("\n")[1].split(" ")[1];
        String connectionType = requestInfo.toString().split("\n")[2].split(" ")[1];
        info.append("[Info] Host: " + host + "\n");
        info.append("[Info] Method: " + method
                + ", Path: " + fileName
                + ", Version: " + version + "\n");

        if (fileExists(fileName)) {
            info.append("[Info] Server request file exists\n");
        } else {
            info.append("[Error] Server request file does not exist\n");
        }
        if (isFileNameComplete(fileName)) {
            info.append("[Info] Requested item is a file\n");
        } else {
            info.append("[Info] Requested item is a directory\n");
        }
        if (!isFileNameComplete(fileName)) {
            fileName += "/index.html";
        }

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        info.append("[Info] Date: " + strDate + ", Server: " + Server.class.getCanonicalName() + "\n");
        info.append("[Info] Connection: " + connectionType + "\n");
        info.append("[Info] Content-length: " + getContentLength(fileName) + ", Content-type: "
                + getContentType(fileName) + "");

        return info.toString();
    }

    private String getFileName(StringBuilder requestInfo) {
        String fileName = requestInfo.toString().split("\n")[0].split(" ")[1];
        if (!validateFileName(fileName)) {
            System.err.println("[Error] User tried to escape root");
            fileName = "/index.html";
        }
        return fileName;
    }

    private String getContentType(String fileName) {
        String contentType = "text/html";
        if (fileName.endsWith(".png") || fileName.endsWith(".jpeg")) {
            contentType = "image/png";
        } else if (fileName.endsWith(".css")) {
            contentType = "text/css";
        }
        return contentType;
    }

    private void postResponse() throws IOException {
        String fileName = "newImage.png";
        InputStreamReader inputStrReader = new InputStreamReader(socket.getInputStream());
        BufferedReader buffReader = new BufferedReader(inputStrReader);
        StringBuilder requestHeader = new StringBuilder();

        String buffLine = null;
        for (int i = 0; i < 4; i++) { // Skipping header
            buffLine = buffReader.readLine();
            requestHeader.append(buffLine + "\r\n");
        }
        System.out.println(requestHeader);

        StringBuilder requestContent = new StringBuilder();
        do {
            buffLine = buffReader.readLine(); // Reading body, containing img
            requestContent.append(buffLine + "\r\n");
        } while (!buffLine.isBlank());

        System.out.println(requestContent);

        byte[] fileBytes = requestContent.toString().getBytes();

        try {
            FileOutputStream fos = new FileOutputStream(dir + fileName);
            fos.write(fileBytes);
            fos.close();
        } catch (IOException e) {
            System.err.println("[Error] Could not create a file output stream");
        }

        handleStatusCodes("/uploaded_img" + fileName);
    }

    private void getResponse(String fileName) {
        try {
            if (!isFileNameComplete(fileName)) {
                fileName += "/index.html";
            }
            if (fileName.equals("/redirect.html")) {
                status302(fileName);
            } else if (fileExists(fileName)) {
                handleStatusCodes(fileName);
            } else {
                status404();
            }

        } catch (Exception e) {
            status500();
        }
    }

    private boolean fileExists(String fileName) {
        File file = new File(dir + fileName);
        return file.exists();
    }

    private boolean isFileNameComplete(String fileName) {
        return fileName.matches("^(.+)\\/*([^\\/]+)\\.[a-z]*$");
    }

    private void writeHeaderInfo(String fileName) throws IOException {
        clientOutput.write(("content-type: " + getContentType(fileName) + "\n").getBytes());
        clientOutput.write(("content-length: " + getContentLength(fileName) + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
    }

    private int getContentLength(String fileName) {
        int contentLength = 0;
        FileInputStream file;
        try {
            file = new FileInputStream(dir + fileName);
            contentLength = file.readAllBytes().length;
            file.close();
        } catch (FileNotFoundException e) {
            System.err.println("[Error] File does not exist");
        } catch (IOException e) {
            System.err.println("[Error] Could not read from file");
        }
        return contentLength;
    }

    private void writeHeaderHtml(String fileName) throws IOException {
        FileInputStream file = new FileInputStream(dir + fileName);
        clientOutput.write(file.readAllBytes());
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        file.close();
    }

    private void handleStatusCodes(String fileName) throws IOException {
        if (fileName.equals("/redirect.html")) {
            status302(fileName);
        } else if (fileName.equals("/404.html")) {
            status404();
        } else if (fileName.equals("/500.html")) {
            throw new IllegalArgumentException();
        } else {
            status200(fileName);
        }
    }

    private void status200(String fileName) throws IOException {
        try {
            clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
            System.out.println("[Info] Response: 200 OK\n");
            writeHeaderInfo(fileName);
            writeHeaderHtml(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("\n[Error] File does not exist\n");
            status404();
        }
    }

    private void status302(String fileName) throws IOException {
        System.out.println("[Info] Response: 302 Found\n");

        clientOutput.write("HTTP/1.1 302 Found\r\n".getBytes());
        clientOutput.write("location: /index.html".getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
    }

    private void status404() throws IOException {
        String fileName = "/404.html";
        clientOutput.write("HTTP/1.1 404 Not Found\r\n".getBytes());
        System.out.println("[Info] Response: 404 Not Found\n");

        writeHeaderInfo(fileName);
        writeHeaderHtml(fileName);
    }

    private void status500() {
        try {
            String fileName = "/500.html";
            clientOutput.write("HTTP/1.1 500 Internal Server Error\r\n".getBytes());
            System.out.println("[Info] Response: 500 Internal Server Error\n");

            writeHeaderInfo(fileName);
            writeHeaderHtml(fileName);
        } catch (IOException e) {
            System.err.println("[Error] Could not show Status 500 Page");
        }
    }
}
