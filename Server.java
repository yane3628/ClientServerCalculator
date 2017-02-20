import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket server = null;
        Socket client = null;
        BufferedReader inBuffer = null;
        PrintWriter outWriter = null;
        String message = null;

        // Get port number from args
        int serverPort = Integer.parseInt(args[0]);

        try {
            // Initiate ServerSocket
            server = new ServerSocket(serverPort);

            try {
                // Exit with 'terminate'
                while(true) {
                    try {
                        client = server.accept();
                        System.out.println("Connected");

                        try {
                            inBuffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            // outWriter = new PrintWriter(client.getOutputStream(), true);
                        } catch (IOException e) {
                            System.out.println("Read failed 1");
                            System.exit(-1);
                        }

                        // Exit with 'bye'
                        while(true) {
                            try{
                                message = inBuffer.readLine();
                                System.out.println(message);
                                //Send data back to client
                                // outWriter.println(message);
                            } catch (IOException e) {
                                System.out.println("Read failed 2");
                                System.exit(-1);
                            }
                            // Deconstruct inBuffer and outWriter
                        }
                    } catch(Exception e) {
                        System.out.println("Couldn't accept client");
                    }

                    System.out.println("connected");
                }
            } finally { // Change to catch
                server.close();
            }
        }
        catch(IOException e) {
            System.out.println("Connection could not be established");
            System.exit(-5);
        }
    }
}
