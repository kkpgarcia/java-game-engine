package Test;

import SKEngine.Core.Vector2;
import SKEngine.Core.GameObject;
import SKEngine.Core.Input;
import SKEngine.Core.InputAction;
import SKEngine.Core.Sprite;
import SKEngine.Animation.Frame;
import SKEngine.Animation.Animation;
import SKEngine.Animation.Animator;
import SKEngine.Animation.AnimationCallback;
import SKEngine.Collision.BoundingBox2D;
import SKEngine.Collision.Circle;
import SKEngine.Physics.Rigidbody;
import SKEngine.Network.NetworkActor;

import SKEngine.Collections.Dictionary;
import SKEngine.Collision.Polygon;
import SKEngine.Utility.Resources;

import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;

public class Switch extends GameObject {

    private boolean activate;

    public Switch() {
        super();
        initialize();
    }

    private void initialize() {
        this.tag = "switch";
        this.transform.scale.set(2, 2);
        this.rigidbody = new Rigidbody(new Circle(20), 0, 0);
        this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(100, 100));
        BufferedImage image = Resources.loadImage("src\\Assets\\crate-2.png");
        this.renderer.sprite = new Sprite(image);
        this.rigidbody.position.y = 150;
        Moving move = new Moving();
    }

    public void update() {
        super.update();

        this.rigidbody.setStatic();
        this.boundingbox.translate(this.transform.position);
    }

    public void onCollisionEnter(GameObject other) {
        if (other.tag.equals("player") || other.tag.equals("dummy")) {
            System.out.println("switch activated");
            activate = true;
        }
    }

    public void onCollisionExit(GameObject other) {
        if (other.tag.equals("player") || other.tag.equals("dummy")) {
            System.out.println("switch deactivate");
            activate = false;
        }
    }

    public class Moving extends GameObject {

        public boolean goingRight;

        public Moving() {
            super();
            initialize();
        }

        private void initialize() {
            this.tag = "platform";
            this.transform.scale.set(10, 2);
            this.rigidbody = new Rigidbody(new Polygon(100, 20), 0, 0);
            this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(150, 50));
            BufferedImage image = Resources.loadImage("src\\Assets\\grass-platform.png");
            this.renderer.sprite = new Sprite(image);
            this.rigidbody.position.x = 150;
            this.rigidbody.position.y = 100;
            goingRight = true;
        }

        public void update() {
            super.update();

            if (activate) {

                if (goingRight) {
                    this.rigidbody.position.x += 5;
                    this.rigidbody.position.y -= 5;

                    if (this.rigidbody.position.x >= 500) {
                        goingRight = false;
                    }
                }
                
                if (goingRight == false) {
                    this.rigidbody.position.x -= 5;
                    this.rigidbody.position.y += 5;
                    
                    if (this.rigidbody.position.x <= 150) {
                        goingRight = true;
                    }
                }
                
            }

            this.rigidbody.setStatic();
            this.boundingbox.translate(this.transform.position);
        }
    }
}
