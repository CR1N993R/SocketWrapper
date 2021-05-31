package ch.fenix.serverwrapper.interfaces;

import ch.fenix.serverwrapper.Connection;

public interface ConnectionListener {
    void onConnection(Connection socket);
}
