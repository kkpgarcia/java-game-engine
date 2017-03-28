public class Vector2 {
    public float x;
    public float y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static float dot(Vector2 v1, Vector2 v2) {
        float result = 0.0f;
        result = v1.x * v2.x + v1.y * v2.y;
        return result;
    }

    public float magnitude() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public static float distance(Vector2 v1, Vector2 v2) {
        return (float) Math.sqrt((v2.x - v1.x) * (v2.x - v1.x) + (v2.y - v1.y) * (v2.y - v1.y));
    }

    public Vector2 add(Vector2 v2) {
        Vector2 result = new Vector2();
        result.x = this.x + v2.x;
        result.y = this.y + v2.y;
        return result;
    }

    public Vector2 subtract(Vector2 v2) {
        Vector2 result = new Vector2();
        result.x = this.x - v2.x;
        result.y = this.y - v2.y;
        return result;
    }

    public Vector2 multiply(float scaleFactor) {
        Vector2 result = new Vector2();
        result.x = this.x * scaleFactor;
        result.y = this.y * scaleFactor;
        return result;
    }

    public static Vector2 delta(Vector2 v1, Vector2 v2) {
        Vector2 result = new Vector2();
        result.x = v1.x - v2.x;
        result.y = v1.y - v2.y;
        return result;
    }

    public boolean greaterThan(Vector2 v2) {
        return Comparison.tolerantGreaterThanOrEquals(this.x, v2.x) ||
        Comparison.tolerantGreaterThanOrEquals(this.y, v2.y);
    }

    public boolean lesserThan(Vector2 v2) {
        return Comparison.tolerantLesserThanOrEquals(this.x, v2.x) ||
        Comparison.tolerantLesserThanOrEquals(this.y, v2.y);
    }

    public Vector2 normalize() {
        float len = magnitude();
        if(len != 0.0f) {
            this.x = this.x / len;
            this.y = this.y / len;
        } else {
            this.x = 0.0f;
            this.y = 0.0f;
        }

        return this;
    }

    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}