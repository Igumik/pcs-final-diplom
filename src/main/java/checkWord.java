import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class checkWord {
    private static final int PORT = 8989;

    public static void main(String[] args) throws IOException {
        try (Socket clientSocket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            out.println("бизнес");
        }
    }
}
