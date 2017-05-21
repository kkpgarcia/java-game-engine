package Game;

import Editor.Exporter;
import SKEngine.Core.Vector2;

public class Level {
    public Level() {
        createLevel();
    }

    public void createLevel() {
        createMap();

        //Apply create new platform colliders
        PlatformCollider collider1 = new PlatformCollider(new Vector2(0,0), 100,100);
        PlatformCollider collider2 = new PlatformCollider(new Vector2(100,0), 100,100);
    }

    public void createMap() {
        Exporter.load("map.sk");
    }
}