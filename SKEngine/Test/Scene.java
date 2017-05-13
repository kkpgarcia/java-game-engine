package SKEngine.Test;

import SKEngine.Core.Camera;
import SKEngine.Core.GameObject;
import SKEngine.Core.Input;
import SKEngine.Core.Vector2;
import java.util.ArrayList;

public class Scene {
    public Input input;
    public ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public void createScene(Camera camera) {

        Alien alien = new Alien();
        alien.input = input;
        alien.bindInput();
        camera.follow(alien);
        objects.add(alien);

        int brickAmount = 20;
        Platform platform = new Platform(new Vector2(0, 200), brickAmount);
        objects.add(platform);

        for(int i = 0; i < brickAmount; i++) {
            objects.add(platform.platforms[i]);
        }
    }
}