package SKEngine.Network;

import SKEngine.Collections.Dictionary;
import SKEngine.Collections.Queue;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;

import java.util.UUID;
import java.util.ArrayList;

public class NetworkClient {
    private String id;
    private Socket socket;
    private boolean connected = false;

    private NetworkDispatcher dispatcher;
    private NetworkListener listener;

    private ArrayList<NetworkClientCallback> onUpdateActions;
    private ArrayList<NetworkClientCallback> onNewClientActions;
    private ArrayList<NetworkClientCallback> onClientDisconnectActions;

    private volatile ArrayList<String> actorId;
    private volatile Dictionary<String,NetworkActor> networkActors;
    private volatile Queue<NetworkTask> networkTasks;

    private final int port = 8888;
    private final int SOCKET_TIMEOUT = 15;
    private final int SLEEP_AMOUNT = 1;

    public NetworkClient() {
        networkTasks = new Queue<NetworkTask>();
        actorId = new ArrayList<String>();
        networkActors = new Dictionary<String, NetworkActor>();
    }

    public void connect() {
        try {
            System.out.println("Connected to " + String.valueOf(port));
            socket = new Socket("localhost", port);
            socket.setSoTimeout(SOCKET_TIMEOUT);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

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
                        Thread.sleep(SLEEP_AMOUNT);
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
        
        if(task.type == TaskType.IN)
            updateClient(task);
        else
            updateServer(task);
    }

    public void updateClient(NetworkTask task) {
        if(task == null)
            return;

        doClientCommand(task);
    }

    private void doClientCommand(NetworkTask task) {
        String command = task.command;
        
        switch(command) {
            case "COMMAND_UPDATE":
                if(onUpdateActions != null) {
                    for(NetworkClientCallback callback : onUpdateActions)
                        callback.onExecute();
                }

                if(networkActors.size() <= 1)
                    return;
                NetworkActor actor = networkActors.getValue(task.id);
                if(actor == null)
                    return;
                actor.applyActor(task);
                break;
            case "COMMAND_CREATE":
                if(onNewClientActions != null) {
                    for(NetworkClientCallback callback : onNewClientActions)
                        callback.onExecute();
                }
                break;
            case "COMMAND_REMOVE":
                if(onClientDisconnectActions != null) {
                    for(NetworkClientCallback callback : onClientDisconnectActions)
                        callback.onExecute();
                }
                break;
            case "COMMAND_ON_CONNECT":
                this.id = task.id;
                break;
        }
    }

    private void updateServer(NetworkTask task) {
        if(task == null)
            return;

        dispatcher.dispatch(task);
    }

    public void addNetworkTask(NetworkTask task) {
        networkTasks.enqueue(task);
    }

    public void addOnUpdateActions(NetworkClientCallback callback) {
        if(onUpdateActions != null) {
            onUpdateActions.add(callback);
        } else {
            if(callback != null) {
                onUpdateActions = new ArrayList<NetworkClientCallback>();
                onUpdateActions.add(callback);
            }
        }
    }

    public void addNewClientConnectionAction(NetworkClientCallback callback) {
        if(onNewClientActions != null) {
            onNewClientActions.add(callback);
        } else {
            if(callback != null) {
                onNewClientActions = new ArrayList<NetworkClientCallback>();
                onNewClientActions.add(callback);
            }
        }
    }

    public void addClientDisconnectAction(NetworkClientCallback callback) {
        if(onClientDisconnectActions != null) {
            onClientDisconnectActions.add(callback);
        } else {
            if(callback != null) {
                onClientDisconnectActions = new ArrayList<NetworkClientCallback>();
                onClientDisconnectActions.add(callback);
            }
        }
    }

    public void addNetworkActor(NetworkActor actor) {
        try {
            networkActors.add(actor.id, actor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        actorId.add(id);
    }
}