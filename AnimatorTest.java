import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class AnimatorTest {
    public static void main(String[] args) {
        AnimatorTest test = new AnimatorTest();
        test.runTest();
    }

    public void runTest() {
        Screen screen = new Screen("Animator Test",500,500);
        Input input = new Input(screen);
        GameEngine game = new GameEngine(screen);
        Camera camera = new Camera();

        Bee bee = new Bee();
        bee.input = input;
        bee.bindInput();

        Bee bee2 = new Bee();
        bee2.rigidbody.position = new Vector2(150,150);
        bee2.transform.setParent(bee);

        camera.follow(bee);
        
        game.addObject(bee);
        game.addObject(bee2);
        game.addObject(camera);

        game.start();
    }

    class Bee extends GameObject {
        public Input input;
        private float movementSpeed = 5;

        private InputAction rightArrow = new InputAction("right");
        private InputAction leftArrow = new InputAction("left");
        private InputAction upArrow = new InputAction("up");
        private InputAction downArrow = new InputAction("down");
        private InputAction spacebar = new InputAction("space", InputAction.ON_PRESS);

        public Bee() {
            super();
            initialize();
        }

        public void initialize() {
            this.tag = "player";
            this.transform.scale.set(4, 4);
            this.rigidbody = new Rigidbody(new Circle(10), 0, 0);
            this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(10, 10));
            this.animator = new Animator();
            this.animator.addAnimation("IDLE_STATE", idleAnimation());
            this.animator.addAnimation("MORPH_STATE", morphAnimation());
            this.animator.play("IDLE_STATE");
        }

        public Animation idleAnimation() {
            Animation animation = new Animation();
            animation.renderer = this.renderer;
            animation.frames = 2;
            animation.sprites = new Sprite[] {
                new Sprite(Resources.loadImage("Assets/bee-1.png")),
                new Sprite(Resources.loadImage("Assets/bee-2.png"))
            };
            animation.interval = new float[]{ 1, 0.75f };
            animation.callbacks = new AnimationCallback[] {
                null,
                new AnimationCallback() {
                    public void onExecute() {
                        System.out.println("Doing callback from animation!");
                    }
                }
            };
            animation.onLoop = true;
            return animation;
        }

        public Animation morphAnimation() {
            Animation animation = new Animation();
            animation.renderer = this.renderer;
            animation.frames = 9;
            animation.sprites = new Sprite[] {
                new Sprite(Resources.loadImage("Assets/blue-alien.png")),
                new Sprite(Resources.loadImage("Assets/blue-alien-2.png")),
                new Sprite(Resources.loadImage("Assets/blue-alien-3.png")),
                new Sprite(Resources.loadImage("Assets/blue-alien-4.png")),
                new Sprite(Resources.loadImage("Assets/blue-alien-5.png")),
                new Sprite(Resources.loadImage("Assets/blue-alien-6.png")),
                new Sprite(Resources.loadImage("Assets/blue-alien-7.png")),
                new Sprite(Resources.loadImage("Assets/blue-alien-8.png"))
            };
            animation.interval = new float[] { 1, 0.5f, 0.7f, 1, 1, 0.5f, 1, 1, 2 };
            animation.onLoop = true;
            return animation;
        }

        public void bindInput() {
            input.mapToKey(rightArrow, KeyEvent.VK_RIGHT);
            input.mapToKey(leftArrow, KeyEvent.VK_LEFT);
            input.mapToKey(upArrow, KeyEvent.VK_UP);
            input.mapToKey(downArrow, KeyEvent.VK_DOWN);
            input.mapToKey(spacebar, KeyEvent.VK_SPACE);
        }

        @Override
        public void update() {
            super.update();

            if (rightArrow.isPressed()) {
                if (this.renderer.flipped) {
                    this.renderer.flipped = !this.renderer.flipped;
                }
                this.rigidbody.clearVelocity();
                this.rigidbody.position.x += movementSpeed;
            }
            if (leftArrow.isPressed()) {
                if (!this.renderer.flipped) {
                    this.renderer.flipped = !this.renderer.flipped;
                }
                this.rigidbody.clearVelocity();
                this.rigidbody.position.x -= movementSpeed;
            }
            if (upArrow.isPressed()) {
                this.rigidbody.position.y -= movementSpeed;
            }
            if (downArrow.isPressed()) {
                this.rigidbody.position.y += movementSpeed;
            }
            if(spacebar.isPressed()) {
                if(animator.currentState == "IDLE_STATE")
                    animator.play("MORPH_STATE");
                else
                    animator.play("IDLE_STATE");
            }
            this.boundingbox.translate(this.transform.position);
        }
    }
}