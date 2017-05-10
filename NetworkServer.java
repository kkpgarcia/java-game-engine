import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.UUID;

public class NetworkServer {
    private ServerSocket server;
    private boolean running;

    private ArrayList<String> idList;
    private Dictionary<String, NetworkClientConnection> clients;
    private Queue<NetworkTask> networkTasks;

    private final int PORT = 8888;
    private final int SOCKET_TIMEOUT = 1000;

    public NetworkServer() {
        idList = new ArrayList<String>();
        networkTasks = new Queue<NetworkTask>();
        clients = new Dictionary<String, NetworkClientConnection>();
        try {
            server = new ServerSocket(PORT);
            server.setSoTimeout(SOCKET_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runServer() {
        running = true;

        while(running) {
            acceptConnections();
            listenToConnections();
            updateConnections();
        }
    }

    private void acceptConnections() {
        try {
            //System.out.println("Waiting for connections...");
            Socket socket = server.accept();

            if(!socket.isConnected())
                return;

            createNewClientConnection(socket);
        } catch(SocketTimeoutException se) {
            //System.out.println("Socket timed out!");
        } catch (IOException e) {
            e.printStackTrace();
            running = false;
        }
    }

    private void createNewClientConnection(Socket socket) {
        String id = UUID.randomUUID().toString();
        NetworkClientConnection client = new NetworkClientConnection(id, socket);
        
        try {
            clients.add(id, client);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(String currentId : idList) {
            NetworkTask task = new NetworkTask();
            task.id = id;
            task.type = TaskType.NEW;
            task.action = "CREATE";
            addNetworkTask(task);
        }

        System.out.println("New Connection added " + id);

        idList.add(id);
    }

    private void listenToConnections() {
        if(clients.size() == 0)
            return;

        for(String id : idList) {
            NetworkClientConnection client = clients.getValue(id);

            if(client == null)
                continue;

            NetworkTask newTask = client.listen();
            if(newTask != null)
                addNetworkTask(newTask);
        }
    }

    private void updateConnections() {
        if(networkTasks.isEmpty())
            return;

        NetworkTask currentTask = networkTasks.dequeue();

        if(currentTask == null || clients.isEmpty())
            return;

        if(clients.size() <= 1)
            return;

        for(String id : idList) {
            if(id.equals(currentTask.id))
                continue;

            NetworkClientConnection client = clients.getValue(currentTask.id);

            if(client == null)
                continue;

            client.dispatch(currentTask);
        }
    }

    public void addNetworkTask(NetworkTask task) {
        networkTasks.enqueue(task);
    }

    public static void main(String[] args) {
        NetworkServer networkServer = new NetworkServer();
        networkServer.runServer();
    }
}