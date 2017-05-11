import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkDispatcher implements Runnable {
    private ObjectOutputStream output;
    private volatile Queue<NetworkTask> networkTasks;
    private boolean connected;

    private final int SLEEP_AMOUNT = 100;

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
                        Thread.sleep(SLEEP_AMOUNT);
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