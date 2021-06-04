import ch.fenix.serverwrapper.ServerSocketWrapper;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        ServerSocketWrapper serverSocketWrapper = new ServerSocketWrapper(25555);
        serverSocketWrapper.addOnConnection(socket -> socket.setOn("hello", msg -> {
            System.out.println(msg);
            socket.emit("hello", "fdsafdsdsafdsafd");
            serverSocketWrapper.broadCast("hello", "broadcast");
        }));
    }
}
