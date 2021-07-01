import ch.fenix.clientwrapper.Connection;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        Connection connection = new Connection("127.0.0.1", 25555);
        connection.setOn("hello", (s) -> {
            System.out.println(s);
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connection.emit("hello", "World");
    }
}
