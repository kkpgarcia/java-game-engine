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
        
        //middle middle
        PlatformCollider collider1 = new PlatformCollider(new Vector2(-6,-80), 125,20);
        PlatformBounding bounding1 = new PlatformBounding(new Vector2(260,100), new Vector2(0,50), new Vector2(-110,-80));
        
        //lower right
        PlatformCollider collider2 = new PlatformCollider(new Vector2(435,110), 125,20);
        PlatformBounding bounding2 = new PlatformBounding(new Vector2(200,100), new Vector2(0,50), new Vector2(330, 110));
        
        //lower left
        PlatformCollider collider3 = new PlatformCollider(new Vector2(-445,110), 125,20);
        PlatformBounding bounding3 = new PlatformBounding(new Vector2(200,100), new Vector2(0,50), new Vector2(-490, 110));
        
        //lower middle
        PlatformCollider collider4 = new PlatformCollider(new Vector2(-6,170), 125,15);
        PlatformBounding bounding4 = new PlatformBounding(new Vector2(260,100), new Vector2(0,50), new Vector2(-110,170));
        
        //upper middle
        PlatformCollider collider5 = new PlatformCollider(new Vector2(-6,-265), 60,15);
        PlatformBounding bounding5 = new PlatformBounding(new Vector2(200,100), new Vector2(60,50), new Vector2(-50,-265));
        
        //upper right
        PlatformCollider collider6 = new PlatformCollider(new Vector2(370,-208), 130,15);
        PlatformBounding bounding6 = new PlatformBounding(new Vector2(260,100), new Vector2(0,50), new Vector2(265,-208));
        
        //upper left
        PlatformCollider collider7 = new PlatformCollider(new Vector2(-380,-208), 130,15);
        PlatformBounding bounding7 = new PlatformBounding(new Vector2(260,100), new Vector2(0,50), new Vector2(-485,-208));
        
        //lowest right
        PlatformCollider collider8 = new PlatformCollider(new Vector2(320,350), 200,15);
        PlatformBounding bounding8 = new PlatformBounding(new Vector2(260,100), new Vector2(-120,50), new Vector2(145,350));
        
        //lower left
        PlatformCollider collider9 = new PlatformCollider(new Vector2(-330,350), 200,15);
        PlatformBounding bounding = new PlatformBounding(new Vector2(260,100), new Vector2(-120,50), new Vector2(-485,350));
        
        //small platform right
        PlatformCollider collider10 = new PlatformCollider(new Vector2(212,227), 30,15);
        PlatformBounding bounding10 = new PlatformBounding(new Vector2(100,100), new Vector2(20,50), new Vector2(195,227));
        
        //small platform left
        PlatformCollider collider11 = new PlatformCollider(new Vector2(-222,227), 30,15);
        PlatformBounding bounding11 = new PlatformBounding(new Vector2(100,100), new Vector2(20,50), new Vector2(-235,227));
        
    }

    public void createMap() {
        Exporter.load("src\\map.sk");
    }
}