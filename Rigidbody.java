public class Rigidbody {
    private GameObject gameObject;

    public Vector2 position;
    public Vector2 velocity;
    public Vector2 force;
    public float angularVelocity;
    public float torque;
    public float orient;
    public float mass;
    public float invMass;
    public float inertia;
    public float invInertia;
    public float staticFriction;
    public float dynamicFriction;
    public float restitution;

    public boolean isGrounded = false;

    public Rigidbody(GameObject gameObject) {
        this.position = gameObject.transform.position;
        this.velocity = new Vector2();
        this.angularVelocity = 0;
        this.torque = 0;
        this.orient = 0;
        //this.force.set(0,0);
        this.staticFriction = 0.5f;
        this.dynamicFriction = 0.3f;
        this.restitution = 0.2f;
    }
    /*
    public void applyForce(Vector2 f) {
        this.force = force.add(f);
    }

    public void applyImpulse(Vector2 impulse, Vector2 contactVector) {
        velocity = velocity.add(impulse, invMass);
        angularVelocity += invInertia * Vector2.cross(contactVector, impulse);
    }

    public void setRest() {
        this.inertia = 0.0f;
        this.invInertia = 0.0f;
        this.mass = 0.0f;
        this.invMass = 0.0f;
    }

    public void setOrient(float radians) {
        orient = radians;
        shape.setOrient(radians);
    }

    public void computeMass(float density) {
        Vector2 centroid = new Vector2();
        float area = 0.0f;
        float I = 0.0f;
        final float k_inv3 = 1.0f/3.0f;

        
    }*/

    public void update() {
        //Velocity BUGGY
        /*acceleration.x = gameObject.normalDirection.x * 10;
        acceleration.y = gameObject.normalDirection.y * 10;
        velocity.x = velocity.x + acceleration.x * (float)Math.abs(Time.deltaTime);
        velocity.y = velocity.y + acceleration.y * (float)Math.abs(Time.deltaTime);
        gameObject.transform.position.x = gameObject.transform.position.x + velocity.x * (float)Time.deltaTime;
        gameObject.transform.position.y = gameObject.transform.position.y + velocity.y * (float)Time.deltaTime;*/
        //position.y += 50f * Time.deltaTime;
    }
}