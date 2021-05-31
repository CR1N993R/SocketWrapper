import ch.fenix.clientwrapper.Connection;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Connection connection = new Connection("127.0.0.1", 25555);
        Thread.sleep(100);
        connection.setOn("hello", System.out::println);
        connection.emit("hello","fdsafdsafa");

    }
}
