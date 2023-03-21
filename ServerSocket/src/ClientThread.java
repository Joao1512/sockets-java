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

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("Client: " + line);
                System.out.print("You: ");
                Scanner scanner = new Scanner(System.in);

                String message = "";
                message = scanner.nextLine();
                printWriter.println(message);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Client " + id + " exited. ");
        }
    }


    private void setPrintWriter(Socket clientSocket) throws IOException {
        printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    private void setBufferedReader(Socket clientSocket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
}
