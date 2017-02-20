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

    public enum Operation {
        add, subtract, multiply
    }

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

                                String[] messageArray = message.split("\\s+");

                                if (message.equalsIgnoreCase("bye")) {
                                    outWriter.println(exitCode);
                                    printReturn(Integer.toString(exitCode));

                                    // Close current client socket connection
                                    client.close();
                                }
                                else if(message.equalsIgnoreCase("terminate")) {
                                    outWriter.println(exitCode);
                                    printReturn(Integer.toString(exitCode));

                                    // Close current client socket connection
                                    client.close();
                                    server.close();
                                    System.exit(0);
                                }
                                else {
                                    boolean noErrors = true;
                                    // Check if add, subtract, or multiply
                                    try {
                                        Operation operation = Operation.valueOf(messageArray[0]);

                                        // Check number of inputs
                                        if(messageArray.length < 3) {
                                            // Send error code -2 for number of inputs is less than two
                                            outWriter.println(numInputsLTTwo);
                                            printReturn(Integer.toString(numInputsLTTwo));
                                        }
                                        else if(messageArray.length > 5) {
                                            // Send error code -3 for number of inputs is more than 4
                                            outWriter.println(numInputsGTFour);
                                            printReturn(Integer.toString(numInputsGTFour));
                                        }
                                        else {
                                            // Check everything is an int (positive)
                                            for(int i = 1; i < messageArray.length; i++) {
                                                if(!messageArray[i].matches("\\d+")) {
                                                    // Send error code -4 for inputs contains non-numbers
                                                    outWriter.println(nan);
                                                    printReturn(Integer.toString(nan));

                                                    noErrors = false;
                                                    break;
                                                }
                                            }

                                            if(noErrors) {
                                                switch (operation) {
                                                    case add:
                                                        int sum = 0;
                                                        for(int i = 1; i < messageArray.length; i++) {
                                                            sum += Integer.parseInt(messageArray[i]);
                                                        }

                                                        outWriter.println(sum);
                                                        printReturn(Integer.toString(sum));
                                                        break;
                                                    case subtract:
                                                        int sub = Integer.parseInt(messageArray[1]);
                                                        for(int i = 2; i < messageArray.length; i++) {
                                                            sub -= Integer.parseInt(messageArray[i]);
                                                        }

                                                        outWriter.println(sub);
                                                        printReturn(Integer.toString(sub));
                                                        break;
                                                    case multiply:
                                                        int mult = Integer.parseInt(messageArray[1]);
                                                        for(int i = 2; i < messageArray.length; i++) {
                                                            mult *= Integer.parseInt(messageArray[i]);
                                                        }

                                                        outWriter.println(mult);
                                                        printReturn(Integer.toString(mult));
                                                        break;
                                                    default:
                                                        outWriter.println("0");
                                                        printReturn(Integer.toString(0));
                                                        break;
                                                }
                                            }
                                        }
                                    } catch(IllegalArgumentException e) {
                                        // Send error code -1 for incorrect operation command
                                        outWriter.println(incorrectOperationCommand);
                                        printReturn(Integer.toString(incorrectOperationCommand));
                                    }

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
                    //     System.out.println("Couldn't accept client");
                    }

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
