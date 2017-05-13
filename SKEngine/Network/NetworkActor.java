package SKEngine.Network;

import SKEngine.Core.GameObject;

/**
 * <h2>Network Actor</h2>
 * A simple network AI that simply updates a game object
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-09-05
 * */
public class NetworkActor {
    public String id;
    private NetworkClient client;
    private GameObject gameObject;
    public String action = "";

    /**
     * Constructs and initializes the network actor
     * @param String id of the actor
     * @param GameObject object to be acted by the actor
     * @param NetworkClient the current client
     * */
    public NetworkActor(String id, GameObject obj, NetworkClient client) {
        this.gameObject = obj;
        this.client = client;
        this.id = id;
        client.addNetworkActor(this);
    }

    /**
     * Constantly creates a network task to be sent out to the
     * server, and to its actor in a different client.
     * @see NetworkClient - SKEngine.Network
     * */
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

    /**
     * Constantly applies the information from the server
     * to the game object that needs to be updated remotely.
     * @param NetworkTask task to be accomplished.
     * */
    public void applyActor(NetworkTask task) {
        gameObject.transform.position.x = task.x;
        gameObject.transform.position.y = task.y;
        gameObject.onNetworkEvent(task.action);
    }
}