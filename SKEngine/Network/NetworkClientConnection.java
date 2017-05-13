package SKEngine.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * <h2>Network Client Connection</h2>
 * A server node connecting the client and the server.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-09-05
 * */
public class NetworkClientConnection {
    public String id;
    private NetworkDispatcher dispatcher;
    private NetworkListener listener;

    /**
     * Constructs a connection between a client, and the server.
     * @param String connection id
     * @param DataInputStream input stream from socket
     * @param DataOutputStream output stream from socket
     * */
    public NetworkClientConnection(String id, DataInputStream input, DataOutputStream output) {
        this.id = id;
        listener = new NetworkListener(input);
        dispatcher = new NetworkDispatcher(output);
    }

    /**
     * Initializes this connection, and runs the threads for the
     * listeners, and the dispatchers.
     * */
    public void initialize() {
        Thread dispatcherThread = new Thread(dispatcher);
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
        dispatcherThread.start();
    }

    /**
     * Sends a task to the dispatcher.
     * @param NetworkTask task
     * */
    public void dispatch(NetworkTask task) {
        dispatcher.dispatch(task);
    }

    /**
     * Retrieve a task from the listener.
     * return NetworkTask task
     * */
    public NetworkTask listen() {
        NetworkTask task = listener.listen();
        if(task != null)
            task.id = id;
        return task;
    }
}