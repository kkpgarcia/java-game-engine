package Game;

import SKEngine.Collision.BoundingBox2D;
import SKEngine.Core.GameObject;
import SKEngine.Collision.Polygon;
import SKEngine.Core.Vector2;
import SKEngine.Physics.Rigidbody;

public class PlatformBounding extends GameObject {
    public PlatformBounding(Vector2 min, Vector2 max, Vector2 position) {
        super();
        this.tag = "platform";
        this.boundingbox = new BoundingBox2D(min, max);
        this.boundingbox.translate(position);
    }
}