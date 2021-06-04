package ch.fenix.serverwrapper;

import ch.fenix.serverwrapper.interfaces.ConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketWrapper {
    private final ServerSocket serverSocket;
    private final List<ConnectionListener> listeners = new ArrayList<>();
    private final List<Connection> connections = new ArrayList<>();
    private boolean running;

    public ServerSocketWrapper(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        start();
    }

    private void start(){
        running = true;
        new Thread(this::threadTask).start();
    }

    private void threadTask(){
        while (running){
            try {
                createConnection(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createConnection(Socket socket) throws IOException {
        Connection connection = new Connection(socket, connections::remove);
        connections.add(connection);
        sendToListeners(connection);
    }

    private void sendToListeners(Connection connection){
        for (ConnectionListener listener : listeners) {
            listener.onConnection(connection);
        }
    }


    public void addOnConnection(ConnectionListener listener){
        listeners.add(listener);
    }

    public void removeConnectionListener(ConnectionListener listener){
        listeners.remove(listener);
    }

    public void removeAllListeners(){
        listeners.clear();
    }

    public void broadCast(String event, String msg){
        for (Connection connection : connections) {
            connection.emit(event, msg);
        }
    }

    public void emitToGroup(String event, String msg, String group){
        for (Connection connection : connections) {
            if (connection.getGroup().equals(group)){
                connection.emit(event, msg);
            }
        }
    }
}
