import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("10.194.120.57", 8081);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(" Connected to server. Type a number to check (or 'exit' to quit):");

            while (true) {
                System.out.print("Enter a number: ");
                String msg = scanner.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;

                out.println(msg);
                String response = in.readLine();
                System.out.println("Server says: " + response);
            }

            System.out.println(" Connection closed.");
        }
    }
}
