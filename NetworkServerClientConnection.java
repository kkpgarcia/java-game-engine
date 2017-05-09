import java.net.Socket;
import java.net.ServerSocket;

public class NetworkServerClientConnection {
    public String id;
    private ServerSocket server;
    private Socket client;
    private NetworkServerDispatcher dispatcher;
    private NetworkServerListener listener;

    public NetworkServerClientConnection(String id, Socket socket, ServerSocket server) {
        this.client = socket;
        this.server = server;
        dispatcher = new NetworkServerDispatcher(id, socket, this);
        listener = new NetworkServerListener(id, socket, this);
        
        //initialize();
    }

    public void initialize() {
        Thread dispatcherThread = new Thread(dispatcher);
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
        dispatcherThread.start();
    }

    public void dispatch(String data) {
        dispatcher.dispatch(data);
    }
}