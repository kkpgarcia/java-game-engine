package Game;

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

public class Alien extends GameObject {
    public Input input;
    public NetworkActor networkActor;
    
    private int i = 0;
    private int j = 0;
    
    private float movementSpeed = 5;
    private String currentState = "IDLE_STATE";
    private boolean isGrounded;
    private boolean isJumping;
    private boolean isFalling;

    private Dictionary<String, Sprite> spriteMap = new Dictionary<String, Sprite>();
    private InputAction rightArrow = new InputAction("right");
    private InputAction leftArrow = new InputAction("left");
    private InputAction upArrow = new InputAction("up");
    private InputAction downArrow = new InputAction("down");
    private InputAction spacebar = new InputAction("space", InputAction.ON_PRESS);

    public Alien() {
        super();
        initialize();
    }

    private void initialize() {
        this.tag = "player";
        this.transform.scale.set(3,3);
        this.animator = createAnimator();
        this.rigidbody = new Rigidbody(new Circle(40), 0, 0);
        this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(100,100));
        isJumping = false;
        this.animator.play(currentState);
    }

    public void bindInput() {
        input.mapToKey(rightArrow, KeyEvent.VK_RIGHT);
        input.mapToKey(leftArrow, KeyEvent.VK_LEFT);
        input.mapToKey(upArrow, KeyEvent.VK_UP);
        input.mapToKey(downArrow, KeyEvent.VK_DOWN);
        input.mapToKey(spacebar, KeyEvent.VK_SPACE);
    }

    public void update() {
        super.update();

        if (rightArrow.isPressed()) {
            if (this.renderer.flipped) {
                this.renderer.flipped = !this.renderer.flipped;
            }
            this.currentState = "WALKING_STATE";
            this.rigidbody.position.x += movementSpeed;
        }
        if (leftArrow.isPressed()) {
            if (!this.renderer.flipped) {
                this.renderer.flipped = !this.renderer.flipped;
            }
            this.currentState = "WALKING_STATE";
            this.rigidbody.position.x -= movementSpeed;
        }
       
        if(isGrounded){
               if(spacebar.isPressed()) {
                this.currentState = "JUMP_STATE";
                this.rigidbody.position.y -= 1;
                this.rigidbody.velocity.y = -1200;
               isGrounded = false;
                isJumping = true;
            }   
        }
        
        if(isJumping){
            this.rigidbody.velocity.y += 80;
            if(this.rigidbody.velocity.y >= 0){
                isFalling = true;
                isJumping = false;
            }
        }
        
        if(isFalling) {
            this.rigidbody.velocity.y = 400;
            isFalling = false;
        }
            
        if(!rightArrow.isPressed() && !leftArrow.isPressed()
        && !upArrow.isPressed() && !downArrow.isPressed()
        && !spacebar.isPressed() && !currentState.equals("ACTION_STATE")) {
            this.currentState = "IDLE_STATE";
        }

        if(!this.animator.currentState.equals(currentState)) {
            this.animator.play(currentState);
        }
        
        this.boundingbox.translate(this.transform.position);
        if(networkActor != null)
            networkActor.updateActor();
    }
    
    public void onCollisionEnter(GameObject other) {
        if(other.tag == null){
            return;
        }
        if(other.tag.equals("platform") || (other.tag.equals("switch"))) {
            isGrounded = true;
        }

        if(other.tag.equals("exit")) {
            System.exit(0);
        }
    }

    private Animator createAnimator() {
        mapSprites();
        Animator animator = new Animator();
        animator.addAnimation("IDLE_STATE", createIdleAnimation());
        animator.addAnimation("WALKING_STATE", createWalkingAnimation());
        animator.addAnimation("JUMP_STATE", createJumpingAnimation());
        animator.addAnimation("ACTION_STATE", createActionAnimation());
        return animator;
    }

    private void mapSprites() {
        try {
            spriteMap.add("STANDING", loadSprite("0-19"));
            spriteMap.add("WALKING_1", loadSprite("0-28"));
            spriteMap.add("WALKING_2", loadSprite("0-20"));
            spriteMap.add("WALKING_3", loadSprite("0-29"));
            spriteMap.add("JUMP_1", loadSprite("0-26"));
            spriteMap.add("JUMP_2", loadSprite("0-27"));
            spriteMap.add("ACTION", loadSprite("0-20"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Animation createIdleAnimation() {
        Frame[] idleFrames = new Frame[] {
            new Frame(spriteMap.getValue("STANDING"), 1, null)
        };

        Animation idleAnimation = new Animation(idleFrames);
        idleAnimation.onLoop = true;
        idleAnimation.renderer = this.renderer;
        return idleAnimation;
    }

    private Animation createWalkingAnimation() {
        Frame[] walkingFrames = new Frame[] {
            new Frame(spriteMap.getValue("WALKING_1"), 0.25f, null),
            new Frame(spriteMap.getValue("WALKING_2"), 0.1f, null),
            new Frame(spriteMap.getValue("WALKING_3"), 0.25f, null),
            new Frame(spriteMap.getValue("WALKING_2"), 0.1f, null)
        };

        Animation walkAnimation = new Animation(walkingFrames);
        walkAnimation.onLoop = true;
        walkAnimation.renderer = this.renderer;
        return walkAnimation;
    }

    private Animation createActionAnimation() {
        Frame[] actionFrames = new Frame[] {
            new Frame(spriteMap.getValue("ACTION"), 1, 
                new AnimationCallback() {
                    public void onExecute() {
                        onAction();
                    }
                }
            )
        };

        Animation actionAnimation = new Animation(actionFrames);
        actionAnimation.onLoop = true;
        actionAnimation.renderer = this.renderer;
        return actionAnimation;
    }

    private Animation createJumpingAnimation() {
        Frame[] jumpFrames = new Frame[] {
            new Frame(spriteMap.getValue("JUMP_1"), 1, null),
            new Frame(spriteMap.getValue("JUMP_2"), 1, null)
        };

        Animation jumpAnimation = new Animation(jumpFrames);
        jumpAnimation.onLoop = true;
        jumpAnimation.renderer = this.renderer;
        return jumpAnimation;
    }

    private Sprite loadSprite(String name) {
        BufferedImage image = Resources.loadImage("Assets/" + name + ".png");
        return new Sprite(image);
    }

    private void onAction() {
        System.out.println("Doing Action");
        this.networkActor.action = "DO_SOMETHING";
        this.currentState = "IDLE_STATE";
    }
}