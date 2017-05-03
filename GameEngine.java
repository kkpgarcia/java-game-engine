import javax.swing.JFrame;

import java.awt.Color;

import java.util.ArrayList;

public class GameEngine extends Engine {

	public static GameEngine instance;

	private ArrayList<GameObject> objects;
	private RenderEngine renderEngine;
	private PhysicsEngine physicsEngine;

	public GameEngine(Screen screen) {
		instance = this;
		objects = new ArrayList<>();
		renderEngine = new RenderEngine(screen);
		physicsEngine = new PhysicsEngine(MathEx.DT, 10);
	}

	public void addObject(GameObject obj) {
		if(objects == null) {
			objects = new ArrayList<>();
		}

		objects.add(obj);
		renderEngine.addRenderers(obj);

		if(obj.rigidbody != null)
			physicsEngine.addRigidbody(obj);
	}

	public void init() {
		super.init();
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



		window.add(screen);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		game.init();
	}
}