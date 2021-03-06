import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    // Helper method to print in the "receive: " format
    // parameters:
    //  message - String to print with "receive: " appended
    public static void printReceive(String message) {
        System.out.println("receive: " + message);
    }

    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            System.out.println("Not enough arguments");
            System.exit(-1);
        }

        String ipAddress = args[0];
        int port = Integer.parseInt(args[1]);
        try {
            Socket server = new Socket(ipAddress, port);

            String message = null;
            String userInput = null;

            try {
                PrintWriter outWriter = new PrintWriter(server.getOutputStream(), true);
                BufferedReader inBuffer = new BufferedReader(new InputStreamReader(server.getInputStream()));

                // Reads "Hello!" from server
                message = inBuffer.readLine();
                printReceive(message);

                Scanner scanner = new Scanner(System.in);

                // Loop that is constantly waiting for user input
                while(true) {
                    try {
                        userInput = scanner.nextLine();
                        outWriter.println(userInput);

                        // Message received from server
                        message = inBuffer.readLine();

                        switch (Integer.parseInt(message)) {
                            case -1:
                                printReceive("incorrect operation command.");
                                break;
                            case -2:
                                printReceive("number of inputs is less than two.");
                                break;
                            case -3:
                                printReceive("number of inputs is more than four.");
                                break;
                            case -4:
                                printReceive("one or more of the inputs contain(s) non-number(s).");
                                break;
                            case -5:
                                printReceive("exit.");
                                server.close();
                                System.exit(0);
                                break;
                            default:
                                printReceive(message);
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("Server not responding. Please restart server and then restart client.");
                        server.close();
                        System.exit(-1);
                    }
                }
            } catch (IOException e) {
                System.out.println("Could not create BufferedReader and/or PrintWriter");
                server.close();
                System.exit(-1);
            }
        } catch(Exception e) {
            System.out.println("Server is not running. Please start server.");
            System.exit(0);
        }
    }
}
