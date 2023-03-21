import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Client client;
    public static void main(String[] args) throws IOException {
        client = new Client();
        client.startConnection("127.0.0.1", 8080);

        String message = "";
        while(!message.equals(".")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("You: ");
            message = scanner.nextLine();

            String res = client.sendMessage(message);
            System.out.println("Server: " + res);
        }

        client.stopConnection();
    }
}