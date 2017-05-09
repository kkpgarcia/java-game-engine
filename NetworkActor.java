import java.util.UUID;

public class NetworkActor {

    public int objectId;
    public GameObject networkObject;

    public NetworkActor(GameObject obj) {
        this.networkObject = obj;
        this.objectId = UUID.randomUUID().variant();
    }

    public void updateActor() {

    }
}