public class BoundingBox2D {
    public Vector2 min;
    public Vector2 max;
    private float BIG_NUMBER = 1e3f;

    public boolean isCollided;

    public BoundingBox2D() {
        this.min = new Vector2();
        this.max = new Vector2();
        empty();
    }

    public BoundingBox2D(Vector2 vec1, Vector2 vec2) {
        this.min = new Vector2();
        this.max = new Vector2();
        empty();

        addToContain(vec1);
        addToContain(vec2);
    }

    public void empty() {
        min.x = BIG_NUMBER;
        min.y = BIG_NUMBER;
        max.x = -BIG_NUMBER;
        max.y = -BIG_NUMBER;
    }

    public boolean isEmpty() {
        return (min.x > max.x && min.y > max.y);
    }

    public Vector2 getCenter() {
        Vector2 min = new Vector2(this.min.x, this.min.y);
        Vector2 max = new Vector2(this.max.x, this.max.y);
        return min.addi(max).multiplyi(0.5f);
    }

    public Vector2 getSize() {
        Vector2 min = new Vector2(this.min.x, this.min.y);
        Vector2 max = new Vector2(this.max.x, this.max.y);
        return max.subtracti(min);
    }

    public Vector2 getRadiusVector() {
        return getSize().multiplyi(0.5f);
    }

    public float getRadius() {
        return getRadiusVector().magnitude();
    }

    public void addToContain(Vector2 v) {
        if(v.x < min.x)
            min.x = v.x;
        if(v.y < min.y)
            min.y = v.y;
        if(v.x > max.x)
            max.x = v.x;
        if(v.y > max.y)
            max.y = v.y;
    }

    public boolean contains(Vector2 v) {
        return MathEx.tolerantLesserThanOrEquals(min.x, v.x)
        && MathEx.tolerantLesserThanOrEquals(v.x, max.x)
        && MathEx.tolerantLesserThanOrEquals(min.y, v.y)
        && MathEx.tolerantLesserThanOrEquals(v.y, max.y);
    }

    public boolean isOverlapping(BoundingBox2D otherBox) {
        return contains(otherBox.getTopLeft())
        || contains(otherBox.getBottomLeft())
        || contains(otherBox.getTopRight())
        || contains(otherBox.getBottomRight());
    }

    public void translate(Vector2 translation) {
        if(isEmpty())
            return;

        Vector2 center = getCenter();
        /*min = min.subtract(center);
        max = max.subtract(center);
        min = min.add(translation);
        max = max.add(translation);*/

        min.subtracti(center);
        max.subtracti(center);
        
        min.addi(translation);
        max.addi(translation);
    }

    public Vector2 getTopLeft() {
        return new Vector2(min.x, max.y);
    }

    public Vector2 getBottomLeft() {
        return min;
    }

    public Vector2 getTopRight() {
        return max;
    }

    public Vector2 getBottomRight() {
        return new Vector2(max.x, min.y);
    }

    public String toString() {
        return "MIN: " + min.toString() + " MAX: " + max.toString();
    }
}