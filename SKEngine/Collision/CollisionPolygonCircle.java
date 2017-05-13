package SKEngine.Collision;

import SKEngine.Physics.Rigidbody;
import SKEngine.Physics.Manifold;

/**
 * <h2>Collision Polygon vs. Circle</h2>
 * Collision handler between polygon and a circle.
 * <p>
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-01-05
 * */
public class CollisionPolygonCircle implements CollisionCallback {

	public static final CollisionPolygonCircle instance = new CollisionPolygonCircle();
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void handleCollision(Manifold m, Rigidbody a, Rigidbody b) {
		CollisionCirclePolygon.instance.handleCollision(m, b, a); 
		
		if ( m.contactCount > 0 ) {
			m.normal.negatei();
		}
	}
}
