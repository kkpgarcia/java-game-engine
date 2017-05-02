public class Rigidbody {
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Vector2 force = new Vector2();
	public float angularVelocity;
	public float torque;
	public float orient;
	public float mass, invMass, inertia, invInertia;
	public float staticFriction;
	public float dynamicFriction;
	public float restitution;
	public Shape shape;

	public Rigidbody( Shape shape, int x, int y )
	{
		this.shape = shape;

		position.set( x, y );
		velocity.set( 0, 0 );
		angularVelocity = 0;
		torque = 0;
		orient = ImpulseMath.random( -ImpulseMath.PI, ImpulseMath.PI );
		force.set( 0, 0 );
		staticFriction = 0.5f;
		dynamicFriction = 0.3f;
		restitution = 0.2f;

		shape.body = this;
		shape.initialize();
	}

	public void applyForce( Vector2 f ) {
		//force.addi( f );
		force = force.add(f);
		
	}

	public void applyImpulse( Vector2 impulse, Vector2 contactVector ) {
		//velocity.addsi( impulse, invMass );
		velocity = velocity.add(impulse.multiply(invMass));
		angularVelocity += invInertia * Vector2.cross( contactVector, impulse );
	}

	public void setStatic() {
		inertia = 0.0f;
		invInertia = 0.0f;
		mass = 0.0f;
		invMass = 0.0f;
	}

	public void setOrient( float radians ) {
		orient = radians;
		shape.setOrient( radians );
	}

}
