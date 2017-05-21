package SKEngine.Core;

import SKEngine.Animation.Animator;
import SKEngine.Collision.BoundingBox2D;
import SKEngine.Physics.Rigidbody;

import java.awt.Graphics2D;
import java.util.UUID;

/**
 * <h2>Game Object</h2>
 * Abstract object in the game. Composed of different components that handles
 * different types of executions. This class is updated by the Main Engine,
 * Render Engine, Collision Engine, Physics Engine, and the Network Client.
 * This class needs to be extended to be able to work with it.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */
public abstract class GameObject {

    public String tag;
    public Transform transform;
    public BoundingBox2D boundingbox;
    public Renderer renderer;
    public Rigidbody rigidbody;
    public Animator animator;

    /**
     * Constructs, and initializes this object. It creates transform for
     * transformation references, and renderer for drawing purposes.
     * 
     * <b>NOTE:</b> add super or initialize transform and renderer.
     * */
	public GameObject() {
            this.transform = new Transform();
            this.renderer = new Renderer();
            instantiate(this);
	}

    /**
     * Main loop for the game object. This is where we add logic to the
     * game object, inputs, etc.
     * 
     * <b>NOTE:</b> add super or update rigidbody positions, and parent 
     * transformations manually.
     * */
	public void update() {
        if(this.rigidbody != null && this.transform.getParent() != null) {
            this.transform.position = this.transform.getParent().transform.position.add(this.rigidbody.position);
        } else if(this.rigidbody != null && this.transform.getParent() == null) {
            this.transform.position = this.rigidbody.position;
        }
    }

    /**
     * Rendering part of the game object. It does not need to be called unless
     * we want to debug a certain feature that needs to be drawn.
     * 
     * <b>NOTE:</b> add super.render and super.reset at the end
     *  or initialize transform and renderer.
     * 
     * @param Graphics2D graphics from the Screen class.
     * @see Screen - SKEngine.Core
     * */
	public void render(Graphics2D g) {
		renderer.render(transform, g,boundingbox, rigidbody);
	}

    public void reset(Graphics2D g) {
        renderer.reset(g);
    }

    /**
     * This is called when an object enters to this object's
     * bounding box.
     * <b>NOTE:</b> this function only called once on collision.
     * 
     * @param GameObject other object collided
     * */
    public void onCollisionEnter(GameObject obj) {

    }

    /**
     * This is called when an object stays inside this object's
     * bounding box.
     * <b>NOTE:</b> this function called as long as the object
     * is overlapping to this bounding box.
     * 
     * @param GameObject other object collided
     * */
    public void onCollisionStay(GameObject obj) {

    }

    /**
     * This is called when an object exits to this object's
     * bounding box.
     * <b>NOTE:</b> this function only called once after collision.
     * 
     * @param GameObject other object collided
     * */
    public void onCollisionExit(GameObject obj) {

    }

    /**
     * This function is called when there are a specific command
     * that is available from the network client.
     * 
     * @param String actor command
     * @see NetworkActor - SKEngine.Network
     * */
    public void onNetworkEvent(String string) {
        
    }

    /**
     * Global function that creates a game object in runtime
     * <b>NOTE:</b> this function is pretty heavy. Consider object
     * pooling.
     * */
    public static void instantiate(GameObject obj) {
        GameEngine.instance.addObject(obj);
    }
    
    /**
     * Global function that destroys a game object in runtime
     * <b>NOTE:</b> this function is pretty heavy. Consider object
     * pooling.
     * */
    public static void destroy(GameObject obj) {
        GameEngine.instance.removeObject(obj);
    }
}