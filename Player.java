import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public class Player extends GameObject {

    public Input input;

    private InputAction rightArrow = new InputAction("right");
    private InputAction leftArrow  = new InputAction("left");
    private InputAction upArrow = new InputAction("up");
    private InputAction downArrow = new InputAction("down");
    private InputAction spaceBar = new InputAction("space", InputAction.ON_PRESS);

    public Player() {
        super();
        initialize();
    }

    public void initialize() {
        BufferedImage image = Resources.loadImage("Assets/test.png");
        Sprite newSprite = new Sprite(image);
        this.renderer.sprite = newSprite;

        //this.rigidbody = new Rigidbody(new Circle(50), 0,0);
    }

    public void bindInput() {
        input.mapToKey(rightArrow, KeyEvent.VK_RIGHT);
        input.mapToKey(leftArrow, KeyEvent.VK_LEFT);
        input.mapToKey(upArrow, KeyEvent.VK_UP);
        input.mapToKey(downArrow, KeyEvent.VK_DOWN);
        input.mapToKey(spaceBar, KeyEvent.VK_SPACE);
    }

    public void update() {
        //this.transform.position.x += 1;
        //this.transform.position.y += 1;

        if(rightArrow.isPressed()) {
            this.transform.position.x += 1;
        }

        if(leftArrow.isPressed()) {
            this.transform.position.x -= 1;
        }

        if(upArrow.isPressed()) {
            this.transform.position.y -= 1;
        }

        if(downArrow.isPressed()) {
            this.transform.position.y += 1;
        }

        if(spaceBar.isPressed()) {
            System.out.println("Creating new Object!");
            Player player2 = new Player();
            GameEngine.instance.addObject(player2);
        }
    }
}