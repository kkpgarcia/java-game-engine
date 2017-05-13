package SKEngine.Test;

import SKEngine.Core.GameObject;
import SKEngine.Core.Vector2;
import SKEngine.Core.Sprite;
import SKEngine.Collision.Polygon;
import SKEngine.Physics.Rigidbody;
import SKEngine.Utility.Resources;

import java.awt.image.BufferedImage;

public class Platform extends GameObject {
    public Brick[] platforms;

    public Platform(Vector2 position, int amount) {
        super();
        initialize(position, amount);
    }

    public void initialize(Vector2 position, int amount) {
        BufferedImage image = Resources.loadImage("Assets/brick.png");
        Sprite sprite = new Sprite(image);
        platforms = new Brick[amount];

        for(int i = -(platforms.length/2); i < platforms.length/2; i++) {
            Vector2 brickPos = new Vector2(60 * i, 200);
            Brick brick = new Brick(brickPos, sprite);
            brick.transform.setParent(this);
            platforms[i + (platforms.length/2)] = brick;
        }

        this.rigidbody = new Rigidbody (new Polygon(500, 35), 0, 200);
        this.rigidbody.setStatic();
        this.transform.position = position;
    }

    public void update() {
        super.update();
    }
}