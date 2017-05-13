package SKEngine.Collision;

import SKEngine.Core.Mat2x2;
import SKEngine.Physics.Rigidbody;

public abstract class Shape {

	public enum Type {
		Circle, Poly, Count
	}

	public Rigidbody body;
	public float radius;
	public final Mat2x2 u = new Mat2x2();

	public Shape()
	{
	}

	/**
	 * Clones this shape
	 * @return Shape cloned shape
	 * */
	public abstract Shape clone();

	/**
	 * Computes the mass of this shape
	 * */
	public abstract void initialize();

	/**
	 * Computes the mass of this shape
	 * @param float density
	 * */
	public abstract void computeMass( float density );

	/**
	 * Sets the orientation of this shape
	 * @param float radians
	 * */
	public abstract void setOrient( float radians );

	/**
	 * Gets the type of this shape
	 * @return Type shape
	 * */
	public abstract Type getType();

}
