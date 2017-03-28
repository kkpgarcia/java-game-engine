public class Rigidbody {
    private GameObject gameObject;

    public Vector2 velocity;
    public Vector2 acceleration;
    public float gravity;
    public float drag;
    public float mass;
    public boolean applyGravity;

    public boolean isGrounded = false;

    public Rigidbody(GameObject gameObject) {
        this.velocity = new Vector2();
        this.acceleration = new Vector2(0,2f);
        this.gravity = 1;
        this.drag = 0;
        this.mass = 10;
        this.gameObject = gameObject;
    }

    public void update() {
        /*if(isGrounded) {
            acceleration.x = 0;
            acceleration.y = 0;
            velocity.x = 0;
            velocity.y = 0;
        }*/

        //Vector2 prevPos = gameObject.transform.prevPosition;
        //velocity = Vector2.delta(prevPos, gameObject.transform.position).multiply((float)Time.deltaTime);

        
        //velocity = velocity.add(new Vector2(acceleration.x * (float)Time.deltaTime, acceleration.y * (float)Time.deltaTime));
        //gameObject.transform.position = gameObject.transform.position.subtract(velocity);
        //System.out.println(velocity.toString());
    }
}