public class Aabb2D {
    public Vector2 position;
    public float width;
    public float height;
    public boolean isOnCollision = false;

    private float OFFSET = 10;
    public Aabb2D(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    public Aabb2D() {
        this(0,0,0,0);
    }

    public Aabb2D(float width, float height) {
        this(0,0,width,height);
    }

    public Vector2 getCenter() {
        return new Vector2(width/2, height/2);
    }

    public boolean isOverlapping(Aabb2D other) {
        return this.position.add(new Vector2(width/2, height/2)).greaterThan(other.position.add(new Vector2(other.width/2, other.height/2)))
        || this.position.add(new Vector2(width/2, height/2)).lesserThan(other.position.add(new Vector2(other.width/2, other.height/2)));
    }

    public boolean isColliding(Aabb2D other) {
        return position.x < other.position.x + other.width && 
                    position.x + width > other.position.x &&
                    position.y < other.position.y + other.height && 
                    position.y + height > other.position.y;
    }

    public String toString() {
        return this.width + " x " + this.height;
    }
}