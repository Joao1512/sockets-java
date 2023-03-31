import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread {

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    private Socket clientSocket;


    private int id;

    public ClientThread(Socket clientSocket, int id) {
        this.clientSocket = clientSocket;
        this.id = id;
    }

    public void run() {
        try {
            setPrintWriter(this.clientSocket);
            setBufferedReader(this.clientSocket);
            writeThread.start();
            readThread.start();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Client " + id + " exited. ");
        }
    }

    private Thread writeThread = new Thread(new Runnable() {
        public void run() {
            write();
        }
    });

    private void write() {
        String message = "";
        while(!message.equals("/")) {
            Scanner scanner = new Scanner(System.in);
            message = scanner.nextLine();

            sendMessage(message);
        }
    }

    private void sendMessage(String message) {
        printWriter.println(message);
    }

    private Thread readThread = new Thread(new Runnable() {
        @Override
        public void run() {
            String line;
            while (true) {
                try {
                    line = bufferedReader.readLine();
                    System.out.println("Client: " + line);
                } catch (IOException e) {
                    System.out.println("Erro ao ler mensagem do cliente: " + e.getMessage());
                }
            }
        }
    });

    private void setPrintWriter(Socket clientSocket) throws IOException {
        printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    private void setBufferedReader(Socket clientSocket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
}
