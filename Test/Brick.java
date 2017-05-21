package Test;

import SKEngine.Collision.BoundingBox2D;
import SKEngine.Core.GameObject;
import SKEngine.Core.Vector2;
import SKEngine.Core.Sprite;

public class Brick extends GameObject {
    public Brick(Vector2 position, Sprite sprite) {
        super();
        initialize(position, sprite);
    }

    public void initialize(Vector2 position, Sprite sprite) {
        this.tag = "brick";
        this.transform.scale.set(3,3);
        this.transform.position = position;
        this.renderer.sprite = sprite;
        this.rigidbody = null;
        //this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(50,50));
    }
    
    public void update() {
        super.update();
        //this.boundingbox.translate(this.transform.position);
    }
}