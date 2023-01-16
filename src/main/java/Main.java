import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {
    private static final int PORT = 8989;

    public static void main(String[] args) throws IOException {
        File[] cat = new File("pdfs").listFiles();
        for (int i = 0; i < Objects.requireNonNull(cat).length; i++) {
            File pdf = cat[i];
            BooleanSearchEngine engine = new BooleanSearchEngine(pdf);
            i++;
//            if(cat.length == i){
//                List<PageEntry> totalList = engine.search("бизнес");
//                for (PageEntry pageEntry : totalList) {
//                    System.out.println(pageEntry.toString());
//                }
//            }
        }

        //SERVER
        try (ServerSocket serverSocket = new ServerSocket(PORT);) { // стартуем сервер один(!) раз
            BooleanSearchEngine engine = new BooleanSearchEngine();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    String word = in.readLine();
                    List<PageEntry> page = engine.search(word);
                    var json = gson.toJson(page);
                    System.out.println("Найдено слово: " + word);
                    System.out.println(json);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}