package SKEngine.Collision;

import SKEngine.Core.Engine;
import SKEngine.Core.GameObject;

import java.util.ArrayList;

/**
 * <h2>Collision Engine</h2>
 * This is the engine that handles all the collisions in the
 * game.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */
public class CollisionEngine extends Engine {
    public CollisionEngine() {
        super();
    }
    /**
     * {@inheritDoc}
     * */
    public void updateCollisionEngine() {
        for(GameObject obj : objects) {
            for(GameObject other : objects) {
                if(!obj.equals(other)) {
                    if(obj.boundingbox.isOverlapping(other.boundingbox)) {
                        resolveCollisionStay(obj, other);
                        resolveCollisionStay(other, obj);
                    } else {
                        resolveCollisionExit(obj, other);
                        resolveCollisionExit(other, obj);
                    }
                }
            }
        }
    }

    private void resolveCollisionStay(GameObject main, GameObject other) {
        if(main.boundingbox.isCollided) {
            main.onCollisionStay(other);
        } else {
            main.onCollisionEnter(other);
            main.boundingbox.isCollided = true;
        }
    }

    private void resolveCollisionExit(GameObject main, GameObject other) {
        if(main.boundingbox.isCollided) {
            main.onCollisionExit(other);
            main.boundingbox.isCollided = false;
        }   
    }
}