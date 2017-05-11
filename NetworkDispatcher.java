import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkDispatcher implements Runnable {
    private ObjectOutputStream output;
    private volatile Queue<NetworkTask> networkTasks;
    private boolean connected;

    public NetworkDispatcher(ObjectOutputStream output) {
        networkTasks = new Queue<NetworkTask>();
        //try {
            this.output = output;//new ObjectOutputStream(socket.getOutputStream());
            //if(output != null)
              //  connected = true;
        //} catch (IOException e) {
          //  e.printStackTrace();
        //}
    }
    
    public void run() {
        connected = true;
        try {
            while(connected) {
                if(!networkTasks.isEmpty()) {
                    NetworkTask task = networkTasks.dequeue();
                    if(task == null)
                        continue;
                    output.writeObject(task);
                    output.flush();
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        connected = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            connected = false;
        }
    }

    public void dispatch(NetworkTask task) {
        networkTasks.enqueue(task);
    }
}