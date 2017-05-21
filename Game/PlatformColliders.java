package Game;

import SKEngine.Core.GameObject;
import SKEngine.Collision.Polygon;
import SKEngine.Core.Vector2;
import SKEngine.Physics.Rigidbody;

public class PlatformColliders extends GameObject {
    public PlatformColliders(Vector2 position, int width, int height) {
        super();
        this.rigidbody = new Rigidbody(new Polygon(width, height), (int)position.x, (int)position.y);
        this.rigidbody.setStatic();
    }
}