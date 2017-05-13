package SKEngine.Test;

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
    public ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public void createScene(Camera camera) {
        NetworkClient networkClient = new NetworkClient();
        
        Alien alien = new Alien();
        alien.input = input;
        alien.bindInput();
        alien.networkActor = new NetworkActor("main", alien, networkClient);

        camera.follow(alien);
        objects.add(alien);

        int brickAmount = 20;
        Platform platform = new Platform(new Vector2(0, 200), brickAmount);
        objects.add(platform);

        for(int i = 0; i < brickAmount; i++) {
            objects.add(platform.platforms[i]);
        }

        networkClient.addNewClientConnectionAction(new NetworkClientCallback() {
            public void onExecute(NetworkTask task, NetworkClient client) {
                Dummy dummy = new Dummy();
                dummy.networkActor = new NetworkActor(task.actorId, dummy, client);
                GameObject.instantiate(dummy);
            }
        });
        networkClient.connect();
    }
}