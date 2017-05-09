import java.io.IOException;
import java.io.DataInputStream;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

import java.util.ArrayList;
import java.util.NoSuchElementException;


public class NetworkServer {
    public static NetworkServer instance;

    private ServerSocket serverSocket;
    private int numPlayers;
    private boolean running;

    private ArrayList<NetworkServerClientConnection> connections = new ArrayList<>();
    public Queue<NetworkTask> networkTasks = new Queue<NetworkTask>();
    
    private final int PORT = 8888;
    private final int MAX_PLAYERS = 1;
    private final int SOCKET_TIMEOUT = 100000;

    public NetworkServer() {
        this.instance = this;

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
                numPlayers++;
                System.out.println("Player Connected");

                running = true;
            }
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

            /*for(NetworkServerClientConnection connection : connections) {
                connection.dispatch("Hellooooooo From Server");
            }*/

            if(networkTasks.size() == 0) 
                continue;

            NetworkTask task = networkTasks.dequeue();
        
            for(NetworkServerClientConnection connection : connections) {
                connection.dispatch(task.command);
            }

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