package SKEngine.Physics;

import SKEngine.Core.Vector2;
import SKEngine.Collision.Collisions;
import SKEngine.Calculations.MathEx;

/**
 * <h2>Manifold</h2>
 * A collision manifold is a small bit of information about the nature of the collision.
 * <p>
 * @see Rigidbody - SKEngine.Physics
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-01-05
 * */
public class Manifold {
	public Rigidbody A;
	public Rigidbody B;
	public float penetration;
	public final Vector2 normal = new Vector2();
	public final Vector2[] contacts = { new Vector2(), new Vector2() };
	public int contactCount;
	public float e;
	public float df;
	public float sf;

	/**
	 * Creates a manifold for the collision solution between two Rigidbodies.
	 * @param Rigidbody from
	 * @param Rigidbody against
	 * */
	public Manifold( Rigidbody a, Rigidbody b )
	{
		A = a;
		B = b;
	}

	/**
	 * Solves the collision between two bodies and map both to be passed to handlers.
	 * @param Rigidbody from
	 * @param Rigidbody against
	 * */
	public void solve()
	{
		int ia = A.shape.getType().ordinal();
		int ib = B.shape.getType().ordinal();

		Collisions.dispatch[ia][ib].handleCollision( this, A, B );
	}

	/**
	 * Initializes the manifolld and see if the bodies are at rest,
	 * and see if the bodies needs restitution
	 * */
	public void initialize()
	{
		// Calculate average restitution
		e = StrictMath.min( A.restitution, B.restitution );

		// Calculate static and dynamic friction
		sf = (float)StrictMath.sqrt( A.staticFriction * A.staticFriction + B.staticFriction * B.staticFriction);
		df = (float)StrictMath.sqrt( A.dynamicFriction * A.dynamicFriction + B.dynamicFriction * B.dynamicFriction);

		for (int i = 0; i < contactCount; ++i)
		{
			// Calculate radii from COM to contact
			Vector2 ra = contacts[i].subtract( A.position );
			Vector2 rb = contacts[i].subtract( B.position );
			Vector2 rv = B.velocity.add( Vector2.cross( B.angularVelocity, rb, new Vector2() ) ).subtracti( A.velocity ).subtracti( Vector2.cross( A.angularVelocity, ra, new Vector2() ) );

			// Determine if we should perform a resting collision or not
			// The idea is if the only thing moving this object is gravity,
			// then the collision should be performed without any restitution
			if (rv.lengthSq() < MathEx.RESTING)
			{
				e = 0.0f;
			}
		}
	}

	/**
	 * Applies the impulse to both rigid bodies.
	 * */
	public void applyImpulse()
	{
		// Early out and positional correct if both objects have infinite mass
		if (MathEx.tolerantEquals( A.invMass + B.invMass, 0 ))
		{
			infiniteMassCorrection();
			return;
		}

		for (int i = 0; i < contactCount; ++i)
		{
			// Calculate radii from COM to contact
			Vector2 ra = contacts[i].subtract( A.position );
			Vector2 rb = contacts[i].subtract( B.position );

			// Relative velocity
			Vector2 rv = B.velocity.add( Vector2.cross( B.angularVelocity, rb, new Vector2() ) ).subtracti( A.velocity ).subtracti( Vector2.cross( A.angularVelocity, ra, new Vector2() ) );

			// Relative velocity along the normal
			float contactVel = Vector2.dot( rv, normal );

			// Do not resolve if velocities are separating
			if (contactVel > 0)
			{
				return;
			}

			float raCrossN = Vector2.cross( ra, normal );
			float rbCrossN = Vector2.cross( rb, normal );
			float invMassSum = A.invMass + B.invMass + (raCrossN * raCrossN) * A.invInertia + (rbCrossN * rbCrossN) * B.invInertia;

			// Calculate impulse scalar
			float j = -(1.0f + e) * contactVel;
			j /= invMassSum;
			j /= contactCount;

			// Apply impulse
			Vector2 impulse = normal.multiply( j );
			A.applyImpulse( impulse.negate(), ra );
			B.applyImpulse( impulse, rb );

			// Friction impulse
			rv = B.velocity.add( Vector2.cross( B.angularVelocity, rb, new Vector2() ) ).subtracti( A.velocity ).subtracti( Vector2.cross( A.angularVelocity, ra, new Vector2() ) );

			Vector2 t = new Vector2( rv );
			t.addscalei( normal, -Vector2.dot( rv, normal ) );
			t.normalize();

			// j tangent magnitude
			float jt = -Vector2.dot( rv, t );
			jt /= invMassSum;
			jt /= contactCount;

			// Don't apply tiny friction impulses
			if (MathEx.tolerantEquals( jt, 0.0f ))
			{
				return;
			}

			// Coulumb's law
			Vector2 tangentImpulse;
			if (StrictMath.abs( jt ) < j * sf) {
				tangentImpulse = t.multiply( jt );
			} else{
				tangentImpulse = t.multiply( j ).multiplyi( -df );
			}

			// Apply friction impulse
			A.applyImpulse( tangentImpulse.negate(), ra );
			B.applyImpulse( tangentImpulse, rb );
		}
	}

	/**
	 * Corrects the position of the rigid bodies
	 * */
	public void positionalCorrection()
	{
		float correction = StrictMath.max( penetration - MathEx.PENETRATION_ALLOWANCE, 0.0f ) / (A.invMass + B.invMass) * MathEx.PENETRATION_CORRECTION;

		A.position.addscalei( normal, -A.invMass * correction );
		B.position.addscalei( normal, B.invMass * correction );
	}

	/**
	 * Corrects the position of the rigid bodies if it reaches infinite velocity
	 * */
	public void infiniteMassCorrection()
	{
		A.velocity.set( 0, 0 );
		B.velocity.set( 0, 0 );
	}

}
