import java.io.IOException;

public class Main {
    private static Server server;
    public static void main(String[] args) throws IOException {
        server = new Server();
        try {
            server.start(8080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}