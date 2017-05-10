import java.io.IOException;
import java.net.Socket;

public class NetworkClient {
    private String id;
    private Socket socket;
    private boolean connected;

    private NetworkDispatcher dispatcher;
    private NetworkListener listener;

    private Queue<NetworkTask> networkTasks;

    private final int port = 8888;

    public NetworkClient() {
        networkTasks = new Queue<NetworkTask>();
    }

    public void connect() {
        try {
            System.out.println("Connecting to " + String.valueOf(port));
            socket = new Socket("localhost", port);

            if(socket != null) {
                dispatcher = new NetworkDispatcher(socket);
                listener = new NetworkListener(socket);
                connected = true;
                runClient();
            } else {
                connected = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void runClient() {
        Thread dispatcherThread = new Thread(dispatcher);
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
        dispatcherThread.start();

        while(connected) {
            listenToServer();
            handleTasks();
        }
    }

    public void listenToServer() {
        NetworkTask task = listener.listen();
        if(task != null) {
            task.type = TaskType.IN;
            addNetworkTask(task);
        }
    }

    private void handleTasks() {
        if(networkTasks.isEmpty())
            return;

        NetworkTask task = networkTasks.dequeue();

        if(task.type == TaskType.IN)
            updateClient(task);
        else
            updateServer(task);
    }

    public void updateClient(NetworkTask task) {
        if(task == null)
            return;
        
        System.out.println(task.action);
    }

    public void updateServer(NetworkTask task) {
        if(task == null)
            return;

        dispatcher.dispatch(task);
    }

    public void addNetworkTask(NetworkTask networkTask) {
        networkTasks.enqueue(networkTask);
    }

    public static void main(String[] args) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.connect();
    }
}