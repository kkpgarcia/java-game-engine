public class NetworkActor {
    public String id;
    private NetworkClient client;
    private GameObject gameObject;
    public String action = "";

    public NetworkActor(String id, GameObject obj, NetworkClient client) {
        this.gameObject = obj;
        this.client = client;
        this.id = id;
        client.addNetworkActor(this);
    }

    public void updateActor() {
        NetworkTask task = new NetworkTask();
        task.type = TaskType.UPDATE;
        task.actorId = id;
        task.x = gameObject.transform.position.x;
        task.y = gameObject.transform.position.y;
        task.command = "COMMAND_UPDATE";
        task.action = action;

        client.addNetworkTask(task);
        action = "";
    }

    public void applyActor(NetworkTask task) {
        gameObject.transform.position.x = task.x;
        gameObject.transform.position.y = task.y;
        gameObject.onNetworkEvent(task.action);
    }
}