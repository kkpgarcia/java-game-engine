package SKEngine.Collision;

/**
 * <h2>Collisions</h2>
 * Simple collision matrix.
 * <p>
 * @see CollisionCircleCircle - SKEngine.Collision
 * @see CollisionCirclePolygon - SKEngine.Collision
 * @see CollisionPolygonCircle - SKEngine.Collision
 * @see CollisionPolygonPolygon - SKEngine.Collision
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-01-05
 * */
public class Collisions {
	/**
	 * 	Maps the most appropriate handlers to handle the collisions
	 * */
	public static CollisionCallback[][] dispatch =
	{
		{ CollisionCircleCircle.instance, CollisionCirclePolygon.instance },
		{ CollisionPolygonCircle.instance, CollisionPolygonPolygon.instance }
	};
}
