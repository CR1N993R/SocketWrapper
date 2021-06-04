package ch.fenix.serverwrapper;

import ch.fenix.basesocketwrapper.BaseConnection;
import ch.fenix.serverwrapper.interfaces.DisconnectListener;

import java.io.*;
import java.net.Socket;

public class Connection extends BaseConnection {
    private final DisconnectListener disconnectListener;
    private String group;

    public Connection(Socket socket, DisconnectListener disconnectListener) throws IOException {
        super(socket);
        this.disconnectListener = disconnectListener;
    }

    @Override
    protected void disconnected(){
        running = false;
        sendToListeners("disconnect", "");
        disconnectListener.onDisconnect(this);
    }

    public String getGroup(){
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
