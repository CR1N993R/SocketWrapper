# Java-Socket-Wrapper

This is a java library for simplifying the usage of Java sockets.


# Build
Run the following command in the root directory this will build all packages.

The server jar will be located in server-wrapper/target/server-wrapper-{version}.jar

The client jar will be located in client-wrapper/target/client-wrapper-{version}.jar


```shell
mvn install
```


# Usage
## Server

```java
import ch.fenix.serverwrapper.ServerSocketWrapper;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        ServerSocketWrapper serverSocketWrapper = new ServerSocketWrapper(25555);
        serverSocketWrapper.addOnConnection(socket -> socket.setOn("hello", msg -> {
            System.out.println(msg);
            socket.emit("hello", "world");
            serverSocketWrapper.broadCast("hello", "broadcast");
        }));
    }
}
```

## Client

```java
import ch.fenix.clientwrapper.Connection;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Connection connection = new Connection("127.0.0.1", 25555);
        Thread.sleep(100);
        connection.setOn("hello", System.out::println);
        connection.emit("hello","world");
    }
}
```