import javax.swing.JFrame;

import java.awt.Color;

import java.util.ArrayList;

public class GameEngine extends Engine {
	private ArrayList<GameObject> objects;
    private CollisionEngine collisionEngine;
	private RenderEngine renderEngine;
	private PhysicsEngine physicsEngine;
	private Time time;

	public GameEngine(Screen screen) {
		objects = new ArrayList<>();
        collisionEngine = new CollisionEngine();
		renderEngine = new RenderEngine(screen);
		physicsEngine = new PhysicsEngine();
		time = new Time();
		time.init();
	}

	public void addObject(GameObject obj) {
		if(objects == null) {
			objects = new ArrayList<>();
		}

		objects.add(obj);
        collisionEngine.addObjects(obj);
		renderEngine.addRenderers(obj);
		physicsEngine.addObjects(obj);
	}

	public void init() {
		super.init();
		collisionEngine.init();
		renderEngine.init();
		physicsEngine.init();
	}

	public void updateMainEngine() {
		for(GameObject g : objects) {
			g.update();
		}
	}

	public static void main(String[] args) {
		JFrame window = new JFrame("Game");
		Screen screen = new Screen(300, 800);
		GameEngine game = new GameEngine(screen);
        Input input = new Input(screen);

        Player player = new Player();
        player.transform.position = new Vector2(0,0);
        player.color = Color.BLACK;
        player.boundingBox = new BoundingBox2D(new Vector2(0,0),new Vector2(100,100));
        player.input = input;
        player.velocity = new Vector2(5,5);
        player.bindInput();
		player.rigidbody = new Rigidbody(player);
		player.renderer.sprite = new Sprite();

        game.addObject(player);
        game.addObject((GameObject)createDummy(new Vector2(-Screen.width/2, 0), new Vector2(1, 0), Color.RED, new BoundingBox2D(new Vector2(0,0),new Vector2(100,100))));
        /*game.addObject((GameObject)createDummyPlayer(new Vector2(0, 0), new Vector2(0, 1), Color.BLUE, 20, 20));
        game.addObject((GameObject)createDummyPlayer(new Vector2(0, 0), new Vector2(0, -1), Color.RED, 20, 20));
        game.addObject((GameObject)createDummyPlayer(new Vector2(0, 0), new Vector2(1, 1), Color.BLUE, 20, 20));
        game.addObject((GameObject)createDummyPlayer(new Vector2(0, 0), new Vector2(1, -1), Color.RED, 20, 20));
        game.addObject((GameObject)createDummyPlayer(new Vector2(0, 0), new Vector2(-1, 1), Color.BLUE, 20, 20));
        game.addObject((GameObject)createDummyPlayer(new Vector2(0, 0), new Vector2(-1, -1), Color.RED, 20, 20));*/

		/*game.addObject(createDebris(Color.RED));
		game.addObject(createDebris(Color.BLUE));
		game.addObject(createDebris(Color.YELLOW));
		game.addObject(createDebris(Color.GREEN));
		game.addObject(createDebris(Color.ORANGE));*/

		window.add(screen);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		game.init();
	}

    private static Enemy createDummy(Vector2 position, Vector2 velocity, Color color, BoundingBox2D boundingBox) {
        Enemy enemy = new Enemy();
        enemy.transform.position = position;
        enemy.velocity = velocity;
        enemy.color = color;
        enemy.boundingBox = boundingBox;
		enemy.renderer.sprite = new Sprite();
        
        return enemy;
    }

	private static Debris createDebris(Color color) {
		Debris debris = new Debris();
		debris.transform.position.x = MathEx.random(-Screen.width/2, Screen.width/2);
		debris.transform.position.y = MathEx.random(-Screen.height/2, Screen.height/2);
		debris.color = color;
		debris.boundingBox = new BoundingBox2D(new Vector2(0,0), new Vector2(20,20));
		debris.renderer.sprite = new Sprite();
		debris.velocity = new Vector2(1,1);
		debris.rigidbody = new Rigidbody(debris);

		return debris;
	}
}