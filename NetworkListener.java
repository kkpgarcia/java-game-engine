import java.io.DataInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkListener implements Runnable {
    private DataInputStream input;
    private boolean connected;
    private Queue<NetworkTask> networkTasks;

    public NetworkListener(Socket socket) {
        networkTasks = new Queue<NetworkTask>();

        try {
            input = new DataInputStream(socket.getInputStream());
            if(input != null)
                connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected) {
            try {
                NetworkTask task = new NetworkTask();
                TaskType type = TaskType.fromInt(input.readInt());
                task.id = input.readUTF();
                task.x = input.readFloat();
                task.y = input.readFloat();
                task.action = input.readUTF();

                networkTasks.enqueue(task);
            } catch (EOFException e) {
                connected = false;
                break;
            } catch (IOException e) {
                connected = false;
                break;
            }
        }
    }

    public NetworkTask listen() {
        if(networkTasks.size() != 0)
            return networkTasks.dequeue();
        else
            return null;
    }
}