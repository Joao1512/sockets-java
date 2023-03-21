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
        serverSocket = new ServerSocket(port);
        System.out.println("Listening on port " + port + "...");
        int count = 0;
        while (true) {
            clientSocket = serverSocket.accept();
            count++;
            System.out.println("Total clients connected: " + count);
            ClientThread clientThread = new ClientThread(clientSocket, count);
            clientThread.start();
        }

    }
}
