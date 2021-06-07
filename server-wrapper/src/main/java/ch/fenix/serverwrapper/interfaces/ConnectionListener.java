package ch.fenix.serverwrapper.interfaces;

import ch.fenix.serverwrapper.Connection;

/**
 * @author CR1N993R
 *
 * The Interface for Listening for connections
 */
public interface ConnectionListener {
    /**
     * Returns the newly connected socket
     * @param socket new socket
     */
    void onConnection(Connection socket);
}
