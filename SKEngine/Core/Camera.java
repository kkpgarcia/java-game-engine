package SKEngine.Core;

public class Camera extends GameObject {
    public static float x;
    public static float y;
    private GameObject target;
    
    public void follow(GameObject target) {
        this.target = target;
    }

    public void update() {
        if(target != null) {
            this.x = target.transform.position.x;
            this.y = target.transform.position.y;
        }

        System.out.println(x);
    }
}