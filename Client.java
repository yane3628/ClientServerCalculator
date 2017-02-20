import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void printReceive(String message) {
        System.out.println("receive: " + message);
    }
    public static void main(String[] args) throws IOException {
        String ipAddress = args[0];
        int port = Integer.parseInt(args[1]);
        Socket server = new Socket(ipAddress, port);
        String message = null;
        String userInput = null;

        PrintWriter outWriter = new PrintWriter(server.getOutputStream(), true);
        BufferedReader inBuffer = new BufferedReader(new InputStreamReader(server.getInputStream()));

        // Reads "Hello!" from server
        message = inBuffer.readLine();
        printReceive(message);

        Scanner scanner = new Scanner(System.in);

        while(true) {
            userInput = scanner.nextLine();
            outWriter.println(userInput);

            message = inBuffer.readLine();
            printReceive(message);

            if(message.equals("-5")) {
                server.close();
                System.exit(0);
            }

        }
        // System.exit(0);
    }
}
