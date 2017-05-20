package Test;

import SKEngine.Core.GameObject;
import SKEngine.Core.Sprite;
import SKEngine.Collision.Circle;
import SKEngine.Physics.Rigidbody;
import SKEngine.Network.NetworkActor;
import SKEngine.Utility.Resources;
import SKEngine.Core.Vector2;
import SKEngine.Collision.BoundingBox2D;

import java.awt.image.BufferedImage;

public class Dummy extends GameObject {
    public NetworkActor networkActor;

    public Dummy() {
        super();
        this.transform.scale.set(4,4);
        BufferedImage image = Resources.loadImage("Assets/pink-alien.png");
        this.renderer.sprite = new Sprite(image);
        //this.rigidbody = new Rigidbody(new Circle(40),0,0);
        this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(100,100));
    }

    public void onNetworkEvent(String action) {
        switch(action) {
            case "DO_SOMETHING":
                action();
                break;
        }
    }

    public void action() {
        System.out.println("DOING SOMETHING");
    }

    public void onCollisionStay(GameObject other) {
        
    }
}