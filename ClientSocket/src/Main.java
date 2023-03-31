import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Client client;
    public static void main(String[] args) throws IOException {
        client = new Client();

        client.startConnection("127.0.0.1", 8080);
    }



}