import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkDispatcher implements Runnable {
    private DataOutputStream output;
    private Queue<NetworkTask> networkTasks;
    private boolean connected;

    public NetworkDispatcher(Socket socket) {
        networkTasks = new Queue<NetworkTask>();
        try {
            output = new DataOutputStream(socket.getOutputStream());
            if(output != null)
                connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        while(connected) {
            if(networkTasks.isEmpty())
                return;
            
            try {
                NetworkTask task = networkTasks.dequeue();
                output.writeInt(TaskType.toInt(task.type));
                output.writeFloat(task.x);
                output.writeFloat(task.y);
                output.writeUTF(task.action);
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
                connected = false;
                break;
            }
        }
    }

    public void dispatch(NetworkTask task) {
        networkTasks.enqueue(task);
    }
}