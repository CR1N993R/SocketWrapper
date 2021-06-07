package ch.fenix.clientwrapper;

import ch.fenix.basesocketwrapper.BaseConnection;

import java.io.IOException;

/**
 *  @author CR1N993R
 *
 *  the connection for the client
 */
public class Connection extends BaseConnection {
    public Connection(String address, int port) throws IOException {
        super(address,port);
    }
}
