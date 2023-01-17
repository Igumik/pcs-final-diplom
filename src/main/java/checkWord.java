import java.io.*;
import java.net.Socket;

public class checkWord {
    private static final int PORT = 8989;

    public static void main(String[] args) throws IOException {
        try (Socket clientSocket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String word = "бизнес";
            out.println(word);
            System.out.println("Найдено слово: " + word);
            String check;
            while ((check = in.readLine()) != null) {
                System.out.println(check);
            }
        }
    }
}
