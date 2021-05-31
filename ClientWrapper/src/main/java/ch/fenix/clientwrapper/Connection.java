package ch.fenix.clientwrapper;

import ch.fenix.basesocketwrapper.BaseConnection;

import java.io.IOException;

public class Connection extends BaseConnection {
    public Connection(String address, int port) throws IOException {
        super(address,port);
    }
}
