import java.io.Serializable;

public class NetworkTask implements Serializable{
    public TaskType type;
    public String id;
    public String actorId;
    public float x;
    public float y;
    public String action;

    public NetworkTask() {
        id = "null";
        actorId = "null";
        action = "null";
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("    TYPE: ").append(type).append("\n");
        builder.append("    ID: ").append(id).append("\n");
        builder.append("    ACTOR ID: ").append(actorId).append("\n");
        builder.append("    X: ").append(x).append("\n");
        builder.append("    Y: ").append(y).append("\n");
        builder.append("    ACTION: ").append(action);
        return builder.toString();
    }
}