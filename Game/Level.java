package Game;

import Editor.Exporter;

public class Level {
    public Level() {
        createLevel();
    }

    public void createLevel() {
        createMap();

        //Apply create new platform colliders

    }

    public void createMap() {
        Exporter.load("map.sk");
    }
}