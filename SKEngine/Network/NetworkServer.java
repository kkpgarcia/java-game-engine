package SKEngine.Network;

import SKEngine.Collections.Dictionary;
import SKEngine.Collections.Queue;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.UUID;

/**
 * <h2>Network Server</h2>
 * A server implementation that accepts more than one client.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-09-05
 * */
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

    /**
     * Creates and initializes the Server.
     * */
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

    /**
     * Runs the server.
     * */
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

    /**
     * Accepts connections when ever it is available.
     * */
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

    /**
     * Creates a new client connection, creates a listener, and a dispatcher 
     * and maps the connection to a dictionary with a unique identification string.
     * @param Socket socket of the client.
     * @see NetworkClientConnection - SKEngine.Network
     * */
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

    /**
     * Resolves the client connections to each other. This function sends new information to
     * existing clients, and sends the information of the existing clients to the new client.
     * @param NetworkClientConnection new client
     * @see NetworkClientConnection - SKEngine.Network
     * @see NetworkTask - SKEngine.Network
     * */
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
            task.command = "COMMAND_CREATE";
            System.out.println("SENDING COMMAND_CREATE TO: " + id);
            client.dispatch(task);

            NetworkTask task2 = new NetworkTask();
            //task.id = newClient.id;
            task2.actorId = id;
            task2.command = "COMMAND_CREATE";
            newClient.dispatch(task2);
            System.out.println("SENDING COMMAND_CREATE TO: " + newClient.id);
        }

        idList.add(newClient.id);
    }

    /**
     * Checks if a network task exists on the listener queue. If
     * its not empty, it drags out of the listener, and put it in the
     * main server task queue.
     * @see NetworkClientConnection - SKEngine.Network
     * */
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

    /**
     * Updates the connection if the network task queue is not empty.
     * Checks the appropriate id for the update, and sends the task through
     * the network client connection.
     * @see NetworkClientConnection - SKEngine.Network
     * */
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

    /**
     * Enqueues a task on the server task queue.
     * @param NetworkTask task
     * */
    public void addNetworkTask(NetworkTask task) {
        networkTasks.enqueue(task);
    }
}