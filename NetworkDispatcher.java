import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkDispatcher implements Runnable {
    private DataOutputStream output;
    private volatile Queue<NetworkTask> networkTasks;
    private boolean connected;

    private final int SLEEP_AMOUNT = 1;

    public NetworkDispatcher(DataOutputStream output) {
        networkTasks = new Queue<NetworkTask>();
        this.output = output;
    }
    
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

    private String createTask(NetworkTask task) {
        StringBuilder builder = new StringBuilder();
        builder.append(TaskType.toInt(task.type)).append(" ");
        builder.append(task.id).append(" ");
        builder.append(task.actorId).append(" ");
        builder.append(task.x).append(" ");
        builder.append(task.y).append(" ");
        builder.append(task.action);
        return builder.toString();
    }

    public void dispatch(NetworkTask task) {
        networkTasks.enqueue(task);
    }
}