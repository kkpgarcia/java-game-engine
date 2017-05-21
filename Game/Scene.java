package Game;

import SKEngine.Core.Input;
import SKEngine.Core.GameObject;
import SKEngine.Network.NetworkClient;
import SKEngine.Network.NetworkActor;
import SKEngine.Network.NetworkClientCallback;
import SKEngine.Network.NetworkTask;

public class Scene {
    public Input input;
    public NetworkClient networkClient;

    public void createScene() {
        Level level = new Level();

        Alien alien = new Alien();
        alien.input = input;
        alien.bindInput();
        alien.networkActor = new NetworkActor("main", alien, networkClient);

        networkClient.addNewClientConnectionAction(new NetworkClientCallback() {
             public void onExecute(NetworkTask task, NetworkClient client) {
                Dummy dummy = new Dummy();
                dummy.networkActor = new NetworkActor(task.actorId, dummy, client);
                GameObject.instantiate(dummy);
            }
        });
    }
}