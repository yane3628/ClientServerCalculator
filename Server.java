import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // private static int serverPort;
    // private static ServerSocket server;
    // private static Socket client;

    public static void main(String[] args) throws IOException {
        ServerSocket server;
        Socket client;

        for(int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        // this.serverPort = Integer.parseInt(args[0]);
        int serverPort = Integer.parseInt(args[0]);

        try {
            // this.server = new ServerSocket(serverPort);
            server = new ServerSocket(serverPort);

            // this.client = server.accept();
            client = server.accept();

            try {
                while(true) {
                    System.out.println("connected");
                }
            }
            finally {
                // this.client.close();
                client.close();
                // this.server.close();
                server.close();
            }
        }
        catch(IOException e) {
            System.out.println("Connection could not be established");
            System.exit(-5);
        }
    }
}
