import java.io.IOException;
import java.io.DataInputStream;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class NetworkServer {
    public static NetworkServer instance;

    private ServerSocket serverSocket;
    private int numPlayers;
    private boolean running;

    private ArrayList<NetworkServerClientConnection> connections = new ArrayList<>();
    public Queue<NetworkTask> networkTasks;
    
    private final int PORT = 8888;
    private final int MAX_PLAYERS = 2;
    private final int SOCKET_TIMEOUT = 100000;

    public NetworkServer() {
        this.instance = this;

        networkTasks = new Queue<NetworkTask>();

        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(SOCKET_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnections() {
        running = false;

        try {
            while(numPlayers != MAX_PLAYERS) {
                System.out.println("Waiting for connections...");
                Socket socket = serverSocket.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String id = input.readUTF();
                createServerClientConnection(id, socket);
                
                NetworkTask registerTask = new NetworkTask();
                registerTask.command = "REGISTER " + id;
                addNetworkTask(registerTask);

                numPlayers++;
                System.out.println("Player Connected");
            }
            running = true;
        } catch(SocketTimeoutException e) {
            System.out.println("Socket timed out!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            
            if(running) {
                for(NetworkServerClientConnection connection : connections) {
                    connection.initialize();
                }
            }

            runServer();
        }
    }

    public void runServer() {
        while(running) {
            if(connections.size() == 0)
                break;

            if(networkTasks.size() == 0) 
                continue;

            NetworkTask task = networkTasks.dequeue();
            processCommand(task.command);

            try {
                Thread.sleep(10);
            } catch (Exception e){
                e.printStackTrace();
                break;
            }
        }

        System.out.println("Server ended.");
    }

    public void addNetworkTask(NetworkTask networkTask) {
        networkTasks.enqueue(networkTask);
    }

    public void processCommand(String command) {
        StringTokenizer st = new StringTokenizer(command);
        String method;
        String newCommand = "";
        String id;

        //while(st.hasMoreTokens()) {
        if(!st.hasMoreTokens())
            return;
        
        method = st.nextToken();
        id = st.nextToken();

        switch(method) {
            case "REGISTER":
                newCommand = "NEW " + id;
                System.out.println("Registered!");
                System.out.println(newCommand);
                break;
            case "UPDATE":
                newCommand = command;
                break;
        }
        //}

        if(newCommand.equals(null) || newCommand.equals(""))
            return;

        for(NetworkServerClientConnection connection : connections) {
            if(!id.equals(connection.id))
                connection.dispatch(newCommand);

            //System.out.println(newCommand);
        }
    }

    public void createServerClientConnection(String id, Socket socket) {
        NetworkServerClientConnection connection = new NetworkServerClientConnection(id, socket, serverSocket);
        connection.initialize();
        connections.add(connection);

        System.out.println("Created Server-Client Connection with id #" + String.valueOf(id));
    }
    
    public static void main (String[] args) {
        NetworkServer networkServer = new NetworkServer();
        networkServer.acceptConnections();
    }
}