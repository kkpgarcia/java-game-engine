import java.util.ArrayList;

public class CollisionEngine extends Engine {
    public CollisionEngine() {
        super();
    }

    public void updateCollisionEngine() {
        for(GameObject obj : objects) {
            for(GameObject other : objects) {
                if(!obj.equals(other)) {
                    if(obj.boundingbox.isOverlapping(other.boundingbox)) {
                        if(!obj.boundingbox.isCollided) {
                            resolveCollisionEnter(obj, other);
                            resolveCollisionEnter(other, obj);
                        } else {
                            resolveCollisionStay(obj, other);
                            resolveCollisionStay(other, obj);
                        }
                    } else {
                        if(obj.boundingbox.isCollided) {
                            resolveCollisionExit(obj, other);
                            resolveCollisionExit(other, obj);
                        }
                    }
                }
            }
        }
    }

    private void resolveCollisionEnter(GameObject main, GameObject other) {
        //if(!main.isTrigger)
            main.onCollisionEnter(other);
        //else
        //    main.onTriggerEnter(other);

        main.boundingbox.isCollided = true;
    }

    private void resolveCollisionStay(GameObject main, GameObject other) {
        //if(!main.isTrigger)
            main.onCollisionStay(other);
        //else
       //     main.onTriggerStay(other);
    }

    private void resolveCollisionExit(GameObject main, GameObject other) {
        //if(!main.isTrigger)
            main.onCollisionExit(other);
        //else
        //   main.onTriggerExit(other);

        main.boundingbox.isCollided = false;
    }
}