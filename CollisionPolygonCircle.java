public class CollisionPolygonCircle implements CollisionCallback {

	public static final CollisionPolygonCircle instance = new CollisionPolygonCircle();
	
	@Override
	public void handleCollision(Manifold m, Rigidbody a, Rigidbody b) {
		CollisionCirclePolygon.instance.handleCollision(m, b, a); 
		
		if ( m.contactCount > 0 ) {
			m.normal.negate();
		}
	}
}
