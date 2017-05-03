import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class EngineTest {

    public static void main(String[] args) {
        EngineTest test = new EngineTest();
        test.runTest();
    }

    public void runTest() {
        JFrame window = new JFrame("Physics Test");
        Screen screen = new Screen(500, 500);
        Input input = new Input(screen);

        GameEngine game = new GameEngine(screen);

        Alien alien = new Alien();
        alien.input = input;
        alien.bindInput();
        PinkAlien pink = new PinkAlien();

        game.addObject(pink);
        game.addObject(alien);

        window.add(screen);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

        game.start();
    }

    class Alien extends GameObject {
        public Input input;
        private float movementSpeed = 5;

        private InputAction rightArrow = new InputAction("right");
        private InputAction leftArrow  = new InputAction("left");
        private InputAction upArrow = new InputAction("up");
        private InputAction downArrow = new InputAction("down");

        public Alien() {
            super();
            initialize();
        }

        public void initialize() {
            this.transform.scale.set(4,4);
            BufferedImage image = Resources.loadImage("Assets/green-alien-2.png");
            this.renderer.sprite = new Sprite(image);
            this.rigidbody = new Rigidbody(new Circle(50), 0,0);
        }

        public void bindInput() {
            input.mapToKey(rightArrow, KeyEvent.VK_RIGHT);
            input.mapToKey(leftArrow, KeyEvent.VK_LEFT);
            input.mapToKey(upArrow, KeyEvent.VK_UP);
            input.mapToKey(downArrow, KeyEvent.VK_DOWN);
        }

        public void update() {
            if(rightArrow.isPressed()) {
                if(this.renderer.flipped)
                    this.renderer.flipped = !this.renderer.flipped;
                this.transform.position.x += movementSpeed;
            }
            if(leftArrow.isPressed()) {
                if(!this.renderer.flipped)
                    this.renderer.flipped = !this.renderer.flipped;

                this.transform.position.x -= movementSpeed;
            }
            if(upArrow.isPressed()) this.transform.position.y -= movementSpeed;
            if(downArrow.isPressed()) this.transform.position.y += movementSpeed;
        }
    }

    class PinkAlien extends GameObject {
        public PinkAlien() {
            super();
            initialize();
        }

        public void initialize() {
            this.transform.scale.set(4,4);
            BufferedImage image = Resources.loadImage("Assets/pink-alien.png");
            this.renderer.sprite = new Sprite(image);
            this.rigidbody = new Rigidbody(new Circle(50), 0,0);
        }
    }
}