package SKEngine.Core;


/**
 * <h2>Matrix 2 by 2</h2>
 * Two by two matrix structure for 2D transformations.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */
public class Mat2x2 {

	public float m00, m01;
	public float m10, m11;

	/**
	 * Constructs an empty matrix.
	 * */
	public Mat2x2()
	{
	}

	/**
	 * Constructs a rotated matrix.
	 * @param float radians
	 * */
	public Mat2x2( float radians ) {
		set( radians );
	}

	/**
	 * Constructs a matrix with value.
	 * @param a 00
	 * @param b 01
	 * @param c 10
	 * @param d 11
	 * */
	public Mat2x2( float a, float b, float c, float d ) {
		set( a, b, c, d );
	}

	/**
	 * Sets this matrix to a rotation matrix with the given radians.
	 * @param float radians
	 */
	public void set( float radians ) {
		float c = (float)StrictMath.cos( radians );
		float s = (float)StrictMath.sin( radians );

		m00 = c;
		m01 = -s;
		m10 = s;
		m11 = c;
	}

	/**
	 * Sets the values of this matrix.
	 * @param a 00
	 * @param b 01
	 * @param c 10
	 * @param d 11
	 */
	public void set( float a, float b, float c, float d ) {
		m00 = a;
		m01 = b;
		m10 = c;
		m11 = d;
	}

	/**
	 * Sets this matrix to have the same values as the given matrix.
	 * @param Mat2x2 other matrix
	 */
	public void set( Mat2x2 m ) {
		m00 = m.m00;
		m01 = m.m01;
		m10 = m.m10;
		m11 = m.m11;
	}

	/**
	 * Sets the values of this matrix to their absolute value.
	 */
	public void absi() {
		abs( this );
	}

	/**
	 * Returns a new matrix that is the absolute value of this matrix.
	 * @return Mat2x2 an absolute value of this matrix
	 */
	public Mat2x2 abs() {
		return abs( new Mat2x2() );
	}

	/**
	 * Sets out to the absolute value of this matrix.
	 * @return Mat2x2 absolute value of this matrix
	 */
	public Mat2x2 abs( Mat2x2 out ) {
		out.m00 = StrictMath.abs( m00 );
		out.m01 = StrictMath.abs( m01 );
		out.m10 = StrictMath.abs( m10 );
		out.m11 = StrictMath.abs( m11 );
		return out;
	}

	/**
	 * Sets out to the x-axis (1st column) of this matrix.
	 * @return Vector2 x axis
	 */
	public Vector2 getAxisX( Vector2 out ) {
		out.x = m00;
		out.y = m10;
		return out;
	}

	/**
	 * Returns a new vector that is the x-axis (1st column) of this matrix.
	 * @return Vector2 new vector with x axis
	 */
	public Vector2 getAxisX() {
		return getAxisX( new Vector2() );
	}

	/**
	 * Sets out to the y-axis (2nd column) of this matrix.
	 * @return Vector2 new vector with y axis
	 */
	public Vector2 getAxisY( Vector2 out ) {
		out.x = m01;
		out.y = m11;
		return out;
	}

	/**
	 * Returns a new vector that is the y-axis (2nd column) of this matrix.
	 * @return Vector2 new vector with y axis
	 */
	public Vector2 getAxisY() {
		return getAxisY( new Vector2() );
	}

	/**
	 * Sets the matrix to it's transpose.
	 */
	public void transposei() {
		float t = m01;
		m01 = m10;
		m10 = t;
	}

	/**
	 * Sets out to the transpose of this matrix.
	 * @return Mat2x2 transposed matrix
	 */
	public Mat2x2 transpose( Mat2x2 out ) {
		out.m00 = m00;
		out.m01 = m10;
		out.m10 = m01;
		out.m11 = m11;
		return out;
	}

	/**
	 * Returns a new matrix that is the transpose of this matrix.
	 * @return Mat2x2 transposed matrix
	 */
	public Mat2x2 transpose() {
		return transpose( new Mat2x2() );
	}

	/**
	 * Transforms v by this matrix.
	 * @return Vector2 transforms, and multiply a vector to matrix
	 */
	public Vector2 muli( Vector2 v ) {
		return mul( v.x, v.y, v );
	}

	/**
	 * Sets out to the transformation of v by this matrix.
	 * @return Vector2 transforms, and multiply a vector to matrix
	 */
	public Vector2 mul( Vector2 v, Vector2 out ) {
		return mul( v.x, v.y, out );
	}

	/**
	 * Returns a new vector that is the transformation of v by this matrix.
	 * @@return Vector2 transforms, and multiply a vector to matrix
	 */
	public Vector2 mul( Vector2 v ) {
		return mul( v.x, v.y, new Vector2() );
	}

	/**
	 * Sets out the to transformation of {x,y} by this matrix.
	 * @@return Vector2 transforms, and multiply a vector to matrix
	 */
	public Vector2 mul( float x, float y, Vector2 out ) {
		out.x = m00 * x + m01 * y;
		out.y = m10 * x + m11 * y;
		return out;
	}

	/**
	 * Multiplies this matrix by x.
	 */
	public void muli( Mat2x2 x ) {
		set(
			m00 * x.m00 + m01 * x.m10,
			m00 * x.m01 + m01 * x.m11,
			m10 * x.m00 + m11 * x.m10,
			m10 * x.m01 + m11 * x.m11 );
	}

	/**
	 * Sets out to the multiplication of this matrix and x.
	 * @return Mat2x2 result
	 */
	public Mat2x2 mul( Mat2x2 x, Mat2x2 out ) {
		out.m00 = m00 * x.m00 + m01 * x.m10;
		out.m01 = m00 * x.m01 + m01 * x.m11;
		out.m10 = m10 * x.m00 + m11 * x.m10;
		out.m11 = m10 * x.m01 + m11 * x.m11;
		return out;
	}

	/**
	 * Returns a new matrix that is the multiplication of this and x.
	 * @return Mat2x2 new result
	 */
	public Mat2x2 mul( Mat2x2 x ) {
		return mul( x, new Mat2x2() );
	}

}
