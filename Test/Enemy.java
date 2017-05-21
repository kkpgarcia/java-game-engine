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
import SKEngine.Utility.Resources;

import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;

public class Enemy extends GameObject{
    
    private float point1;
    private float point2;
    private float movespeed;
    
    private boolean isFacingLeft;
    public NetworkActor networkActor;
    
    public Enemy(){
        super();
        initialize();
    }
    
    private void initialize() {
        this.tag = "enemy";
        isFacingLeft = true;
        this.transform.scale.set(2,2);
        this.rigidbody = new Rigidbody(new Circle(20), 0, 0);
        this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(20,20));
        BufferedImage image = Resources.loadImage("src\\Assets\\bee-1.png");
        this.renderer.sprite = new Sprite(image);
    }
    
    public void update(){
        super.update();
        
        if(isFacingLeft) {
            this.rigidbody.position.x -= 5;
            if(this.rigidbody.position.x <= -500) {
                isFacingLeft = false;
                this.renderer.flipped = !this.renderer.flipped;
           }
        }
        
        if(this.isFacingLeft == false) {
            this.rigidbody.position.x += 5;
            if(this.rigidbody.position.x >= 500) {
                isFacingLeft = true;
                this.renderer.flipped = !this.renderer.flipped;
            }
        }
        
        this.rigidbody.setStatic();
        this.boundingbox.translate(this.transform.position);
    }
}
