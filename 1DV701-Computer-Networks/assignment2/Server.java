import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int port = 80;
    private static String path = "public";

    public static void main(String[] args) {
        getProgramArguments(args);
        manageConn();
    }

    private static void manageConn() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("[Info] Listening on port " + port);
            File pathFolder = new File(path);
            System.out.println("[Info] Detailed path: " + pathFolder.getAbsolutePath() + "\n");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("===================\n");
                    System.out.println("\n[Info] Client connected: \nInet Address: " + socket.getInetAddress()
                            + "\nLocal Address: " + socket.getLocalAddress() + "\nPort: " + socket.getPort()
                            + "\nLocal Port: " + socket.getLocalPort() + "\n");
                    Thread conn = new Thread(new ServerConn(path, socket));
                    conn.setPriority(conn.getPriority() + 1);
                    conn.start();
                } catch (IOException e) {
                    System.err.println("\n[Error] Socket error\n");
                }
            }
        } catch (IOException e) {
            System.err.println("\n[Error] Socket error\n");
        }
    }

    private static void getProgramArguments(String[] args) {
        try {
            port = Integer.parseInt(args[0]);
            path = args[1];
            if (path == null) {
                System.err.println("\n[Error] Null path\n");
            }
            path.replaceAll("../", "");
            File file = new File(path);
            if (file.exists()) {
                System.out.println("[Info] Server source file exists");
            } else {
                System.err.println("[Error] Server source file does not exist");
            }
        } catch (Exception e) {
            System.err.println("\n[Error] Invalid program arguments. Usage \"java Server [port] [dir]\". Using defaults\n");
            port = 80;
            path = "public";
        }
    }
}
