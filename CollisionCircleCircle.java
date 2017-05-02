public class CollisionCircleCircle implements CollisionCallback {
	public static final CollisionCircleCircle instance = new CollisionCircleCircle();

	@Override
	public void handleCollision( Manifold m, Rigidbody a, Rigidbody b ) {
		Circle A = (Circle)a.shape;
		Circle B = (Circle)b.shape;

		// Calculate translational vector, which is normal
		Vector2 normal = b.position.subtract( a.position );
		float dist_sqr = normal.lengthSq();
		float radius = A.radius + B.radius;

		// If not in contact
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
			m.normal.set( normal.divide(distance) );
			//m.normal.divide( distance );
			m.contacts[0].set( m.normal.multiply( A.radius ).add(a.position) );
			//m.contacts[0] = m.contacts[0].multiply( A.radius ).add(a.position);
		}
	}

}
