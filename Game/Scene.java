package Game;

import SKEngine.Core.Input;
import SKEngine.Core.GameObject;
import SKEngine.Network.NetworkClient;
import SKEngine.Network.NetworkActor;
import SKEngine.Network.NetworkClientCallback;
import SKEngine.Network.NetworkTask;
import SKEngine.Core.Vector2;

public class Scene {
    public Input input;
    public NetworkClient networkClient;

    public void createScene() {
        Background bg = new Background();
        Level level = new Level();

        Alien alien = new Alien();
        alien.input = input;
        alien.bindInput();
        alien.networkActor = new NetworkActor("main", alien, networkClient);
        alien.rigidbody.position = new Vector2(-400, 200);

        Exit exit = new Exit();


        networkClient.addNewClientConnectionAction(new NetworkClientCallback() {
             public void onExecute(NetworkTask task, NetworkClient client) {
                Dummy dummy = new Dummy();
                //dummy.rigidbody.position = new Vector2(400, 200);
                //GameObject.instantiate(dummy);
                dummy.networkActor = new NetworkActor(task.actorId, dummy, client);
            }
        });
    }
}