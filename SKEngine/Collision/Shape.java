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

	public abstract Shape clone();

	public abstract void initialize();

	public abstract void computeMass( float density );

	public abstract void setOrient( float radians );

	public abstract Type getType();

}