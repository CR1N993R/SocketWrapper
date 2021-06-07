package ch.fenix.basesocketwrapper;

import ch.fenix.basesocketwrapper.interfaces.MsgEventListener;

/**
 *  @author CR1N993R
 *
 *  This class is being used for storing the MsgEventListener and the type when it should be called
 */
public class MsgListener {
    private final String type;
    private final MsgEventListener eventListener;

    public MsgListener(String type, MsgEventListener eventListener) {
        this.type = type;
        this.eventListener = eventListener;
    }

    /**
     * Triggers the attached event
     * @param msg the received message
     */
    public void onMessage(String msg){
        eventListener.onMessage(msg);
    }

    /**
     * @return returns the event type
     */
    public String getType() {
        return type;
    }

    /**
     * @return returns the event listener
     */
    public MsgEventListener getEventListener() {
        return eventListener;
    }
}
