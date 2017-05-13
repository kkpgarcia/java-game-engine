package SKEngine.Collision;

import SKEngine.Physics.Manifold;
import SKEngine.Physics.Rigidbody;

public interface CollisionCallback {
	public void handleCollision( Manifold m, Rigidbody a, Rigidbody b );
}
