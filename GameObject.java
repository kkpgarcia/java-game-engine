import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public abstract class GameObject {

    public Transform transform;
    public BoundingBox2D boundingbox;
    public Renderer renderer;
    public Rigidbody rigidbody;

	public GameObject() {
		this.transform = new Transform();
        this.renderer = new Renderer();
	}

	public void update() {
        this.transform.position = this.rigidbody.position;//this.transform.position.add(rigidbody.deltaPosition);
    }

	public void render(Graphics2D g) {
		renderer.render(transform, g);
	}

    public void reset(Graphics2D g) {
        renderer.reset(g);
    }

    public void onCollisionEnter(GameObject obj) {

    }

    public void onCollisionStay(GameObject obj) {

    }

    public void onCollisionExit(GameObject obj) {

    }

    public static void instantiate(GameObject obj) {
        GameEngine.instance.addObject(obj);
    }
    
    public static void destroy(GameObject obj) {
        GameEngine.instance.removeObject(obj);
    }
}