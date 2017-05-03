import javax.swing.JFrame;

import java.awt.Color;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class GameEngine extends Engine {
	public static GameEngine instance;
	private RenderEngine renderEngine;
	private PhysicsEngine physicsEngine;

	public GameEngine(Screen screen) {
		super();
		instance = this;
		renderEngine = new RenderEngine(screen);
		physicsEngine = new PhysicsEngine(MathEx.DT, 10);
	}

	public void addObject(GameObject obj) {
		super.addObject(obj);
		
		renderEngine.addObject(obj);
		
		if(obj.renderer != null);
			physicsEngine.addObject(obj);
	}

	public void start() {
		super.start();
		physicsEngine.start();
		renderEngine.start();
	}

	public void updateMainEngine() {
		for(GameObject g : objects) {
			g.update();
		}
	}
}