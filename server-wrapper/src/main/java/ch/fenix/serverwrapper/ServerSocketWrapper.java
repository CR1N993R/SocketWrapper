package ch.fenix.serverwrapper;

import ch.fenix.serverwrapper.interfaces.ConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *  @author CR1N993R
 *
 *  Contains the Serversocket and adds aditional functionality like listening for incomming connection, emitting to groups
 *  and managing the connections
 */
public class ServerSocketWrapper {
    private final ServerSocket serverSocket;
    private final List<ConnectionListener> listeners = new ArrayList<>();
    private final List<Connection> connections = new ArrayList<>();
    private boolean running;

    public ServerSocketWrapper(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        start();
    }

    /**
     * Starts the thread which listens for new connections
     */
    private void start(){
        running = true;
        new Thread(this::threadTask).start();
    }

    /**
     * This is the task which the Thread is executing
     */
    private void threadTask(){
        while (running){
            try {
                createConnection(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * If a new connection is established it is being sent to here for further processing
     * @param socket the new connection
     * @throws IOException due to the setup of the message listeners the socket may send a IOException
     */
    private void createConnection(Socket socket) throws IOException {
        Connection connection = new Connection(socket, connections::remove);
        connections.add(connection);
        sendToListeners(connection);
    }

    /**
     * If a new connection is established this function is being called to inform all listeners
     * @param connection the new connection
     */
    private void sendToListeners(Connection connection){
        for (ConnectionListener listener : listeners) {
            listener.onConnection(connection);
        }
    }

    /**
     * Takes in a new listener which listens for connections
     * @param listener the listener
     */
    public void addOnConnection(ConnectionListener listener){
        listeners.add(listener);
    }

    /**
     * Takes in a previously generated ConnectionListener and removes it from the listeners
     * @param listener the listener which will be removed
     */
    public void removeConnectionListener(ConnectionListener listener){
        listeners.remove(listener);
    }

    /**
     * Removes all connection listeners
     */
    public void removeAllListeners(){
        listeners.clear();
    }

    /**
     * @return returns all connections
     */
    public List<Connection> getConnections() {
        return connections;
    }

    /**
     * @return returns the instance of the ServerSocket
     */
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * emits a message to every connection
     * @param event takes in an event which can be listened for in all clients
     * @param msg takes in any message that will be sent to all clients
     */
    public void broadCast(String event, String msg){
        for (Connection connection : connections) {
            connection.emit(event, msg);
        }
    }

    /**
     * emits to the specified group
     * @param event takes in an event which can be listened for in all clients
     * @param msg takes in any message that will be sent to all clients
     * @param group requires a the group name
     */
    public void emitToGroup(String event, String msg, String group){
        for (Connection connection : connections) {
            if (connection.getGroup().equals(group)){
                connection.emit(event, msg);
            }
        }
    }
}
