import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NetworkClientConnection {
    public String id;
    //public Socket socket;
    private NetworkDispatcher dispatcher;
    private NetworkListener listener;

    public NetworkClientConnection(String id, ObjectInputStream input, ObjectOutputStream output) {
        this.id = id;
        //this.socket = socket;
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
        return listener.listen();
    }
}