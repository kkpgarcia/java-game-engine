package SKEngine.Core;

/**
 * <h2>Camera</h2>
 * Camera representation for the game.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-13-05
 * */
public class Camera extends GameObject {
    public static float x;
    public static float y;
    private GameObject target;
    
    /**
     * Mount the camera to a certain object.
     * @param GameObject target
     * */
    public void follow(GameObject target) {
        this.target = target;
        super.registerObject();
    }

    /**
     * {@inheritDocs}
     * */
    public void update() {
        if(target != null) {
            this.x = target.transform.position.x;
            this.y = target.transform.position.y;
        }
    }
}