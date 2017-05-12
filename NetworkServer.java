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
    private int numPlayers = 0;

    private ArrayList<String> idList;
    private Dictionary<String, NetworkClientConnection> clients;
    private Queue<NetworkTask> networkTasks;

    private final int PORT = 8888;
    private final int SOCKET_TIMEOUT = 5;
    private final int MAX_PLAYERS = 2;
    private final int SLEEP_AMOUNT = 1;

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
            //if(numPlayers != MAX_PLAYERS)
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
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            client = new NetworkClientConnection(id, input, output);
            clients.add(id, client);
            client.initialize();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("NEW CLIENT ADDED WITH ID#: " + id);
        System.out.println("CLIENT COUNT: " + clients.size());
        
        //idList.add(id);
        if(client != null);
        resolveClients(client);
    }

    private void resolveClients(NetworkClientConnection newClient) {
        if(idList.size() == 0) {
            idList.add(newClient.id);
            return;
        }

        if(clients.size() < 1 || newClient == null)
            return;

        for(String id : idList) {
            NetworkClientConnection client = clients.getValue(id);
            NetworkTask task = new NetworkTask();
            //task.id = client.id;
            task.actorId = newClient.id;
            task.action = "COMMAND_CREATE";
            System.out.println("SENDING COMMAND_CREATE TO: " + id);
            client.dispatch(task);

            NetworkTask task2 = new NetworkTask();
            //task.id = newClient.id;
            task2.actorId = id;
            task2.action = "COMMAND_CREATE";
            newClient.dispatch(task2);
            System.out.println("SENDING COMMAND_CREATE TO: " + newClient.id);
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
                
            if(currentTask.id == null || currentTask.id.equals("null")) {
                continue;
            }

            NetworkClientConnection client = clients.getValue(id);
            
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