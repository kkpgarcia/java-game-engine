import java.io.DataInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class NetworkListener implements Runnable {
    private DataInputStream input;
    private boolean connected;
    private volatile Queue<NetworkTask> networkTasks;
;
    private final int SLEEP_AMOUNT = 1;

    public NetworkListener(DataInputStream input) {
        networkTasks = new Queue<NetworkTask>();
        this.input = input;
    }

    public void run() {
        connected = true;
            try {
                 while(connected) {
                    try {
                        String task = input.readUTF();
                        networkTasks.enqueue(parseTask(task));
                    } catch(SocketTimeoutException se) {
                    } catch(EOFException ee) {
                    } catch(ClassCastException ce) {
                        continue;
                    }
                 }
            } catch (Exception e) {
                e.printStackTrace();
                connected = false;
            }
    }

    private NetworkTask parseTask(String task) {
        NetworkTask newTask = new NetworkTask();
        StringTokenizer token = new StringTokenizer(task);

        if(!token.hasMoreTokens())
            return null;

        newTask.type = TaskType.fromInt(Integer.parseInt(token.nextToken()));
        newTask.command = token.nextToken();
        newTask.id = token.nextToken();
        newTask.actorId = token.nextToken();
        newTask.x = Float.parseFloat(token.nextToken());
        newTask.y = Float.parseFloat(token.nextToken());
        try {
            newTask.action = token.nextToken();
        } catch (NoSuchElementException ne) {
            //System.out.println("No action parsed");
        }

        return newTask;
    }

    public NetworkTask listen() {
        if(networkTasks.size() != 0)
            return networkTasks.dequeue();
        else
            return null;
    }
}