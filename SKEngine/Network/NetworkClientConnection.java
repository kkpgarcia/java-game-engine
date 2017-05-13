package SKEngine.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class NetworkClientConnection {
    public String id;
    private NetworkDispatcher dispatcher;
    private NetworkListener listener;

    public NetworkClientConnection(String id, DataInputStream input, DataOutputStream output) {
        this.id = id;
        listener = new NetworkListener(input);
        dispatcher = new NetworkDispatcher(output);
    }

    public void initialize() {
        Thread dispatcherThread = new Thread(dispatcher);
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
        dispatcherThread.start();
    }

    public void dispatch(NetworkTask task) {
        dispatcher.dispatch(task);
    }

    public NetworkTask listen() {
        NetworkTask task = listener.listen();
        if(task != null)
            task.id = id;
        return task;
    }
}