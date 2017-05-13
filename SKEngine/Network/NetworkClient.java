package SKEngine.Network;

import SKEngine.Collections.Dictionary;
import SKEngine.Collections.Queue;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;

import java.util.UUID;
import java.util.ArrayList;

/**
 * <h2>Network Server</h2>
 * A client implementation for multiplayer games.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-09-05
 * */
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

    /**
     * Creates and initializes the client.
     * */
    public NetworkClient() {
        networkTasks = new Queue<NetworkTask>();
        actorId = new ArrayList<String>();
        networkActors = new Dictionary<String, NetworkActor>();
    }

    /**
     * Connects the client to an available server.
     * */
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

    /**
     * Creates threads for the listener, and the dispatcher, and runs
     * the client update via thread.
     * */
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

    /**
     * Check if a network task exists on the listener queue.
     * If its not empty, it drags out of the listener, and put the
     * task to the main client task queue.
     * @see NetworkListener - SKEngine.Network
     * */
    private void listenToServer() {
        NetworkTask task = listener.listen();
        if(task != null) {
            task.type = TaskType.IN;
            addNetworkTask(task);
        }
    }

    /**
     * Handles all the tasks given by the server, and the client
     * itself. It segregates all the network from IN, and OUT.
     * @see TaskType - SKEngine.Network
     * @see NetworkListener -SKEngine.Network
     * */
    private void handleTasks() {
        if(networkTasks.isEmpty())
            return;

        NetworkTask task = networkTasks.dequeue();
        
        if(task.type == TaskType.IN)
            updateClient(task);
        else
            updateServer(task);
    }

    /**
     * Update the client by doing a client command.
     * @param NetworkTask task to be accomplished
     * */
    public void updateClient(NetworkTask task) {
        if(task == null)
            return;

        doClientCommand(task);
    }

    /**
     * Checks if the command is the ff:
     * <p>
     * <b>COMMAND_UPDATE</b> - update command to update specific actors
     * <p>
     * <b>COMMAND_CREATE</b> - when a new client is connected
     * <p>
     * <b>COMMAND_REMOVE</b> - when a client disconnects
     * <p>
     * <b>COMMAND_ON_CONNECT</b> - when this client connects to a server
     * @param NetworkTask current task
     * */
    private void doClientCommand(NetworkTask task) {
        String command = task.command;
        
        switch(command) {
            case "COMMAND_UPDATE":
                if(onUpdateActions != null) {
                    for(NetworkClientCallback callback : onUpdateActions)
                        callback.onExecute(task, this);
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
                        callback.onExecute(task, this);
                }
                break;
            case "COMMAND_REMOVE":
                if(onClientDisconnectActions != null) {
                    for(NetworkClientCallback callback : onClientDisconnectActions)
                        callback.onExecute(task, this);
                }
                break;
            case "COMMAND_ON_CONNECT":
                this.id = task.id;
                break;
        }
    }

    /**
     * Sends a task to a the dispatcher.
     * @param NetworkTask task to be sent out
     * */
    private void updateServer(NetworkTask task) {
        if(task == null)
            return;

        dispatcher.dispatch(task);
    }

    /**
     * Enqueues a network task to the client task queue
     * @param NetworkTask new task
     * */
    public void addNetworkTask(NetworkTask task) {
        networkTasks.enqueue(task);
    }

    /**
     * Add actions when the client recieves updates
     * @param NetworkClientCallback action to be executed
     * */
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

    /**
     * Add actions when the client recieves new client information
     * @param NetworkClientCallback action to be executed
     * */
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

    /**
     * Add actions when the client recieve other client disconnection
     * @param NetworkClientCallback action to be executed
     * */
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

    /**
     * Add network actors to be updated to the server/client
     * @param NetworkActor new network actor
     * @see NetworkActor - SKEngine.Network
     * */
    public void addNetworkActor(NetworkActor actor) {
        try {
            networkActors.add(actor.id, actor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        actorId.add(id);
    }
}