package SKEngine.Calculations;

import SKEngine.Core.Vector2;

/**
 * <h2>Math Extension</h2>
 * Provides pre-calculated and new calculations for the engine.
 * Mostly used by the Physics engine.
 * <p>
 * @see StrictMath - java.lang
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-04-17
 * */
public class MathEx {
    public static final float PI = (float)StrictMath.PI;
	public static final float EPSILON = 0.0002f;
	public static final float EPSILON_SQ = EPSILON * EPSILON;
	public static final float BIAS_RELATIVE = 0.95f;
	public static final float BIAS_ABSOLUTE = 0.01f;
	public static final float DT = 1.0f / 60.0f;
	public static final Vector2 GRAVITY = new Vector2( 0.0f, 100.0f );
	public static final float RESTING = GRAVITY.multiply( DT ).lengthSq() + EPSILON;
	public static final float PENETRATION_ALLOWANCE = 0.05f;
	public static final float PENETRATION_CORRECTION = 0.4f;

    /**
     * Compares the difference of two floating numbers by an epsilon
     * @param float from
     * @param float against
     * @return boolean from == against
     * */
    public static boolean tolerantEquals(float a, float b) {
        return ((StrictMath.abs(a - b)) <= EPSILON);
    }

    /**
     * Compares the difference of two floating numbers by an epsilon
     * @param float from
     * @param float against
     * @return boolean from >= against
     * */
    public static boolean tolerantGreaterThanOrEquals(float a, float b) {
        return a > b || tolerantEquals(a, b);
    }

    /**
     * Compares the difference of two floating numbers by an epsilon
     * @param float from
     * @param float against
     * @return boolean from <= against
     * */
    public static boolean tolerantLesserThanOrEquals(float a, float b) {
        return a < b || tolerantEquals(a, b);
    }

    /**
     * Clamps a floating number by a given range.
     * @param float minimum range
     * @param float maximum range
     * @param float number to be clamped
     * @return float number clamped
     * */
    public static float clamp( float min, float max, float a ) {
		return (a < min ? min : (a > max ? max : a));
	}

    /**
     * Rounds up a float.
     * @param float number to be rounded
     * @return int rounded float
     * */
    public static int round(float a) {
        return (int)(a + 0.5f);
    }

    /**
     * Provides a random float between the given range
     * @param float minimum
     * @param float maximum
     * @return float random number
     * */
    public static float random(float min, float max) {
        return (float)((max - min) * Math.random() + min);
    }

    /**
     * Provides a random int between the given range
     * @param int minimum
     * @param int maximum
     * @return int random number
     * */
    public static int random(int min, int max) {
        return (int)((max - min + 1) * Math.random() + min);
    }

    /**
     * Compares two floats againts a bias
     * @param float from
     * @param float against
     * @return boolean a >= b with bias
     * */
    public static boolean gt(float a, float b) {
        return a >= b * BIAS_RELATIVE + a * BIAS_ABSOLUTE;
    }
}