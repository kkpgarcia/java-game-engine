package SKEngine.Core;

import SKEngine.Collision.CollisionEngine;
import SKEngine.Physics.PhysicsEngine;
import SKEngine.Calculations.MathEx;

public class GameEngine extends Engine {
	public static GameEngine instance;
	private RenderEngine renderEngine;
	private PhysicsEngine physicsEngine;
	private CollisionEngine collisionEngine;

	public GameEngine(Screen screen) {
		super();
		instance = this;
		renderEngine = new RenderEngine(screen);
		physicsEngine = new PhysicsEngine(MathEx.DT, 1);
		collisionEngine = new CollisionEngine();
	}

	public void addObject(GameObject obj) {
		super.addObject(obj);
		
		renderEngine.addObject(obj);

		if(obj.boundingbox != null)
			collisionEngine.addObject(obj);
		
		if(obj.rigidbody != null);
			physicsEngine.addObject(obj);
	}

	public void removeObject(GameObject obj) {
		super.removeObject(obj);

		renderEngine.removeObject(obj);

		if(obj.boundingbox != null)
			collisionEngine.removeObject(obj);

		if(obj.rigidbody != null)
			physicsEngine.removeObject(obj);
	}

	public void start() {
		super.start();
		collisionEngine.start();
		physicsEngine.start();
		renderEngine.start();
	}

	public void updateMainEngine() {
		for(GameObject g : objects) {
			g.update();
		}
	}
}