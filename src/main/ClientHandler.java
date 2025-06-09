import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter your username:");
            username = in.readLine();

            if (!Validator.isValidUsername(username)) {
                out.println("Invalid username. Connection closed.");
                socket.close();
                return;
            }

            System.out.println(username + " joined the chat.");
            server.broadcast(username + " has joined the chat.", this);

            String msg;
            while ((msg = in.readLine()) != null) {
                if (msg.equalsIgnoreCase("/quit")) break;
                System.out.println(username + ": " + msg);
                server.broadcast(username + ": " + msg, this);
            }
        } catch (IOException e) {
            System.out.println("Error with client " + username + ": " + e.getMessage());
        } finally {
            server.removeClient(this);
            server.broadcast(username + " has left the chat.", this);
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    public void sendMessage(String message) {
        if (out != null) out.println(message);
    }
}