package Game;

import SKEngine.Core.GameObject;
import SKEngine.Core.Vector2;
import SKEngine.Collision.BoundingBox2D;

public class Exit extends GameObject {
    public Exit() {
        super();
        this.tag = "exit";
        this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(50,50));
        this.transform.position = new Vector2(0, -350);
        this.boundingbox.translate(this.transform.position);
    }
}