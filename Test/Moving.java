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

public class Moving extends GameObject{
    
    public boolean activate;
    
    public Moving(){
        super();
        initialize();
    }
    
    private void initialize() {
        this.tag = "switch";
        this.transform.scale.set(2,10);
        this.rigidbody = new Rigidbody(new Circle(20), 0, 0);
        this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(100,100));
        BufferedImage image = Resources.loadImage("src\\Assets\\grass-platform.png");
        this.renderer.sprite = new Sprite(image);
        this.rigidbody.position.x = 150;
    }
    
    public void update(){
        super.update();
        
        if(activate)
        {
            
        }
        
        this.rigidbody.setStatic();
        this.boundingbox.translate(this.transform.position);
    }

}