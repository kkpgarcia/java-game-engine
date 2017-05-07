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

        
        /*for(int i = 0; i < platform.platforms.length; ++i) {
            game.addObject(platform.platforms[i]);
        }*/
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
            this.rigidbody = new Rigidbody(new Circle(40), 0,0);
            this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(100,100));
        }

        public void bindInput() {
            input.mapToKey(rightArrow, KeyEvent.VK_RIGHT);
            input.mapToKey(leftArrow, KeyEvent.VK_LEFT);
            input.mapToKey(upArrow, KeyEvent.VK_UP);
            input.mapToKey(downArrow, KeyEvent.VK_DOWN);
        }

        @Override
        public void update() {
            super.update();
            /*System.out.println("Position: " + this.transform.position);
            System.out.println("Velocity: " + this.rigidbody.velocity);
            System.out.println("Force: " + this.rigidbody.force);
            System.out.println("A Velocity: " + this.rigidbody.angularVelocity);
            System.out.println("Torque: " + this.rigidbody.torque);
            System.out.println("Orient: " + this.rigidbody.orient);
            System.out.println("Mass: " + this.rigidbody.mass);
            System.out.println("InvMass: " + this.rigidbody.invMass);
            System.out.println("Inertia: " + this.rigidbody.inertia);
            System.out.println("invInertia: " + this.rigidbody.invInertia);
            System.out.println("S Fric: " + this.rigidbody.staticFriction);
            System.out.println("D Fric: " + this.rigidbody.dynamicFriction);
            System.out.println("Restitution: " + this.rigidbody.restitution);*/
           
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

        public void onCollisionStay(GameObject other) {
            if(other != null) {
                //System.out.println("Collision Success!");
                return;
            }

            System.out.println("Not Colliding");
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
            this.rigidbody = new Rigidbody(new Circle(40), 0,0);
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
            this.rigidbody = new Rigidbody(new Circle(69), (int)position.x, (int)position.y);
            this.rigidbody.setStatic();
        }
    }
}