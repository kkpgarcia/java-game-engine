public class Vector2 {
    public float x;
    public float y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        set(x,y);
    }

    public Vector2(Vector2 vec) {
        this(vec.x, vec.y);
    }

    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 set(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
        return this;
    }

    /**
     * Global function for cross multiplication between two vectors
     * @param Vector2 first
     * @param Vector2 against
     * @return float result
     * */
    public static float cross(Vector2 v1, Vector2 v2) {
        float result = 0.0f;
        result = v1.x * v2.y - v1.y * v2.x;
        return result;
    }

    public static Vector2 cross(Vector2 v, float a, Vector2 out) {
        out.x = v.y * a;
        out.y = v.x * -a;
        return out;
    }

    public static Vector2 cross(float a, Vector2 v, Vector2 out) {
        out.x = v.y * -a;
        out.y = v.x * a;
        return out;
    }

    /**
     * Global function for dot product between two vevtors
     * @param Vector2 first
     * @param Vector2 against
     * @return float result
     * */
    public static float dot(Vector2 v1, Vector2 v2) {
        float result = 0.0f;
        result = v1.x * v2.x + v1.y * v2.y;
        return result;
    }

    /**
     * Global function for computing the direction of an object to another object
     * @param Vector2 from
     * @param Vector2 to
     * @return Vecotr2 direction
     * */
    public static Vector2 direction(Vector2 v1, Vector2 v2) {
        return v1.subtract(v2).normalize();
    }


    /**
     * Global function that calculates the distance between two objects
     * @param Vector2 first object
     * @param Vector2 second object
     * @return float distance between two objects
     * */
    public static float distance(Vector2 v1, Vector2 v2) {
        return (float) Math.sqrt((v2.x - v1.x) * (v2.x - v1.x) + (v2.y - v1.y) * (v2.y - v1.y));
    }

    public static float distanceSq(Vector2 a, Vector2 b) {
        float dx = a.x - b.x;
        float dy = a.y - b.y;
        return dx * dx + dy * dy;
    }

    /**
     * Global function that sets that calculates the minimum between vector 1 and vector 2
     * @param Vector2 first
     * @param Vector2 to other
     * @return Vector2 minimum
     * */
    public static Vector2 min(Vector2 v1, Vector2 v2) {
        Vector2 temp = new Vector2();
        temp.x = (float)Math.min(v1.x, v2.x);
        temp.y = (float)Math.min(v1.y, v2.y);
        return temp;
    }

    /**
     * Global function that sets that calculates the maximum between vector 1 and vector 2
     * @param Vector2 first
     * @param Vector2 to other
     * @return Vector2 maximum
     * */
    public static Vector2 max(Vector2 v1, Vector2 v2) {
        Vector2 temp = new Vector2();
        temp.x = (float)Math.max(v1.x, v2.x);
        temp.y = (float)Math.max(v1.y, v2.y);
        return temp;
    }

    /**
     * Rotates the vector by radians
     * @param float radians
     * */
    public void rotate(float radians) {
        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);
        float nx = this.x * cos - this.y * sin;
        float ny = this.x * sin + this.y * cos;

        this.x = nx;
        this.y = ny; 
    }

    public float magnitude() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public float lengthSq() {
        return x * x + y * y;
    }

    /**
     * Adds this vector to another vector
     * @param Vector2 add values
     * @return Vector2 result
     * */
    public Vector2 add(Vector2 v2) {
        Vector2 result = new Vector2();
        result.x = this.x + v2.x;
        result.y = this.y + v2.y;
        return result;
    }

    /**
     * Substracts this vector to another vector
     * @param Vector2 subtract values
     * @return Vector2 result
     * */
    public Vector2 subtract(Vector2 v2) {
        Vector2 result = new Vector2();
        result.x = this.x - v2.x;
        result.y = this.y - v2.y;
        return result;
    }

    /**
     * Multiplies this vector to another vector
     * @param float multiply by value
     * @return Vector2 result
     * */
    public Vector2 multiply(float scaleFactor) {
        Vector2 result = new Vector2();
        result.x = this.x * scaleFactor;
        result.y = this.y * scaleFactor;
        return result;
    }

    /**
     * Divides this vector to anoter vector
     * @param float divide by value
     * @return Vector2 result
     * */
    public Vector2 divide(float scaleFactor) {
        Vector2 result = new Vector2();
        result.x = this.x / scaleFactor;
        result.y = this.y /scaleFactor;
        return result;
    }

    /**
     * Checks if this vector is greater than the other vector
     * @param Vector2 other vector
     * @return boolean result
     * */
    public boolean greaterThan(Vector2 v2) {
        return MathEx.tolerantGreaterThanOrEquals(this.x, v2.x) ||
        MathEx.tolerantGreaterThanOrEquals(this.y, v2.y);
    }

    /**
     * Checks if this vector is lesser than the other vector
     * @param Vector2 other vector
     * @return boolean result
     * */
    public boolean lesserThan(Vector2 v2) {
        return MathEx.tolerantLesserThanOrEquals(this.x, v2.x) ||
        MathEx.tolerantLesserThanOrEquals(this.y, v2.y);
    }

    /**
     * Converts the vector to a unit vector. (Unit vector contains a maximum value of 1.0)
     * @return Vector2 normalized vector
     * */
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

    public Vector2 negate() {
        return negate(this);
    }

    public Vector2 negate(Vector2 out) {
        out.x = -x;
        out.y = -y;
        return out;
    }

    public static Vector2[] arrayOf(int length) {
        Vector2[] array = new Vector2[length];
        while(--length >= 0) {
            array[length] = new Vector2();
        }
        return array;
    }

    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}