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
        Screen screen = new Screen(1280, 720);
        Input input = new Input(screen);

        GameEngine game = new GameEngine(screen);

        Platform platform = new Platform();

        Alien alien = new Alien();
        alien.input = input;
        alien.bindInput();

        PinkAlien pink = new PinkAlien();
        pink.input = input;
        pink.bindInput();

        game.addObject(platform);
        for(int i = 0; i < platform.platforms.length; ++i) {
            game.addObject(platform.platforms[i]);
        }
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
        private InputAction spaceBar = new InputAction("space", InputAction.ON_PRESS);

        public Alien() {
            super();
            initialize();
        }

        public void initialize() {
            this.transform.scale.set(4,4);
            BufferedImage image = Resources.loadImage("Assets/green-alien-2.png");
            this.renderer.sprite = new Sprite(image);
            this.rigidbody = new Rigidbody(new Circle(40), 0,0);
            this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(100,100));
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
           
            if(rightArrow.isPressed()) {
                if(this.renderer.flipped)
                    this.renderer.flipped = !this.renderer.flipped;
                //this.rigidbody.clearVelocity();
                this.rigidbody.position.x += movementSpeed;
            }
            if(leftArrow.isPressed()) {
                if(!this.renderer.flipped)
                    this.renderer.flipped = !this.renderer.flipped;
                //this.rigidbody.clearVelocity();
                this.rigidbody.position.x -= movementSpeed;
            }
            if(upArrow.isPressed()) {
                //this.rigidbody.clearVelocity();
                this.rigidbody.position.y -= movementSpeed;
            }
            if(downArrow.isPressed()) {
                //this.rigidbody.clearVelocity();
                this.rigidbody.position.y += movementSpeed;
            }

            if(spaceBar.isPressed()) {
                this.rigidbody.velocity.addscalei(new Vector2(0,-100), 5);
                this.rigidbody.force.addscalei(new Vector2(0,-5000), 10);
                this.rigidbody.angularVelocity += this.rigidbody.torque * this.rigidbody.invInertia * 1;
               // this.rigidbody.force.y -= 100;
            }
            
            this.boundingbox.translate(this.transform.position);
        }

        public void onCollisionEnter(GameObject other) {
            //System.out.println("Collision Enter!");
        }

        public void onCollisionStay(GameObject other) {
            //System.out.println("Collision Stay!");
        }

        public void onCollisionExit(GameObject other) {
            //System.out.println("Collision Exit!");
        } 
    }

    class PinkAlien extends GameObject {
        public Input input;
        private float movementSpeed = 5;

        private InputAction rightArrow = new InputAction("right");
        private InputAction leftArrow  = new InputAction("left");
        private InputAction upArrow = new InputAction("up");
        private InputAction downArrow = new InputAction("down");
        public PinkAlien() {
            super();
            initialize();
        }

        public void initialize() {
            this.transform.position.set(0, 0);
            this.transform.scale.set(4,4);
            BufferedImage image = Resources.loadImage("Assets/pink-alien.png");
            this.renderer.sprite = new Sprite(image);
            this.rigidbody = new Rigidbody(new Polygon(40,40), 0,0);
            this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(100,100));
            //this.rigidbody.setStatic();
        }

        public void bindInput() {
            input.mapToKey(rightArrow, KeyEvent.VK_D);
            input.mapToKey(leftArrow, KeyEvent.VK_A);
            input.mapToKey(upArrow, KeyEvent.VK_W);
            input.mapToKey(downArrow, KeyEvent.VK_S);
        }

        @Override
        public void update() {
            super.update();

            if(rightArrow.isPressed()) {
                if(this.renderer.flipped)
                    this.renderer.flipped = !this.renderer.flipped;
                this.rigidbody.clearVelocity();
                this.rigidbody.position.x += movementSpeed;
            }
            if(leftArrow.isPressed()) {
                if(!this.renderer.flipped)
                    this.renderer.flipped = !this.renderer.flipped;
                this.rigidbody.clearVelocity();
                this.rigidbody.position.x -= movementSpeed;
            }
            if(upArrow.isPressed()) {
                this.rigidbody.clearVelocity();
                this.rigidbody.position.y -= movementSpeed;
            }
            if(downArrow.isPressed()) {
                this.rigidbody.clearVelocity();
                this.rigidbody.position.y += movementSpeed;
            }

            this.boundingbox.translate(this.transform.position);
        }
    }

    public class Platform extends GameObject {
        public Brick[] platforms;

        public Platform() {
            super();
            initialize();
        }

        public void initialize() {
            BufferedImage image = Resources.loadImage("Assets/brick.png");
            Sprite sprite = new Sprite(image);
            platforms = new Brick[10];

            for(int i = 0; i < platforms.length; ++i) {
                Vector2 position = new Vector2(60 * i, 200);
                Brick brick = new Brick(position, sprite);
                platforms[i] = brick;
            }

            this.rigidbody = new Rigidbody(new Polygon(400,40), 0, 200);
            this.rigidbody.setStatic();
        }

        public void update() {
            super.update();
        }
    }

    public class Brick extends GameObject {
        public Brick(Vector2 position, Sprite sprite) {
            super();
            initialize(position, sprite);
        }

        public void initialize(Vector2 position, Sprite sprite) {
            this.transform.scale.set(3,3);
            this.transform.position = position;
            this.renderer.sprite = sprite;
            this.rigidbody = null;
        }
    }
}