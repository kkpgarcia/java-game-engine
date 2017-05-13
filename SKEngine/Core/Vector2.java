package SKEngine.Core;

import SKEngine.Calculations.MathEx;

public class Vector2 {
	public float x;
	public float y;

	public Vector2(){
		this(0,0);
	}

	public Vector2( float x, float y ) {
		set( x, y );
	}

	public Vector2( Vector2 v ) {
		set( v );
	}

	public void set( float x, float y ) {
		this.x = x;
		this.y = y;
	}

	public Vector2 set( Vector2 v ) {
		x = v.x;
		y = v.y;
		return this;
	}

	/**
	 * Adds s to this vector and returns this. 
	 */
	public Vector2 addi( float s ) {
		return add( s, this );
	}

	/**
	 * Sets out to the sum of this vector and s and returns out.
	 */
	public Vector2 add( float s, Vector2 out ) {
		out.x = x + s;
		out.y = y + s;
		return out;
	}

	/**
	 * Returns a new vector that is the sum between this vector and s.
	 */
	public Vector2 add( float s ) {
		return add( s, new Vector2() );
	}

	/**
	 * Multiplies this vector by s and returns this.
	 */
	public Vector2 multiplyi( float s ) {
		return multiply( s, this );
	}

	/**
	 * Sets out to this vector multiplied by s and returns out.
	 */
	public Vector2 multiply( float s, Vector2 out ) {
		out.x = s * x;
		out.y = s * y;
		return out;
	}

	/**
	 * Returns a new vector that is a multiplication of this vector and s.
	 */
	public Vector2 multiply( float s ) {
		return multiply( s, new Vector2() );
	}

	/**
	 * Divides this vector by s and returns this.
	 */
	public Vector2 dividei( float s ) {
		return divide( s, this );
	}

	/**
	 * Sets out to the division of this vector and s and returns out.
	 */
	public Vector2 divide( float s, Vector2 out ) {
		out.x = x / s;
		out.y = y / s;
		return out;
	}

	/**
	 * Returns a new vector that is a division between this vector and s.
	 */
	public Vector2 divide( float s ) {
		return divide( s, new Vector2() );
	}

	/**
	 * Multiplies this vector by v and returns this.
	 */
	public Vector2 multiplyi( Vector2 v ) {
		return multiply( v, this );
	}

	/**
	 * Sets out to the product of this vector and v and returns out.
	 */
	public Vector2 multiply( Vector2 v, Vector2 out ) {
		out.x = x * v.x;
		out.y = y * v.y;
		return out;
	}

	/**
	 * Returns a new vector that is the product of this vector and v.
	 */
	public Vector2 multiply( Vector2 v ) {
		return multiply( v, new Vector2() );
	}

	/**
	 * Divides this vector by v and returns this.
	 */
	public Vector2 dividei( Vector2 v ) {
		return divide( v, this );
	}

	/**
	 * Sets out to the division of this vector and v and returns out.
	 */
	public Vector2 divide( Vector2 v, Vector2 out ) {
		out.x = x / v.x;
		out.y = y / v.y;
		return out;
	}

	/**
	 * Returns a new vector that is the division of this vector by v.
	 */
	public Vector2 divide( Vector2 v ) {
		return divide( v, new Vector2() );
	}

	/**
	 * Adds v to this vector and returns this.
	 */
	public Vector2 addi( Vector2 v ) {
		return add( v, this );
	}

	/**
	 * Sets out to the addition of this vector and v and returns out.
	 */
	public Vector2 add( Vector2 v, Vector2 out ) {
		out.x = x + v.x;
		out.y = y + v.y;
		return out;
	}

	/**
	 * Returns a new vector that is the addition of this vector and v.
	 */
	public Vector2 add( Vector2 v ) {
		return add( v, new Vector2() );
	}

	/**
	 * Adds v * s to this vector and returns this.
	 */
	public Vector2 addscalei( Vector2 v, float s ) {
		return addscale( v, s, this );
	}

	/**
	 * Sets out to the addition of this vector and v * s and returns out.
	 */
	public Vector2 addscale( Vector2 v, float s, Vector2 out ) {
		out.x = x + v.x * s;
		out.y = y + v.y * s;
		return out;
	}

	/**
	 * Returns a new vector that is the addition of this vector and v * s.
	 */
	public Vector2 addscale( Vector2 v, float s ) {
		return addscale( v, s, new Vector2() );
	}

	/**
	 * Subtracts v from this vector and returns this.
	 */
	public Vector2 subtracti( Vector2 v ) {
		return subtract( v, this );
	}

	/**
	 * Sets out to the subtraction of v from this vector and returns out.
	 */
	public Vector2 subtract( Vector2 v, Vector2 out ) {
		out.x = x - v.x;
		out.y = y - v.y;
		return out;
	}

	/**
	 * Returns a new vector that is the subtraction of v from this vector.
	 */
	public Vector2 subtract( Vector2 v ) {
		return subtract( v, new Vector2() );
	}

	/**
	 * Returns the squared length of this vector.
	 */
	public float lengthSq() {
		return x * x + y * y;
	}

	/**
	 * Returns the length of this vector.
	 */
	public float length() {
		return (float)StrictMath.sqrt( x * x + y * y );
	}

	/**
	 * Rotates this vector by the given radians.
	 */
	public void rotate( float radians ) {
		float c = (float)StrictMath.cos( radians );
		float s = (float)StrictMath.sin( radians );

		float xp = x * c - y * s;
		float yp = x * s + y * c;

		x = xp;
		y = yp;
	}

	public float magnitude() {
        return (float)StrictMath.sqrt(this.x * this.x + this.y * this.y);
    }

	/**
	 * Normalizes this vector, making it a unit vector. A unit vector has a length of 1.0.
	 */
	public void normalize() {
		float lenSq = lengthSq();

		if (lenSq > MathEx.EPSILON_SQ)
		{
			float invLen = 1.0f / (float)StrictMath.sqrt( lenSq );
			x *= invLen;
			y *= invLen;
		}
	}

	/**
	 * Sets this vector to the minimum between a and b.
	 */
	public Vector2 mini( Vector2 a, Vector2 b ) {
		return min( a, b, this );
	}

	public static Vector2 min( Vector2 a, Vector2 b, Vector2 out )
	{
		out.x = (float)StrictMath.min( a.x, b.x );
		out.y = (float)StrictMath.min( a.y, b.y );
		return out;
	}

	/**
	 * Sets this vector to the maximum between a and b.
	 */
	public Vector2 maxi( Vector2 a, Vector2 b ) {
		return max( a, b, this );
	}

	public static Vector2 max( Vector2 a, Vector2 b, Vector2 out ) {
		out.x = (float)StrictMath.max( a.x, b.x );
		out.y = (float)StrictMath.max( a.y, b.y );
		return out;
	}

	/**
	 * Returns the dot product between this vector and v.
	 */
	public float dot( Vector2 v ) {
		return dot( this, v );
	}

	/**
	 * Returns the squared distance between this vector and v.
	 */
	public float distanceSq( Vector2 v ) {
		return distanceSq( this, v );
	}

	/**
	 * Returns the distance between this vector and v.
	 */
	public float distance( Vector2 v ) {
		return distance( this, v );
	}

	/**
	 * Sets this vector to the cross between v and a and returns this.
	 */
	public Vector2 cross( Vector2 v, float a ) {
		return cross( v, a, this );
	}

	/**
	 * Sets this vector to the cross between a and v and returns this.
	 */
	public Vector2 cross( float a, Vector2 v ) {
		return cross( a, v, this );
	}

	/**
	 * Returns the scalar cross between this vector and v. This is essentially
	 * the length of the cross product if this vector were 3d. This can also
	 * indicate which way v is facing relative to this vector.
	 */
	public float cross( Vector2 v ) {
		return cross( this, v );
	}

	public static Vector2 cross( Vector2 v, float a, Vector2 out ) {
		out.x = v.y * a;
		out.y = v.x * -a;
		return out;
	}

	public static Vector2 cross( float a, Vector2 v, Vector2 out ) {
		out.x = v.y * -a;
		out.y = v.x * a;
		return out;
	}

	public static float cross( Vector2 a, Vector2 b ) {
		return a.x * b.y - a.y * b.x;
	}

	public static float dot( Vector2 a, Vector2 b ) {
		return a.x * b.x + a.y * b.y;
	}

	public static float distanceSq( Vector2 a, Vector2 b ) {
		float dx = a.x - b.x;
		float dy = a.y - b.y;

		return dx * dx + dy * dy;
	}

	public static float distance( Vector2 a, Vector2 b ) {
		float dx = a.x - b.x;
		float dy = a.y - b.y;

		return (float)StrictMath.sqrt( dx * dx + dy * dy );
	}

	/**
	 * Negates this vector and returns this.
	 */
	public Vector2 negatei() {
		return negate( this );
	}

	/**
	 * Sets out to the negation of this vector and returns out.
	 */
	public Vector2 negate( Vector2 out ) {
		out.x = -x;
		out.y = -y;
		return out;
	}

	/**
	 * Returns a new vector that is the negation to this vector.
	 */
	public Vector2 negate() {
		return negate( new Vector2() );
	}

	/**
	 * Returns an array of allocated Vector2 of the requested length.
	 */
	public static Vector2[] arrayOf( int length ) {
		Vector2[] array = new Vector2[length];

		while (--length >= 0)
		{
			array[length] = new Vector2();
		}

		return array;
	}

}
