package SKEngine.Network;

import SKEngine.Collections.Queue;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * <h2>Network Dispatcher</h2>
 * This class handles all the outgoing messages of the server/client.
 * It composes the network task to a string, and sends it out. This class
 * inherits runnable so that it would run on a thread.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-09-05
 * */
public class NetworkDispatcher implements Runnable {
    private DataOutputStream output;
    private volatile Queue<NetworkTask> networkTasks;
    private boolean connected;

    private final int SLEEP_AMOUNT = 1;

    /**
     * Constructs and initializes the dispatcher class
     * @param DataOutputStream output stream of a socket
     * */
    public NetworkDispatcher(DataOutputStream output) {
        networkTasks = new Queue<NetworkTask>();
        this.output = output;
    }
    
    /**
     * This function is implemented from the runnable implementations.
     * It checks if the dispatcher queue is empty. If its not, it dequeues
     * a task, composes it to string, and sends it out to its corresponding 
     * server/client socket.
     * */
    public void run() {
        connected = true;
        try {
            while(connected) {
                if(!networkTasks.isEmpty()) {
                    NetworkTask task = networkTasks.dequeue();
                    if(task == null)
                        continue;
                    try {
                        String taskString = createTask(task);
                        output.writeUTF(taskString);
                        output.flush();
                        Thread.sleep(SLEEP_AMOUNT);
                    } catch(NullPointerException ne) {
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

    /**
     * Simple straightforward task composition to string.
     * @param NetworkTask task to be composed
     * @return String composed task
     * */
    private String createTask(NetworkTask task) {
        StringBuilder builder = new StringBuilder();
        builder.append(TaskType.toInt(task.type)).append(" ");
        builder.append(task.command).append(" ");
        builder.append(task.id).append(" ");
        builder.append(task.actorId).append(" ");
        builder.append(task.x).append(" ");
        builder.append(task.y).append(" ");
        builder.append(task.action);
        return builder.toString();
    }

    /**
     * This function simply enqueues task to the dispatcher queue.
     * @param NetworkTask task to be enqueued.
     * */
    public void dispatch(NetworkTask task) {
        networkTasks.enqueue(task);
    }
}