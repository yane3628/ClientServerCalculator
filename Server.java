import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int incorrectOperationCommand = -1;
    private static int numInputsLTTwo = -2;
    private static int numInputsGTFour = -3;
    private static int nan = -4;
    private static int exitCode = -5;

    public static void printGet(String message) {
        System.out.print("get: " + message);
    }

    public static void printReturn(String message) {
        System.out.println(", return: " + message);
    }

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
                        // TODO make sure this address is right when running on two IP addresses
                        System.out.println("get connection from ... " + client.getInetAddress());

                        try {
                            inBuffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            outWriter = new PrintWriter(client.getOutputStream(), true);
                        } catch (IOException e) {
                            System.out.println("Read failed 1");
                            System.exit(-1);
                        }

                        outWriter.println("Hello!");

                        // Exit with 'bye'
                        while(true) {
                            // try{
                                message = inBuffer.readLine();
                                printGet(message);

                                if (message.equals("bye")) {
                                    outWriter.println(exitCode);
                                    printReturn(Integer.toString(exitCode));

                                    // Close current client socket connection
                                    client.close();
                                }
                                else if(message.equals("terminate")) {
                                    outWriter.println(exitCode);
                                    printReturn(Integer.toString(exitCode));

                                    // Close current client socket connection
                                    client.close();
                                    server.close();
                                    System.exit(0);
                                }
                                else {
                                    outWriter.println("0");
                                    printReturn(Integer.toString(0));
                                }
                                //Send data back to client
                                // outWriter.println(message);
                            // } catch (IOException e) {
                            //     System.out.println("Read failed 2");
                            //     System.exit(-1);
                            // }
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
