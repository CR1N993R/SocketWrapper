package ch.fenix.basesocketwrapper;

import ch.fenix.basesocketwrapper.interfaces.MsgEventListener;

public class MsgListener {
    private final String type;
    private final MsgEventListener eventListener;

    public MsgListener(String type, MsgEventListener eventListener) {
        this.type = type;
        this.eventListener = eventListener;
    }

    public void onMessage(String msg){
        eventListener.onMessage(msg);
    }

    public String getType() {
        return type;
    }

    public MsgEventListener getEventListener() {
        return eventListener;
    }
}
