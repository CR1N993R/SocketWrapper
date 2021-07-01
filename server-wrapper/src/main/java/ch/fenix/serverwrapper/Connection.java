package ch.fenix.serverwrapper;

import ch.fenix.basesocketwrapper.BaseConnection;
import ch.fenix.serverwrapper.interfaces.DisconnectListener;

import java.io.*;
import java.net.Socket;

/**
 *  @author CR1N993R
 *
 *  This class is for storing the socket and listening in the server package
 */
public class Connection extends BaseConnection {
    private final DisconnectListener disconnectListener;
    private String group;

    public Connection(Socket socket, DisconnectListener disconnectListener) throws IOException {
        super(socket);
        this.disconnectListener = disconnectListener;
    }

    /**
     * Overrides the disconnect method from the superclass and adds the call to the disconnectListener
     */
    @Override
    protected void disconnected(){
        if (running) {
            running = false;
            sendToListeners("disconnect", "");
            disconnectListener.onDisconnect(this);
        }
    }

    /**
     * Returns the current group
     * @return returns the group name
     */
    public String getGroup(){
        return group;
    }

    /**
     * Set the group of this client
     * Can be used in the ServerSocket to emit to a group
     * @param group the group name
     */
    public void setGroup(String group) {
        this.group = group;
    }
}
