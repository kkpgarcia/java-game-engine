import java.io.IOException;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class NetworkClient {
    public static NetworkClient instance;

    private ArrayList<NetworkActor> networkActors = new ArrayList<NetworkActor>();
    private Queue<NetworkTask> networkTasks = new Queue<NetworkTask>();

    private NetworkClientListener listener;
    private NetworkClientDispatcher dispatcher;

    private final int port = 8888;
    private String networkId;
    private Socket socket;
    private boolean connected = false;

    public NetworkClient() {
        instance = this;
        networkId = UUID.randomUUID().toString();
    }

    public void connect() {
        try {
            System.out.println("Connecting to " + String.valueOf(port));
            socket = new Socket("localhost", port);

            listener = new NetworkClientListener(socket);
            dispatcher = new NetworkClientDispatcher(socket);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(networkId);

            if(socket != null) {
                connected = true;

                Thread listenerThread = new Thread(listener);
                Thread dispatcherThread = new Thread(dispatcher);

                listenerThread.start();
                dispatcherThread.start();

                update();
            } else {
                throw new IOException("Failed to create connection.");
            }

            System.out.println("Connected to " + socket.getRemoteSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void update() {
        while(connected) {
            try {
                if(networkTasks.size() == 0)
                    continue;

                NetworkTask task = networkTasks.dequeue();

                if(task.type == TaskType.OUT) {
                    dispatcher.dispatch(task.command);
                } else {
                    processCommand(task.command);
                }

                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void addNetworkActors(NetworkActor networkActor) {
        networkActors.add(networkActor);
    }

    public void addNetworkTask(NetworkTask networkTask) {
        networkTasks.enqueue(networkTask);
    }

    public void processCommand(String command) {
        StringTokenizer st = new StringTokenizer(command);
        String method;

        //while(st.hasMoreTokens()) {
        method = st.nextToken();

        switch(method) {
            case "NEW":
                NetDummy newAlien = new NetDummy();
                GameObject.instantiate(newAlien);
                break;
            case "UPDATE":
                String id = st.nextToken();
                for(NetworkActor actor : networkActors) {
                    if(actor.objectId.equals(id)) {
                        String toReplace = "UPDATE " + id;
                        String processedCommand = command.replace(toReplace, "");
                        actor.applyActor(processedCommand);
                    }
                }
                break;
        }
        //}
    }

    public static void main(String[] args) {
        NetworkClient client = new NetworkClient();
        client.connect();
    }
}