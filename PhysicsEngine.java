import java.util.ArrayList;

public class PhysicsEngine extends Engine {
    public float dt;
    public int iterations;
    //public ArrayList<Rigidbody> bodies = new ArrayList<Rigidbody>();
    public ArrayList<Manifold> contacts = new ArrayList<Manifold>();

    public PhysicsEngine(float dt, int iterations) {
		super();
        this.dt = dt;
        this.iterations = iterations;
    }

    /*public void addRigidbody(GameObject object) {
        bodies.add(object.rigidbody);
    }*/

    public void updatePhysicsEngine() {
        contacts.clear();

		for (int i = 0; i < objects.length; ++i)
		{
			Rigidbody A = objects[i].rigidbody;

			if(A == null)
				return;

			for (int j = i + 1; j < objects.length; ++j)
			{
				Rigidbody B = objects[j].rigidbody;

				if(B == null)
					return;

				if (A.invMass == 0 && B.invMass == 0)
				{
					continue;
				}

				Manifold m = new Manifold( A, B );
				m.solve();

				if (m.contactCount > 0)
				{
					contacts.add( m );
				}
			}
		}

		// Integrate forces
		for (int i = 0; i < objects.length; ++i)
		{
			integrateForces( objects[i].rigidbody, dt );
		}

		// Initialize collision
		for (int i = 0; i < contacts.size(); ++i)
		{
			contacts.get(i).initialize();
		}

		// Solve collisions
		for (int j = 0; j < iterations; ++j)
		{
			for (int i = 0; i < contacts.size(); ++i)
			{
				contacts.get(i).applyImpulse();
			}
		}

		// Integrate velocities
		for (int i = 0; i < objects.length; ++i)
		{
			integrateVelocity( objects[i].rigidbody, dt );
		}

		// Correct positions
		for (int i = 0; i < contacts.size(); ++i)
		{
			contacts.get(i).positionalCorrection();
		}

		// Clear all forces
		for (int i = 0; i < objects.length; ++i)
		{
			if(objects[i].rigidbody == null)
				return;

			Rigidbody b = objects[i].rigidbody;
			b.force.set( 0, 0 );
			b.torque = 0;
		}
    }

    public void clear()
	{
		contacts.clear();
		//objects.clear();
	}

	// Acceleration
	// F = mA
	// => A = F * 1/m

	// Explicit Euler
	// x += v * dt
	// v += (1/m * F) * dt

	// Semi-Implicit (Symplectic) Euler
	// v += (1/m * F) * dt
	// x += v * dt
	public void integrateForces( Rigidbody b, float dt )
	{
		if(b == null) {
			return;
		}

		if (b.invMass == 0.0f) {
			return;
		}

		float dts = dt * 0.5f;
		b.velocity = b.velocity.add(b.force.multiply(b.invMass * dts ));
		b.velocity = b.velocity.add(MathEx.GRAVITY.multiply(dts));
		b.angularVelocity += b.torque * b.invInertia * dts;
	}

	public void integrateVelocity( Rigidbody b, float dt ) {
		if(b == null) {
			return;
		}

        if (b.invMass == 0.0f)
		{
			return;
		}

		b.position = b.position.add( b.velocity.multiply( dt ) );
		b.orient += b.angularVelocity * dt;
		b.setOrient( b.orient );

		integrateForces( b, dt );
	}
}