package ch.fenix.serverwrapper.interfaces;

import ch.fenix.serverwrapper.Connection;

/**
 * @author CR1N993R
 *
 * This interface is only used to remove the disconnected socket from the ServerSocketWrapper instance
 */
public interface DisconnectListener {
    /**
     * Returns a disconnected connection
     * @param connection returns the disconnected connection for removal
     */
    void onDisconnect (Connection connection);
}
