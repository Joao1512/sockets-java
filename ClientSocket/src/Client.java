import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {
    private static Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            listenThread.start();
            writeThread.start();
        } catch (IOException e) {
            System.out.println("Não foi possível iniciar a conexão com o servidor: " + e.getMessage());
        }
    }

    private Thread listenThread = new Thread(new Runnable() {
        public void run() {
            try {
                listenMessages();
            } catch (IOException e) {
                System.out.println("Não foi possível escutar mensagens do servidor: " + e.getMessage());
            }

        }
    });

    private Thread writeThread = new Thread(new Runnable() {
        public void run() {
            String message = "";
            while(!message.equals("/")) {
                Scanner scanner = new Scanner(System.in);
                message = scanner.nextLine();
                sendMessage(message);
            }
            stopConnection();
        }
    });

    public  void sendMessage(String message) {
        out.println(message);
    }

    public void listenMessages() throws IOException {
        while (true) {
            try {
                System.out.println("Server: " + in.readLine());
            } catch (IOException e) {
                System.out.println("Erro ao tentar ler a mensagem do servidor: " + e.getMessage());
            }
        }
    }

    public  void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        }
        catch(IOException e) {
            System.out.println("Não foi possível encerrar a conexão: " + e.getMessage());
        }
    }
}