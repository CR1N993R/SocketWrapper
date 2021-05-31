package ch.fenix.serverwrapper.interfaces;

import ch.fenix.serverwrapper.Connection;

public interface DisconnectListener {
    void onDisconnect (Connection connection);
}
