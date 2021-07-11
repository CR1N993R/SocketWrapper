package ch.fenix.basesocketwrapper;

import ch.fenix.basesocketwrapper.interfaces.MsgEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author CR1N993R
 * This class is the base connection class
 * It has all the basic functions to wrap the java socket
 */
public abstract class BaseConnection {
    protected Socket socket;
    private final BufferedReader reader;
    private final PrintStream printStream;
    private final List<MsgListener> listeners = new CopyOnWriteArrayList<>();
    protected boolean running;

    protected BaseConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.printStream = new PrintStream(socket.getOutputStream());
        start();
    }

    protected BaseConnection(String address, int port) throws IOException {
        socket = new Socket(address, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printStream = new PrintStream(socket.getOutputStream());
        start();
    }

    /**
     * Starts a new Thread which listens for incoming messages
     */
    private void start() {
        running = true;
        new Thread(this::threadTask).start();
    }

    /**
     * This method is the task that the thread executes
     */
    private void threadTask() {
        while (running) {
            try {
                String msg = reader.readLine();
                System.out.println("reading");
                if (msg != null) {
                    String[] data = msg.split("\\|");
                    sendToListeners(data[0], data[1]);
                } else {
                    disconnected();
                }
            } catch (IOException e) {
                disconnected();
            }
        }
    }

    /**
     * This method is called when the socket is closed
     */
    protected void disconnected() {
        if (running) {
            running = false;
            sendToListeners("disconnect", "");
        }
    }

    /**
     * This message will be called after a message was received and sends it to all listeners
     *
     * @param type the event type
     * @param msg  the message
     */
    protected void sendToListeners(String type, String msg) {
        System.out.println("IN: " + type + " | " + msg);
        for (MsgListener listener : listeners) {
            if (listener.getType().equals(type)) {
                listener.onMessage(msg);
            }
        }
    }

    /**
     * Sends a message to the client or the server
     *
     * @param event the event type
     * @param msg   the message
     */
    public void emit(String event, String msg) {
        System.out.println("OUT: " + event + " | " + msg);
        event = event.replace("|", "");
        printStream.println(event + "|" + msg);
    }

    /**
     * Closes the socket
     *
     * @throws IOException The socket.close throws an exception
     */
    public void close() throws IOException {
        running = false;
        socket.close();
    }

    /**
     * Adds an event listener which is called when an message is received
     *
     * @param event    the event type on which it should be called
     * @param listener an instance of the MsgListener interface
     */
    public void setOn(String event, MsgEventListener listener) {
        MsgListener msgListener = new MsgListener(event, listener);
        listeners.add(msgListener);
    }

    /**
     * Removes the specified listener
     *
     * @param listener the listener to remove
     */
    public void removeListener(MsgEventListener listener) {
        listeners.removeIf(msgListener -> msgListener.getEventListener() == listener);
    }

    /**
     * Removes all Listeners which have this event
     *
     * @param event the event to remove
     */
    public void removeAllListenersByEvent(String event) {
        listeners.removeIf(msgListener -> msgListener.getType().equals(event));
    }

    /**
     * Removes all listeners
     */
    public void removeAllListeners() {
        listeners.clear();
    }

    /**
     * @return returns the socket of this instance
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @return returns whether this socket is still running or has already been closed
     */
    public boolean isRunning() {
        return running;
    }
}
