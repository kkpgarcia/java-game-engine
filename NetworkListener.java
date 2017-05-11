import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class NetworkListener implements Runnable {
    private ObjectInputStream input;
    private boolean connected;
    private volatile Queue<NetworkTask> networkTasks;

    private int READ_TIMEOUT = 10;

    public NetworkListener(ObjectInputStream input) {
        networkTasks = new Queue<NetworkTask>();
        this.input = input;
    }

    public void run() {
        connected = true;
            try {
                 while(connected) { 
                    NetworkTask task = (NetworkTask)input.readObject();
                    networkTasks.enqueue(task);
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                        connected = false;
                    }
                 }
            } catch (Exception e) {
                e.printStackTrace();
                connected = false;
            }
    }

    public NetworkTask listen() {
        if(networkTasks.size() != 0)
            return networkTasks.dequeue();
        else
            return null;
    }
}