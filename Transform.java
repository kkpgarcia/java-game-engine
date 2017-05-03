public class Transform {
    public Vector2 position;
    public Vector2 rotation;
    public Vector2 scale;
    public Vector2 prevPosition;

    public Transform() {
        this.position = new Vector2();
        this.rotation = new Vector2();
        this.scale = new Vector2(1,1);
        this.prevPosition = new Vector2();
    }

    public Transform(Vector2 position) {
        this.position = position;
        this.rotation = new Vector2();
        this.scale = new Vector2(1,1);
        this.prevPosition = new Vector2();
    }

    public Transform(Vector2 position, Vector2 rotation) {
        this(position);
        this.rotation = rotation;
        this.scale = new Vector2(1,1);
        this.prevPosition = new Vector2();
    }

    public Transform(Vector2 position, Vector2 rotation, Vector2 scale) {
        this(position, rotation);
        this.scale = scale;
        this.prevPosition = new Vector2();
    }

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

    public String toString() {
        return "Position -> " + position.toString() 
        + "\n Rotation -> " + rotation.toString() 
        + "\n Scale -> " + scale.toString();
    }
}