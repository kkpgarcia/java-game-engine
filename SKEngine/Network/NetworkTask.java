package SKEngine.Network;

import java.io.Serializable;

/**
 * <h2>Network Task</h2>
 * A command/message container to be passed along the network client
 * or network server.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-09-05
 * */
public class NetworkTask implements Serializable{
    public TaskType type;
    public String command;
    public String id;
    public String actorId;
    public float x;
    public float y;
    public String action;

    /**
     * Constructs and initializes the network task
     * */
    public NetworkTask() {
        id = "null";
        actorId = "null";
        action = "null";
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("    TYPE: ").append(type).append("\n");
        builder.append("    COMMAND: ").append(command).append("\n");
        builder.append("    ID: ").append(id).append("\n");
        builder.append("    ACTOR ID: ").append(actorId).append("\n");
        builder.append("    X: ").append(x).append("\n");
        builder.append("    Y: ").append(y).append("\n");
        builder.append("    ACTION: ").append(action);
        return builder.toString();
    }
}