public class MathEx {
    public static final float PI = (float)StrictMath.PI;
	public static final float EPSILON = 0.00015f;
	public static final float EPSILON_SQ = EPSILON * EPSILON;
	public static final float BIAS_RELATIVE = 0.95f;
	public static final float BIAS_ABSOLUTE = 0.01f;
	public static final float DT = 1.0f / 60.0f;
	public static final Vector2 GRAVITY = new Vector2( 0.0f, 500.0f );
	public static final float RESTING = GRAVITY.multiply( DT ).lengthSq() + EPSILON;
	public static final float PENETRATION_ALLOWANCE = 0.05f;
	public static final float PENETRATION_CORRECTION = 0.4f;

    public static boolean tolerantEquals(float a, float b) {
        return ((StrictMath.abs(a - b)) <= EPSILON);
    }

    public static boolean tolerantGreaterThanOrEquals(float a, float b) {
        return a > b || tolerantEquals(a, b);
    }

    public static boolean tolerantLesserThanOrEquals(float a, float b) {
        return a < b || tolerantEquals(a, b);
    }

    public static float clamp( float min, float max, float a ) {
		return (a < min ? min : (a > max ? max : a));
	}

    public static int round(float a) {
        return (int)(a + 0.5f);
    }

    public static float random(float min, float max) {
        return (float)((max - min) * Math.random() + min);
    }

    public static int random(int min, int max) {
        return (int)((max - min + 1) * Math.random() + min);
    }

    public static boolean gt(float a, float b) {
        return a >= b * BIAS_RELATIVE + a * BIAS_ABSOLUTE;
    }
}