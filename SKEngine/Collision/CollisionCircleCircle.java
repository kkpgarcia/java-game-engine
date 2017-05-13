package SKEngine.Collision;

import SKEngine.Core.Vector2;
import SKEngine.Physics.Manifold;
import SKEngine.Physics.Rigidbody;

/**
 * <h2>Collision Circle vs. Circle</h2>
 * Collision handler between circle and another circle.
 * <p>
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-01-05
 * */
public class CollisionCircleCircle implements CollisionCallback {
	public static final CollisionCircleCircle instance = new CollisionCircleCircle();

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void handleCollision( Manifold m, Rigidbody a, Rigidbody b ) {
		Circle A = (Circle)a.shape;
		Circle B = (Circle)b.shape;

		// Calculate translational vector, which is normal
		Vector2 normal = b.position.subtract( a.position );
		float dist_sqr = normal.lengthSq();
		float radius = A.radius + B.radius;

		// Not in contact
		if (dist_sqr >= radius * radius) {
			m.contactCount = 0;
			return;
		}

		float distance = (float)StrictMath.sqrt( dist_sqr );

		m.contactCount = 1;

		if (distance == 0.0f) {
			m.penetration = A.radius;
			m.normal.set( 1.0f, 0.0f );
			m.contacts[0].set( a.position );
		} else {
			m.penetration = radius - distance;
			m.normal.set( normal ).dividei( distance );
			m.contacts[0].set( m.normal ).multiplyi( A.radius ).addi( a.position );
		}
	}

}
