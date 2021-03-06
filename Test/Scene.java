package Test;

import SKEngine.Core.Camera;
import SKEngine.Core.GameObject;
import SKEngine.Core.Input;
import SKEngine.Core.Vector2;
import SKEngine.Network.NetworkClient;
import SKEngine.Network.NetworkActor;
import SKEngine.Network.NetworkClientCallback;
import SKEngine.Network.NetworkTask;
import java.util.ArrayList;

public class Scene {
    public Input input;

    public void createScene(Camera camera) {
        NetworkClient networkClient = new NetworkClient();
        
        
        int brickAmount = 20;
        Platform platform = new Platform(new Vector2(0, 200), brickAmount);

        
        Alien alien = new Alien();
        alien.input = input;
        alien.bindInput();
        alien.networkActor = new NetworkActor("main", alien, networkClient);
        alien.rigidbody.position = new Vector2(-400, 200);
        Enemy enemy = new Enemy();
        
        Switch t = new Switch();
        
        camera.follow(alien);

        networkClient.addNewClientConnectionAction(new NetworkClientCallback() {
            public void onExecute(NetworkTask task, NetworkClient client) {
                Dummy dummy = new Dummy();
                dummy.networkActor = new NetworkActor(task.actorId, dummy, client);
                alien.transform.position = new Vector2(400, 200);
                //GameObject.instantiate(dummy);
            }
        });

        networkClient.connect("localhost", 8888);
    }
}