import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.util.UUID;
import java.util.ArrayList;

public class NetworkClient {
    private String id;
    private Socket socket;
    private boolean connected = false;

    private NetworkDispatcher dispatcher;
    private NetworkListener listener;

    private volatile ArrayList<String> actorId;
    private volatile Dictionary<String,NetworkActor> networkActors;
    private volatile Queue<NetworkTask> networkTasks;

    private final int port = 8888;
    private final int SOCKET_TIMEOUT = 3;

    public NetworkClient() {
        networkTasks = new Queue<NetworkTask>();
        actorId = new ArrayList<String>();
        networkActors = new Dictionary<String, NetworkActor>();
    }

    public void connect() {
        try {
            System.out.println("Connecting to " + String.valueOf(port));
            socket = new Socket("localhost", port);
            socket.setSoTimeout(SOCKET_TIMEOUT);

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            dispatcher = new NetworkDispatcher(output);
            listener = new NetworkListener(input);

            connected = true;
            runClient();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private void runClient() {
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
        Thread dispatcherThread = new Thread(dispatcher);
        dispatcherThread.start();
        Thread clientThread = new Thread() {
            public void run() {
                while(true) {
                    listenToServer();
                    handleTasks();
                    try {
                        Thread.sleep(1);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        clientThread.start();
    }

    private void listenToServer() {
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

        System.out.println(task.action);

        if(task.type == TaskType.IN)
            updateClient(task);
        else
            updateServer(task);
    }

    public void updateClient(NetworkTask task) {
        if(task == null)
            return;
        if(networkActors.size() <= 1)
            return;

        if(task.action.contains("COMMAND")) {
            doClientCommand(task);
            return;
        }

        NetworkActor actor = networkActors.getValue(task.actorId);

        if(actor == null)
            return;
        
        actor.applyActor(task);
    }

    private void doClientCommand(NetworkTask task) {
        String command = task.action;

        switch(command) {
            case "COMMAND CREATE":
                NetDummy dummy = new NetDummy();
                dummy.networkActor = new NetworkActor(dummy, this);
                GameObject.instantiate(dummy);
                break;
            case "COMMAND REMOVE":
                break;
        }
    }

    private void updateServer(NetworkTask task) {
        if(task == null)
            return;
        dispatcher.dispatch(task);
        
    }

    public void addNetworkTask(NetworkTask task) {
        task.id = id;
        networkTasks.enqueue(task);
    }

    public void addNetworkActor(NetworkActor actor) {
        String id = UUID.randomUUID().toString();

        try {
            networkActors.add(id, actor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        actor.id = id;
        actorId.add(id);
    }

    public static void main(String[] args) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.connect();
    }
}