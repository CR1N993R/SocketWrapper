package ch.fenix.basesocketwrapper.interfaces;

/**
 * @author CR1N993R
 *
 * This is the lsitener for messages
 */
public interface MsgEventListener {
    /**
     * Is called when a message is received
     * @param msg the message that has been received
     */
    void onMessage(String msg);
}
