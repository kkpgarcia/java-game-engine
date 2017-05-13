package SKEngine.Collision;

import SKEngine.Core.Vector2;
import SKEngine.Core.Mat2x2;
import SKEngine.Physics.Rigidbody;
import SKEngine.Physics.Manifold;
import SKEngine.Calculations.MathEx;

public class CollisionPolygonPolygon implements CollisionCallback {

	public static final CollisionPolygonPolygon instance = new CollisionPolygonPolygon();

	@Override
	public void handleCollision( Manifold m, Rigidbody a, Rigidbody b ) {
		Polygon A = (Polygon)a.shape;
		Polygon B = (Polygon)b.shape;
		m.contactCount = 0;

		// Check for a separating axis with A's face planes
		int[] faceA = { 0 };
		float penetrationA = findAxisLeastPenetration( faceA, A, B );
		if (penetrationA >= 0.0f)
		{
			return;
		}

		// Check for a separating axis with B's face planes
		int[] faceB = { 0 };
		float penetrationB = findAxisLeastPenetration( faceB, B, A );
		if (penetrationB >= 0.0f)
		{
			return;
		}

		int referenceIndex;
		boolean flip; // Always point from a to b

		Polygon RefPoly; // Reference
		Polygon IncPoly; // Incident

		// Determine which shape contains reference face
		if (MathEx.gt( penetrationA, penetrationB ))
		{
			RefPoly = A;
			IncPoly = B;
			referenceIndex = faceA[0];
			flip = false;
		}
		else
		{
			RefPoly = B;
			IncPoly = A;
			referenceIndex = faceB[0];
			flip = true;
		}

		// World space incident face
		Vector2[] incidentFace = Vector2.arrayOf( 2 );

		findIncidentFace( incidentFace, RefPoly, IncPoly, referenceIndex );
		// Setup reference face vertices
		Vector2 v1 = RefPoly.vertices[referenceIndex];
		referenceIndex = referenceIndex + 1 == RefPoly.vertexCount ? 0 : referenceIndex + 1;
		Vector2 v2 = RefPoly.vertices[referenceIndex];

		// Transform vertices to world space
		v1 = RefPoly.u.mul( v1 ).addi( RefPoly.body.position );
		v2 = RefPoly.u.mul( v2 ).addi( RefPoly.body.position );

		// Calculate reference face side normal in world space
		Vector2 sidePlaneNormal = v2.subtract( v1 );
		sidePlaneNormal.normalize();

		Vector2 refFaceNormal = new Vector2( sidePlaneNormal.y, -sidePlaneNormal.x );

		float refC = Vector2.dot( refFaceNormal, v1 );
		float negSide = -Vector2.dot( sidePlaneNormal, v1 );
		float posSide = Vector2.dot( sidePlaneNormal, v2 );

		// Clip incident face to reference face side planes
		if (clip( sidePlaneNormal.negate(), negSide, incidentFace ) < 2)
		{
			return; // Due to floating point error, possible to not have required
						// points
		}

		if (clip( sidePlaneNormal, posSide, incidentFace ) < 2)
		{
			return; // Due to floating point error, possible to not have required
						// points
		}

		// Flip
		m.normal.set( refFaceNormal );
		if (flip)
		{
			m.normal.negatei();
		}

		// Keep points behind reference face
		int cp = 0; // clipped points behind reference face
		float separation = Vector2.dot( refFaceNormal, incidentFace[0] ) - refC;
		if (separation <= 0.0f)
		{
			m.contacts[cp].set( incidentFace[0] );
			m.penetration = -separation;
			++cp;
		}
		else
		{
			m.penetration = 0;
		}

		separation = Vector2.dot( refFaceNormal, incidentFace[1] ) - refC;

		if (separation <= 0.0f)
		{
			m.contacts[cp].set( incidentFace[1] );

			m.penetration += -separation;
			++cp;

			// Average penetration
			m.penetration /= cp;
		}

		m.contactCount = cp;
	}

	public float findAxisLeastPenetration( int[] faceIndex, Polygon A, Polygon B )
	{
		float bestDistance = -Float.MAX_VALUE;
		int bestIndex = 0;

		for (int i = 0; i < A.vertexCount; ++i)
		{
			Vector2 nw = A.u.mul( A.normals[i] );
			Mat2x2 buT = B.u.transpose();
			Vector2 n = buT.mul( nw );
			Vector2 s = B.getSupport( n.negate() );
			Vector2 v = buT.muli( A.u.mul( A.vertices[i] ).addi( A.body.position ).subtracti( B.body.position ) );

			// Compute penetration distance (in B's model space)
			float d = Vector2.dot( n, s.subtract( v ) );

			// Store greatest distance
			if (d > bestDistance)
			{
				bestDistance = d;
				bestIndex = i;
			}
		}

		faceIndex[0] = bestIndex;
		return bestDistance;
	}

	public void findIncidentFace( Vector2[] v, Polygon RefPoly, Polygon IncPoly, int referenceIndex )
	{
		Vector2 referenceNormal = RefPoly.normals[referenceIndex];

		// Calculate normal in incident's frame of reference 
		referenceNormal = RefPoly.u.mul( referenceNormal ); 
		referenceNormal = IncPoly.u.transpose().mul( referenceNormal );

		// Find most anti-normal face on incident polygon
		int incidentFace = 0;
		float minDot = Float.MAX_VALUE;
		for (int i = 0; i < IncPoly.vertexCount; ++i) { 
			float dot = Vector2.dot( referenceNormal, IncPoly.normals[i] );

			if (dot < minDot)
			{
				minDot = dot;
				incidentFace = i;
			}
		}

		// Assign face vertices for incidentFace 
		v[0] = IncPoly.u.mul( IncPoly.vertices[incidentFace] ).addi( IncPoly.body.position );
		incidentFace = incidentFace + 1 >= (int)IncPoly.vertexCount ? 0 : incidentFace + 1;
		v[1] = IncPoly.u.mul( IncPoly.vertices[incidentFace] ).addi( IncPoly.body.position );
	}

	public int clip( Vector2 n, float c, Vector2[] face )
	{
		int sp = 0;
		Vector2[] out = {
			new Vector2( face[0] ),
			new Vector2( face[1] )
		};

		// Retrieve distances from each endpoint to the line 
		float d1 = Vector2.dot( n, face[0] ) - c;
		float d2 = Vector2.dot( n, face[1] ) - c;

		// If negative (behind plane) clip
		if (d1 <= 0.0f) out[sp++].set( face[0] );
		if (d2 <= 0.0f) out[sp++].set( face[1] );

		// If the points are on different sides of the plane
		if (d1 * d2 < 0.0f) {
			// Push intersection point 
			float alpha = d1 / (d1 - d2);

			out[sp++].set( face[1] ).subtracti( face[0] ).multiplyi( alpha ).addi( face[0] );
		}

		// Assign our new converted values
		face[0] = out[0];
		face[1] = out[1];

		// assert( sp != 3 );

		return sp;
	}

}
