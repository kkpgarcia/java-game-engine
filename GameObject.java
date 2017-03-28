import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public abstract class GameObject {

    public Transform transform;
    public Aabb2D aabb;
    public BoundingBox2D boundingBox;
    public Renderer renderer;
    public Rigidbody rigidbody;

    public Vector2 velocity;
    public Color color;

    public boolean isTrigger = false;

	public GameObject() {
		this.transform = new Transform();
        this.aabb = new Aabb2D();
        this.renderer = new Renderer();
        this.boundingBox = new BoundingBox2D(new Vector2(0,0), new Vector2(20, 20));
	}

	public void update() {
        //this.aabb.position = this.transform.position;
        this.boundingBox.translate(this.transform.position);
        
        //Collision to right screen
        if(this.transform.position.x + aabb.getCenter().x > Screen.width/2) {
            this.transform.position.x = -aabb.getCenter().x + Screen.width/2;
        }

        //Collision to left screen
        if(this.transform.position.x - aabb.getCenter().x < -(Screen.width/2)) {
            this.transform.position.x = aabb.getCenter().x - Screen.width/2;
        }

        //Collision to upper screen
        if(this.transform.position.y - aabb.getCenter().y < -Screen.height/2) {
            this.transform.position.y = aabb.getCenter().y - Screen.height/2;
        }

        //Collision to bottom screen
        if(this.transform.position.y + aabb.getCenter().y > (Screen.height/2)) {
            this.transform.position.y = -aabb.getCenter().y + Screen.height/2;
            rigidbody.isGrounded = true;
        }

        //System.out.println(isOverlapping)
	}

    public void updatePhysics() {

    }

    public void onCollision(GameObject other) {
        if(isTrigger || other.isTrigger)
            return;

        //Collision right
        if(this.transform.position.x + aabb.width/2 > other.transform.position.x + other.aabb.width/2) {
            this.transform.position.x = this.transform.position.x + other.velocity.x; //+ this.velocity.x;
        }

        //Collision down
        if(this.transform.position.y + aabb.height/2 > other.transform.position.y + other.aabb.height/2) {
            this.transform.position.y = this.transform.position.y + other.velocity.y;
        }

        //Collision left
        if(this.transform.position.x + aabb.width/2 < other.transform.position.x + other.aabb.width/2) {
            this.transform.position.x = this.transform.position.x - other.velocity.x;
        }

        //Collision up
        if(this.transform.position.y + aabb.height/2 < other.transform.position.y + other.aabb.height/2) {
            this.transform.position.y = this.transform.position.y - other.velocity.y;
        }
    }

    public void onCollisionEnter(GameObject other) {
        System.out.println("Hello Collision Enter");
    }

    public void onCollisionStay(GameObject other) {

    }

    public void onCollisionExit(GameObject other) {
        System.out.println("Hello Collision Exit");
    }

    public void onTriggerEnter(GameObject other) {
        System.out.println("Hello Trigger Enter");
    }

    public void onTriggerStay(GameObject other) {

    }

    public void onTriggerExit(GameObject other) {
        System.out.println("Hello Trigger Exit");
    }

	public void render(Graphics2D g) {
		renderer.render(transform, g);

        g.setColor(java.awt.Color.BLUE);
        g.drawRect((int)transform.position.x - 10, (int)transform.position.y - 10,20,20);
	}

    public void reset(Graphics2D g) {
        renderer.reset(g);
    }
}