import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkServerDispatcher implements Runnable {
    private String id;
    private NetworkServerClientConnection connection;
    private Socket socket;
    private DataOutputStream output;

    public NetworkServerDispatcher(String id, Socket socket, NetworkServerClientConnection connection) {
        this.connection = connection;
        this.socket = socket;

        try {
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dispatch(String command) {
        try {
            output.writeUTF(command);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {}
}