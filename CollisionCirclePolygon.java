public class CollisionCirclePolygon implements CollisionCallback {

	public static final CollisionCirclePolygon instance = new CollisionCirclePolygon();

	@Override
	public void handleCollision( Manifold m, Rigidbody a, Rigidbody b )
	{
		Circle A = (Circle)a.shape;
		Polygon B = (Polygon)b.shape;

		m.contactCount = 0;

		// Transform circle center to Polygon model space
		// Vector2 center = a->position;
		// center = B->u.Transpose( ) * (center - b->position);
		Vector2 center = B.u.transpose().muli( a.position.subtract( b.position ) );

		// Find edge with minimum penetration
		// Exact concept as using support points in Polygon vs Polygon
		float separation = -Float.MAX_VALUE;
		int faceNormal = 0;
		for (int i = 0; i < B.vertexCount; ++i)
		{
			// real s = Dot( B->m_normals[i], center - B->m_vertices[i] );
			float s = Vector2.dot( B.normals[i], center.subtract( B.vertices[i] ) );

			if (s > A.radius)
			{
				return;
			}

			if (s > separation)
			{
				separation = s;
				faceNormal = i;
			}
		}

		// Grab face's vertices
		Vector2 v1 = B.vertices[faceNormal];
		int i2 = faceNormal + 1 < B.vertexCount ? faceNormal + 1 : 0;
		Vector2 v2 = B.vertices[i2];

		// Check to see if center is within polygon
		if (separation < MathEx.EPSILON)
		{
			// m->contact_count = 1;
			// m->normal = -(B->u * B->m_normals[faceNormal]);
			// m->contacts[0] = m->normal * A->radius + a->position;
			// m->penetration = A->radius;

			m.contactCount = 1;
			B.u.mul( B.normals[faceNormal], m.normal ).negatei();
			m.contacts[0].set( m.normal ).multiplyi( A.radius ).addi( a.position );
			m.penetration = A.radius;
			return;
		}

		// Determine which voronoi region of the edge center of circle lies within
		// real dot1 = Dot( center - v1, v2 - v1 );
		// real dot2 = Dot( center - v2, v1 - v2 );
		// m->penetration = A->radius - separation;
		float dot1 = Vector2.dot( center.subtract( v1 ), v2.subtract( v1 ) );
		float dot2 = Vector2.dot( center.subtract( v2 ), v1.subtract( v2 ) );
		m.penetration = A.radius - separation;

		// Closest to v1
		if (dot1 <= 0.0f)
		{
			if (Vector2.distanceSq( center, v1 ) > A.radius * A.radius)
			{
				return;
			}

			// m->contact_count = 1;
			// Vector2 n = v1 - center;
			// n = B->u * n;
			// n.Normalize( );
			// m->normal = n;
			// v1 = B->u * v1 + b->position;
			// m->contacts[0] = v1;

			m.contactCount = 1;
			B.u.muli( m.normal.set( v1 ).subtracti( center ) ).normalize();
			B.u.mul( v1, m.contacts[0] ).addi( b.position );
		}

		// Closest to v2
		else if (dot2 <= 0.0f)
		{
			if (Vector2.distanceSq( center, v2 ) > A.radius * A.radius)
			{
				return;
			}

			// m->contact_count = 1;
			// Vector2 n = v2 - center;
			// v2 = B->u * v2 + b->position;
			// m->contacts[0] = v2;
			// n = B->u * n;
			// n.Normalize( );
			// m->normal = n;

			m.contactCount = 1;
			B.u.muli( m.normal.set( v2 ).subtracti( center ) ).normalize();
			B.u.mul( v2, m.contacts[0] ).addi( b.position );
		}

		// Closest to face
		else
		{
			Vector2 n = B.normals[faceNormal];

			if (Vector2.dot( center.subtract( v1 ), n ) > A.radius)
			{
				return;
			}

			// n = B->u * n;
			// m->normal = -n;
			// m->contacts[0] = m->normal * A->radius + a->position;
			// m->contact_count = 1;

			m.contactCount = 1;
			B.u.mul( n, m.normal ).negatei();
			m.contacts[0].set( a.position ).addscalei( m.normal, A.radius );
		}
	}
}
