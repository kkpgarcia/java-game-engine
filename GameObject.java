import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public abstract class GameObject {

    public Transform transform;
    //public BoundingBox2D boundingBox;
    public Renderer renderer;
    public Rigidbody rigidbody;

    public Color color;

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
}