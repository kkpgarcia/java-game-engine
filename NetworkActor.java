import java.util.UUID;
import java.util.StringTokenizer;

public class NetworkActor {
    public String id;
    private NetworkClient client;
    private GameObject gameObject;

    public NetworkActor(GameObject obj, NetworkClient client) {
        this.gameObject = obj;
        this.client = client;
        client.addNetworkActor(this);
    }

    public void updateActor() {
        NetworkTask task = new NetworkTask();
        task.type = TaskType.UPDATE;
        task.actorId = id;
        task.x = gameObject.transform.position.x;
        task.y = gameObject.transform.position.y;
        task.action = "";

        client.addNetworkTask(task);
    }

    public void applyActor(NetworkTask task) {
        gameObject.transform.position.x = task.x;
        gameObject.transform.position.y = task.y;
    }
}