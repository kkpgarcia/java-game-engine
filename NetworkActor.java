import java.util.UUID;
import java.util.StringTokenizer;

public class NetworkActor {

    public String objectId;
    private GameObject networkObject;

    public NetworkActor(GameObject obj) {
        this.networkObject = obj;
        this.objectId = UUID.randomUUID().toString();
    }

    public NetworkActor(GameObject obj, String id) {
        this.networkObject = obj;
        this.objectId = id;
    }

    public void registerActor() {
        NetworkTask task = new NetworkTask();
        task.type = TaskType.OUT;
        task.command = "REGISTER " + objectId;

        NetworkClient.instance.addNetworkTask(task);
    }

    public void updateActor(GameObject object) {
        String transform = createTransformCommand(object.transform);

        NetworkTask task = new NetworkTask();
        task.type = TaskType.OUT;
        task.command = "UPDATE " + objectId + " " + transform;

        NetworkClient.instance.addNetworkTask(task);
    }

    public void applyActor(String command) {
        StringTokenizer st = new StringTokenizer(command);

        float x = Float.parseFloat(st.nextToken());
        float y = Float.parseFloat(st.nextToken());

        networkObject.transform.position.x = x;
        networkObject.transform.position.y = y;
    }

    public String createTransformCommand(Transform transform) {
        return String.valueOf(networkObject.transform.position.x) + " " + String.valueOf(networkObject.transform.position.y);
    }
}