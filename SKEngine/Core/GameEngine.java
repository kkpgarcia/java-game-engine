package SKEngine.Core;

import SKEngine.Collision.CollisionEngine;
import SKEngine.Physics.PhysicsEngine;
import SKEngine.Calculations.MathEx;

/**
 * <h2>Game Engine</h2>
 * This is the main engine of the game. This engine composed of sub engines:
 * RenderEngine, CollisionEngine, and PhysicsEngine.
 * <p>
 * 
 * @see RenderEngine - SKEngine.Core
 * @see CollisionEngine - SKEngine.Collision
 * @see PhysicsEngine - SKEngine.Physics
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */
public class GameEngine extends Engine {
	public static GameEngine instance;
	private RenderEngine renderEngine;
	private PhysicsEngine physicsEngine;
	private CollisionEngine collisionEngine;

	/**
	 * Constructs the game engine, and the other sub-engines.
	 * @param Screen the main screen of the game
	 * */
	public GameEngine(Screen screen) {
		super();
		instance = this;
		renderEngine = new RenderEngine(screen);
		//physicsEngine = new PhysicsEngine(MathEx.DT, 1);
		collisionEngine = new CollisionEngine();
	}

	/**
	 * {@inheritDocs}
	 * */
	public void addObject(GameObject obj) {
		super.addObject(obj);
		
		renderEngine.addObject(obj);
		collisionEngine.addObject(obj);
		//physicsEngine.addObject(obj);
	}

	/**
	 * {@inheritDocs}
	 * */
	public void removeObject(GameObject obj) {
		super.removeObject(obj);

		renderEngine.removeObject(obj);
		collisionEngine.removeObject(obj);
		//physicsEngine.removeObject(obj);
	}

	/**
	 * Starts all the engine including this.
	 * */
	public void start() {
		super.start();
		collisionEngine.start();
		//physicsEngine.start();
		renderEngine.start();
	}

	/**
	 * {@inheritDocs}
	 * */
	public void updateMainEngine() {
		for(GameObject g : objects) {
			g.update();
		}
	}
}