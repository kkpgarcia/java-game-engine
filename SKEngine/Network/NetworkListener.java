package SKEngine.Network;

import SKEngine.Collections.Queue;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

/**
 * <h2>Network Listener</h2>
 * This class handles all the incoming messages of the server/client.
 * It parses the string to a task, and enqueues it. This class
 * inherits runnable so that it would run on a thread.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-09-05
 * */

public class NetworkListener implements Runnable {
    private DataInputStream input;
    private boolean connected;
    private volatile Queue<NetworkTask> networkTasks;

    private final int SLEEP_AMOUNT = 1;

    /**
     * Constructs and initializes the listener class
     * @param DataInputStream input stream of a socket
     * */
    public NetworkListener(DataInputStream input) {
        networkTasks = new Queue<NetworkTask>();
        this.input = input;
    }

    /**
     * This function is implemented from the runnable implementations.
     * It constantly checks for data to read. Once finished reading, it
     * parses the string, and enqueues it to the listener queue until the
     * server/client listens to this class.
     * */
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

    /**
     * Simple straightforward string parsing to task.
     * @param String message to be parsed
     * @return NetworkTask created task
     * */
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
        }

        return newTask;
    }

    /**
     * This function simply dequeues task to the server/client queue.
     * @return NetworkTask task from the server/client
     * */
    public NetworkTask listen() {
        if(networkTasks.size() != 0)
            return networkTasks.dequeue();
        else
            return null;
    }
}