package SKEngine.Test;

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



public class Player extends GameObject{
    public Input input;
    public NetworkActor actor;
    
    private Dictionary<String, Sprite> spriteMap = new Dictionary<String, Sprite>();
    private InputAction rightArrow = new InputAction("right");
    private InputAction leftArrow = new InputAction("left");
    private InputAction spacebar = new InputAction("spacebar", InputAction.ON_PRESS);
    
    public Player(){
        super();
        initialize();
    }
    
    public void initialize(){
        this.tag = "player";
        this.transform.scale.set(4,4);
        this.rigidbody = new Rigidbody(new Circle(40), 0, 0);
        this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(20,20));
    }
    
    public void bindInput(){
        input.mapToKey(rightArrow, KeyEvent.VK_RIGHT);
        input.mapToKey(leftArrow, KeyEvent.VK_LEFT);
        input.mapToKey(spacebar, KeyEvent.VK_SPACE);
    }
    
    public void update(){
        super.update();
            
        if(rightArrow.isPressed()) {
            
        }
        
        if(leftArrow.isPressed()) {
            
        }
        
        if(spacebar.isPressed()) {
            
        }
    }
    
    private Sprite loadSprite(String name) {
        BufferedImage image = Resources.loadImage("src\\Assets\\" + name + ".png");
        return new Sprite(image);
    }
}
