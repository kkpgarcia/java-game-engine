import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class NetAlien extends GameObject {
    public Input input;
    private float movementSpeed = 5;

    public NetworkActor networkActor;

    private InputAction rightArrow = new InputAction("right");
    private InputAction leftArrow = new InputAction("left");
    private InputAction upArrow = new InputAction("up");
    private InputAction downArrow = new InputAction("down");
    private InputAction spaceBar = new InputAction("space", InputAction.ON_PRESS);

    public NetAlien() {
        super();
        initialize();
    }

    public void initialize() {
        this.tag = "player";
        this.transform.scale.set(4,4);
        BufferedImage image = Resources.loadImage("Assets/green-alien-2.png");
        this.renderer.sprite = new Sprite(image);
        this.rigidbody = new Rigidbody(new Circle(40), 0, 0);
        //this.networkActor = new NetworkActor(this);
    }

    public void bindInput() {
        input.mapToKey(rightArrow, KeyEvent.VK_RIGHT);
        input.mapToKey(leftArrow, KeyEvent.VK_LEFT);
        input.mapToKey(upArrow, KeyEvent.VK_UP);
        input.mapToKey(downArrow, KeyEvent.VK_DOWN);
        input.mapToKey(spaceBar, KeyEvent.VK_SPACE);
    }

    @Override
    public void update() {
        super.update();

        if (rightArrow.isPressed()) {
            if (this.renderer.flipped) {
                this.renderer.flipped = !this.renderer.flipped;
            }
            //this.rigidbody.clearVelocity();
            this.rigidbody.position.x += movementSpeed;
        }
        if (leftArrow.isPressed()) {
            if (!this.renderer.flipped) {
                this.renderer.flipped = !this.renderer.flipped;
            }
            //this.rigidbody.clearVelocity();
            this.rigidbody.position.x -= movementSpeed;
        }
        if (upArrow.isPressed()) {
            //this.rigidbody.clearVelocity();
            this.rigidbody.position.y -= movementSpeed;
        }
        if (downArrow.isPressed()) {
            //this.rigidbody.clearVelocity();
            this.rigidbody.position.y += movementSpeed;
        }

        if (spaceBar.isPressed()) {
            //this.rigidbody.velocity.addscalei(new Vector2(0,-100), 3);
            /*int jumpSpeed = 25;
            int jumpHeight = 75;
            int jumpLength = 15;
            this.rigidbody.velocity.x = -(jumpSpeed - jumpLength/2)*(jumpSpeed - jumpLength/2) * 4 * jumpHeight/(jumpLength * jumpLength) + jumpHeight;*/
            networkActor.action = "DO_SOMETHING";
        }

        networkActor.updateActor();
    }
}