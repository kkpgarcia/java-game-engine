import java.net.Socket;

public class NetworkClientConnection {
    public String id;
    public Socket socket;
    private NetworkDispatcher dispatcher;
    private NetworkListener listener;

    public NetworkClientConnection(String id, Socket socket) {
        this.id = id;
        this.socket = socket;
        dispatcher = new NetworkDispatcher(socket);
        listener = new NetworkListener(socket);
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