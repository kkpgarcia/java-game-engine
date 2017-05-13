package SKEngine.Collision;

import SKEngine.Core.Vector2;
import SKEngine.Calculations.MathEx;

/**
 * <h2>Bounding Box 2D</h2>
 * Bounding box implementation for simple 2D collision/overlap check
 * <p>
 * @see Animation - SKEngine.Animation
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-04
 * */
public class BoundingBox2D {
    public Vector2 min;
    public Vector2 max;
    private float BIG_NUMBER = 1e3f;

    public boolean isCollided;

    /**
     * Creates an empty bounding box.
     * */
    public BoundingBox2D() {
        this.min = new Vector2();
        this.max = new Vector2();
        empty();
    }

    /**
     * Creates a bounding box that contains a specific size
     * */
    public BoundingBox2D(Vector2 vec1, Vector2 vec2) {
        this.min = new Vector2();
        this.max = new Vector2();
        empty();

        addToContain(vec1);
        addToContain(vec2);
    }

    /**
     * Sets the bounding box to the highest possible number
     * */
    public void empty() {
        min.x = BIG_NUMBER;
        min.y = BIG_NUMBER;
        max.x = -BIG_NUMBER;
        max.y = -BIG_NUMBER;
    }

    /**
     * Checks if the bounding box is empty
     * @returns boolean if the bounding box if empty
     * */
    public boolean isEmpty() {
        return (min.x > max.x && min.y > max.y);
    }

    /**
     * Gets the center of the bounding box
     * @return Vector2 center point of the bounding box
     * */
    public Vector2 getCenter() {
        Vector2 min = new Vector2(this.min.x, this.min.y);
        Vector2 max = new Vector2(this.max.x, this.max.y);
        return min.addi(max).multiplyi(0.5f);
    }

    /**
     * Gets the size of the bounding box
     * @return Vector2 size of the bounding box
     * */
    public Vector2 getSize() {
        Vector2 min = new Vector2(this.min.x, this.min.y);
        Vector2 max = new Vector2(this.max.x, this.max.y);
        return max.subtracti(min);
    }

    /**
     * Gets the radius vector of the bounding box
     * @return Vector2 radius vector of the bounding box
     * */
    public Vector2 getRadiusVector() {
        return getSize().multiplyi(0.5f);
    }

    /**
     * Gets the radius of the bounding box
     * @return float radius
     * */
    public float getRadius() {
        return getRadiusVector().magnitude();
    }

    /**
     * Adds the new vector to the current bounding box
     * @param Vector2 to be added
     * */
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

    /**
     * Checks if the bounding box contains certain point
     * @param Vector2 point to be checked
     * */
    public boolean contains(Vector2 v) {
        return MathEx.tolerantLesserThanOrEquals(min.x, v.x)
        && MathEx.tolerantLesserThanOrEquals(v.x, max.x)
        && MathEx.tolerantLesserThanOrEquals(min.y, v.y)
        && MathEx.tolerantLesserThanOrEquals(v.y, max.y);
    }

    /**
     * Checks if this bounding box contains the top left, bottom left,
     * top right, and bottom right of the other bounding box.
     * @param BoundingBox2D other box
     * @return boolean if the other box overlaps in this bounding box
     * */
    public boolean isOverlapping(BoundingBox2D otherBox) {
        return contains(otherBox.getTopLeft())
        || contains(otherBox.getBottomLeft())
        || contains(otherBox.getTopRight())
        || contains(otherBox.getBottomRight());
    }

    /**
     * Translates the minimum, and the maximum of this bounding box.
     * @param Vector2 translation
     * */
    public void translate(Vector2 translation) {
        if(isEmpty())
            return;

        Vector2 center = getCenter();

        min.subtracti(center);
        max.subtracti(center);
        
        min.addi(translation);
        max.addi(translation);
    }

    /**
     * Gets the top left point of the bounding box
     * @return Vector2 top left point
     * */
    public Vector2 getTopLeft() {
        return new Vector2(min.x, max.y);
    }

    /**
     * Gets the bottom left point of the bounding box
     * @return Vector2 bottom left point
     * */
    public Vector2 getBottomLeft() {
        return min;
    }

    /**
     * Gets the top right point of the bounding box
     * @return Vector2 top right point
     * */
    public Vector2 getTopRight() {
        return max;
    }

    /**
     * Gets the bottom right point of the bounding box
     * @return Vector2 bottom right point
     * */
    public Vector2 getBottomRight() {
        return new Vector2(max.x, min.y);
    }

    public String toString() {
        return "MIN: " + min.toString() + " MAX: " + max.toString();
    }
}