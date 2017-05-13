package SKEngine.Test;

import SKEngine.Core.GameObject;
import SKEngine.Core.Input;
import java.util.ArrayList;

public class Scene {
    public Input input;
    public ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public void createScene() {
        Alien alien = new Alien();
        alien.input = input;
        alien.bindInput();
        objects.add(alien);
    }
}