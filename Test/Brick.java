package Test;

import SKEngine.Core.GameObject;
import SKEngine.Core.Vector2;
import SKEngine.Core.Sprite;

public class Brick extends GameObject {
    public Brick(Vector2 position, Sprite sprite) {
        super();
        initialize(position, sprite);
    }

    public void initialize(Vector2 position, Sprite sprite) {
        this.transform.scale.set(3,3);
        this.transform.position = position;
        this.renderer.sprite = sprite;
        this.rigidbody = null;
        this.boundingbox = null;
        super.registerObject();
    }
}