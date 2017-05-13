package SKEngine.Collision;

import SKEngine.Physics.Manifold;
import SKEngine.Physics.Rigidbody;

/**
 * <h2>Collision Callback</h2>
 * Callback interface for the different handlers
 * <p>
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-01-05
 * */
public interface CollisionCallback {
	/**
	 * Handles the collision of the rigid body and applies the appropriate response.
	 * @param Manifold collision response based on physics manipulation
	 * @param Rigidbody collision from
	 * @param Rigidbody collision against
	 * */
	public void handleCollision( Manifold m, Rigidbody a, Rigidbody b );
}
