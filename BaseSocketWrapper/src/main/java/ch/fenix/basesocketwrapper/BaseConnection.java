package ch.fenix.basesocketwrapper;

import ch.fenix.basesocketwrapper.interfaces.MsgEventListener;
import ch.fenix.basesocketwrapper.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseConnection {
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintStream printStream;
    private final List<MsgListener> listeners = new ArrayList<>();
    protected boolean running;

    protected BaseConnection(Socket socket, PrintStream printStream, BufferedReader reader){
        this.socket = socket;
        this.reader = reader;
        this.printStream = printStream;
        start();
    }

    protected BaseConnection(String address, int port) throws IOException {
        socket = new Socket(address, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printStream = new PrintStream(socket.getOutputStream());
        start();
    }

    private void start(){
        running = true;
        new Thread(this::threadTask).start();
    }

    private void threadTask() {
        while (running){
            try {
                String msg = reader.readLine();
                String [] data = msg.split("\\|");
                sendToListeners(data[0], data[1]);
            } catch (IOException e) {
                disconnected();
            }
        }
    }

    protected void disconnected(){
        running = false;
        sendToListeners("disconnect", "");
    }

    protected void sendToListeners(String type, String msg){
        for (MsgListener listener : listeners) {
            if (listener.getType().equals(type)){
                listener.onMessage(msg);
            }
        }
    }


    public void emit(String event, String msg){
        Util.notNull(event,msg);
        event = event.replace("|", "");
        printStream.println(event + "|" + msg);
    }

    public void close() throws IOException {
        socket.close();
        running = false;
    }

    public void setOn(String event, MsgEventListener listener){
        Util.notNull(event,listener);
        MsgListener msgListener = new MsgListener(event,listener);
        listeners.add(msgListener);
    }

    public void removeListener(MsgEventListener listener){
        for (MsgListener msgListener : listeners) {
            if (msgListener.getEventListener() == listener){
                listeners.remove(msgListener);
                return;
            }
        }
    }

    public void removeAllListeners(){
        listeners.clear();
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isRunning() {
        return running;
    }
}
