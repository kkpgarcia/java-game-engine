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

	public Manifold( Rigidbody a, Rigidbody b ) {
		A = a;
		B = b;
	}

	public void solve() {
		int ia = A.shape.getType().ordinal();
		int ib = B.shape.getType().ordinal();

		Collisions.dispatch[ia][ib].handleCollision( this, A, B );
	}

	public void initialize() {
		// Calculate average restitution 
		e = Math.min( A.restitution, B.restitution );

		// Calculate static and dynamic friction 
		sf = (float)Math.sqrt( A.staticFriction * A.staticFriction + B.staticFriction * B.staticFriction);
		df = (float)Math.sqrt( A.dynamicFriction * A.dynamicFriction + B.dynamicFriction * B.dynamicFriction);

		for (int i = 0; i < contactCount; ++i) {
			// Calculate radii from COM to contact 
			Vector2 ra = contacts[i].subtract( A.position );
			Vector2 rb = contacts[i].subtract( B.position ); 
			Vector2 rv = B.velocity.add( Vector2.cross( B.angularVelocity, rb, new Vector2() ) ).subtract( A.velocity ).subtract( Vector2.cross( A.angularVelocity, ra, new Vector2() ));

			// Determine if we should perform a resting collision or not
			// The idea is if the only thing moving this object is gravity, 
			if (rv.lengthSq() < MathEx.RESTING)
			{
				e = 0.0f;
			}
		}
	}

	public void applyImpulse()
	{
		// Early out and positional correct if both objects have infinite mass 
		if (MathEx.tolerantEquals( A.invMass + B.invMass, 0 )) {
			infiniteMassCorrection();
			return;
		}

		for (int i = 0; i < contactCount; ++i) {
			// Calculate radii from COM to contact 
			Vector2 ra = contacts[i].subtract( A.position );
			Vector2 rb = contacts[i].subtract( B.position );

			// Relative velocity 
			Vector2 rv = B.velocity.add( Vector2.cross( B.angularVelocity, rb, new Vector2() ) ).subtract( A.velocity ).subtract( Vector2.cross( A.angularVelocity, ra, new Vector2() ) );

			// Relative velocity along the normal 
			float contactVel = Vector2.dot( rv, normal );

			// Do not resolve if velocities are separating
			if (contactVel > 0) {
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
			rv = B.velocity.add( Vector2.cross( B.angularVelocity, rb, new Vector2() ) ).subtract( A.velocity ).subtract( Vector2.cross( A.angularVelocity, ra, new Vector2() ) );

			Vector2 t = new Vector2( rv );
			//t.addsi( normal, -Vector2.dot( rv, normal ) );
			t = t.add(normal.multiply(-Vector2.dot(rv,normal)));
			t.normalize(); 
			float jt = -Vector2.dot( rv, t );
			jt /= invMassSum;
			jt /= contactCount;

			// Don't apply tiny friction impulses
			if (MathEx.tolerantEquals( jt, 0.0f )) {
				return;
			}

			// Coulumb's law
			Vector2 tangentImpulse;
			if (Math.abs( jt ) < j * sf) {
				tangentImpulse = t.multiply( jt );
			} else {
				tangentImpulse = t.multiply( j ).multiply( -df );
			}

			// Apply friction impulse 
			A.applyImpulse( tangentImpulse.negate(), ra );
			B.applyImpulse( tangentImpulse, rb );
		}
	}

	public void positionalCorrection() {
		float correction = Math.max( penetration - MathEx.PENETRATION_ALLOWANCE, 0.0f ) / (A.invMass + B.invMass) * MathEx.PENETRATION_CORRECTION;

		A.position = A.position.add( normal.multiply( -A.invMass * correction ));
		B.position = B.position.add( normal.multiply( B.invMass * correction ));
	}

	public void infiniteMassCorrection() {
		A.velocity.set( 0, 0 );
		B.velocity.set( 0, 0 );
	}

}
