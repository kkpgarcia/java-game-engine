package SKEngine.Physics;

import SKEngine.Core.Vector2;
import SKEngine.Collision.Shape;

/**
 * <h2>Rigidbody</h2>
 * Physics component of the game object. This implements the physics
 * calculation, movements, and application.
 * <p>
 * @see PhysicsEngine - SKEngine.Physics
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-01-05
 * */
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

	/**
	 * Constructs and initializes this Rigidbody.
	 * @param Shape shape of the rigidbody
	 * @param int x position
	 * @param int y position
	 * */
	public Rigidbody( Shape shape, int x, int y ) {
		this.shape = shape;

		position.set( x, y );
		velocity.set( 0, 0 );
		angularVelocity = 0;
		torque = 0;
		orient = 0;
		force.set( 0, 0 );
		staticFriction = 0.5f;
		dynamicFriction = 0.3f;
		restitution = 0.2f;

		shape.body = this;
		shape.initialize();
	}
	/**
	 * Applies force to this rigidbody according to the manifold
	 * @param Vector2 force direction
	 * */
	public void applyForce( Vector2 f ) {
		force.addi(f);
	}
	/**
	 * Applies impulse to this rigidbody according to the manifold
	 * @param Vector2 impulse direction
	 * @param Vector2 contact vector
	 * */
	public void applyImpulse( Vector2 impulse, Vector2 contactVector ) {
		velocity.addscalei(impulse, invMass);
		angularVelocity += invInertia * Vector2.cross( contactVector, impulse );
	}

	/**
	 * Sets this rigidbody to static.
	 * */
	public void setStatic() {
		inertia = 0.0f;
		invInertia = 0.0f;
		mass = 0.0f;
		invMass = 0.0f;
		setOrient(0);
	}

	/**
	 * Clears all forces of this rigid body
	 * */
	public void clearForces() {
		force.set( 0, 0 );
		torque = 0;
	}

	/**
	 * Clears all velocity and forces of this rigid body
	 * */
	public void clearVelocity() {
		clearForces();
		velocity.set(0,0);
	}

	/**
	 * Sets the orientation of this rigidbody
	 * */
	public void setOrient( float radians ) {
		orient = radians;
		shape.setOrient( radians );
	}

}
