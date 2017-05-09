import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkServerListener implements Runnable {
    private String id;
    private NetworkServerClientConnection connection;
    private Socket socket;
    private DataInputStream input;
    private boolean connected;

    public NetworkServerListener(String id,Socket socket, NetworkServerClientConnection connection) {
        this.connection = connection;
        this.socket = socket;

        try {
            input = new DataInputStream(socket.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected) {
            try {
                String in = input.readUTF();
                NetworkTask networkTask = new NetworkTask();
                networkTask.id = id;
                networkTask.command = in;
                
                if(in != null || !in.equals("")) {
                    NetworkServer.instance.addNetworkTask(networkTask);
                    
                }
                Thread.sleep(10);
            } catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }
}