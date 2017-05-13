package SKEngine.Core;

/**
 * <h2>Transform</h2>
 * This is a basic component of the game object. Contains
 * position information, rotation information, scale information, and
 * parent information.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */
public class Transform {
    public Vector2 position;
    public Vector2 rotation;
    public Vector2 scale;
    public Vector2 prevPosition;

    private GameObject parent;

    /**
     * Constructs a transform with Vector2 zero on position,
     * rotation, scale.
     * */
    public Transform() {
        this.position = new Vector2();
        this.rotation = new Vector2();
        this.scale = new Vector2(1,1);
        this.prevPosition = new Vector2();
    }

    /**
     * Constructs a transform with a pre-defined position.
     * @param Vector2 position
     * */
    public Transform(Vector2 position) {
        this.position = position;
        this.rotation = new Vector2();
        this.scale = new Vector2(1,1);
        this.prevPosition = new Vector2();
    }

    /**
     * Constructs a transform with a pre-defined position,
     * and rotation.
     * @param Vector2 position
     * @param Vector2 rotation
     * */
    public Transform(Vector2 position, Vector2 rotation) {
        this(position);
        this.rotation = rotation;
        this.scale = new Vector2(1,1);
        this.prevPosition = new Vector2();
    }

    /**
     * Constructs a transform with a pre-defined position,
     * rotation, scale.
     * @param Vector2 position
     * @param Vector2 rotation
     * @param Vector2 scale
     * */
    public Transform(Vector2 position, Vector2 rotation, Vector2 scale) {
        this(position, rotation);
        this.scale = scale;
        this.prevPosition = new Vector2();
    }

    /**
     * Translates this transform to a direction.
     * @param Vector2 direction
     * */
    public void translate(Vector2 direction) {
        this.prevPosition = this.position;
        this.position = this.position.add(direction);
    }

    
    public void rotate(Vector2 rotation) {
        this.rotation = this.rotation.add(rotation);
    }

    public void scale(Vector2 scale) {
        this.scale = this.scale.add(scale);
    }

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public String toString() {
        return "Position -> " + position.toString() 
        + "\n Rotation -> " + rotation.toString() 
        + "\n Scale -> " + scale.toString();
    }
}