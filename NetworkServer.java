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
    private final int SOCKET_TIMEOUT = 3;
    private final int MAX_PLAYERS = 2;

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
                Thread.sleep(1);
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
    
        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            NetworkClientConnection client = new NetworkClientConnection(id, input, output);
            clients.add(id, client);
            client.initialize();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        //for(int i = 0; i < 100; i++) {
        NetworkTask task = new NetworkTask();
        task.id = id;
        task.type = TaskType.UPDATE;
        task.action = "COMMAND CREATE";

        addNetworkTask(task);
        //}

        System.out.println("New Connection added " + id);
        
        idList.add(id);
        numPlayers++;
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

        //if(clients.size() <= 1)
            //return;

        for(String id : idList) {
            //if(id.equals(currentTask.id))
              //  continue;
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