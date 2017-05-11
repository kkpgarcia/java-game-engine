import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.UUID;

public class NetworkServer {
    private ServerSocket server;
    private boolean running;
    private int numPlayers = 0;

    private ArrayList<String> idList;
    private Dictionary<String, NetworkClientConnection> clients;
    private Queue<NetworkTask> networkTasks;

    private final int PORT = 8888;
    private final int SOCKET_TIMEOUT = 120;
    private final int MAX_PLAYERS = 2;
    private final int SLEEP_AMOUNT = 100;

    private int it = 0;

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
            if(numPlayers != MAX_PLAYERS)
                acceptConnections();

            listenToConnections();
            updateConnections();

            try {
                Thread.sleep(SLEEP_AMOUNT);
            } catch(Exception e) {
                e.printStackTrace();
            }
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
        NetworkClientConnection client = null;
        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            client = new NetworkClientConnection(id, input, output);
            clients.add(id, client);
            client.initialize();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("New Connection added " + id);
        
        //idList.add(id);
        resolveClients(client);
        numPlayers++;
    }

    private void resolveClients(NetworkClientConnection newClient) {
        if(idList.size() == 0) {
            idList.add(newClient.id);
            return;
        }

        if(clients.size() <= 1 || newClient == null)
            return;

        for(String id : idList) {
            NetworkClientConnection client = clients.getValue(id);
            NetworkTask task = new NetworkTask();
            task.id = id;
            task.actorId = newClient.id;
            task.action = "COMMAND CREATE";
            client.dispatch(task);

            task.id = newClient.id;
            task.actorId = id;
            task.action = "COMMAND CREATE";
            newClient.dispatch(task);
        }

        idList.add(newClient.id);
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

        if(clients.isEmpty())
            return;

        if(clients.size() <= 1)
            return;

        for(String id : idList) {
            if(id.equals(currentTask.id))
                continue;
            //System.out.println("Looking for " + currentTask.id + " " + String.valueOf(it++));
            if(currentTask.id == null) {
                continue;
            }
            NetworkClientConnection client = clients.getValue(currentTask.id);
            
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