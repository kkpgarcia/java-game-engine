import java.io.Serializable;

public class NetworkTask implements Serializable{
    public TaskType type;
    public String id;
    public String actorId;
    public float x;
    public float y;
    public String action;

    public NetworkTask() {
        id = "";
        actorId = "";
        action = "";
    }
}