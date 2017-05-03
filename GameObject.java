import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public abstract class GameObject {

    public Transform transform;
    public Renderer renderer;
    public Rigidbody rigidbody;

    //To be omitted
    public Color color;

    //To be omitted
    public boolean isTrigger = false;

	public GameObject() {
		this.transform = new Transform();
        this.renderer = new Renderer();
	}

	public void update() {
        
    }

	public void render(Graphics2D g) {
		renderer.render(transform, g);
	}

    public void reset(Graphics2D g) {
        renderer.reset(g);
    }

    public static void instantiate(GameObject obj) {
        GameEngine.instance.addObject(obj);
    }
    
    public static void destroy(GameObject obj) {
        GameEngine.instance.removeObject(obj);
    }
}