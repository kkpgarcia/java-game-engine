package SKEngine.Collision;

import SKEngine.Calculations.MathEx;

/**
 * <h2>Circle</h2>
 * Circle structure for the Rigidbody, and Physics calculations.
 * <p>
 * @see Rigidbody - SKEngine.Physics
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-01-05
 * */
public class Circle extends Shape{

	/**
	 * Creates a circle by the given radius
	 * @param float radius
	 * */
	public Circle( float r )
	{
		radius = r;
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public Shape clone()
	{
		return new Circle( radius );
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void initialize()
	{
		computeMass( 1.0f );
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void computeMass( float density )
	{
		body.mass = MathEx.PI * radius * radius * density;
		body.invMass = (body.mass != 0.0f) ? 1.0f / body.mass : 0.0f;
		body.inertia = body.mass * radius * radius;
		body.invInertia = (body.inertia != 0.0f) ? 1.0f / body.inertia : 0.0f;
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void setOrient( float radians )
	{
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public Type getType()
	{
		return Type.Circle;
	}

}
