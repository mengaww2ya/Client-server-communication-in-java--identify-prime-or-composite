import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            System.out.println("Server started. Waiting for connections...");

            while (true) {
                new Thread(new ClientHandler(serverSocket.accept())).start();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
        System.out.println("New client connected: " + socket.getInetAddress());
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("Received from client: " + msg);

                // Validate input (check if it's a number)
                try {
                    int number = Integer.parseInt(msg);
                    String response = isPrime(number) ? "Prime" : "Composite";
                    out.println(response);
                    System.out.println("Response sent: " + response);
                } catch (NumberFormatException e) {
                    out.println("Enter a valid number!");
                    System.out.println("Invalid input received.");
                }
            }
        } catch (Exception e) {
            System.out.println("Client disconnected.");
        }
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) if (n % i == 0) return false;
        return true;
    }
}
