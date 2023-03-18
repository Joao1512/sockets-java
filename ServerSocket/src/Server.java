import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public void start(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening on port " + port + "...");
            while (true) {
                clientSocket = serverSocket.accept();
                setPrintWriter(clientSocket);
                setBufferedReader(clientSocket);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println("Cliente: " + line);
                    System.out.print("Você: ");
                    Scanner scanner = new Scanner(System.in);

                    String message = "";
                    message = scanner.nextLine();
                    printWriter.println(message);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPrintWriter(Socket clientSocket) throws IOException {
        printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    private void setBufferedReader(Socket clientSocket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
}
