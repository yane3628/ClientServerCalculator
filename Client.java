import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        String ipAddress = args[0];
        int port = Integer.parseInt(args[1]);
        Socket server = new Socket(ipAddress, port);

        PrintWriter outWriter = new PrintWriter(server.getOutputStream(), true);
        // BufferedReader inBuffer = new BufferedReader(new InputStreamReader(server.getInputStream()));

        outWriter.println("hello");
        System.exit(0);
    }
}
