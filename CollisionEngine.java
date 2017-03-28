import java.util.ArrayList;

public class CollisionEngine extends Engine {
    public boolean isRunning = false;

    private ArrayList<GameObject> objects;

    private boolean firstCollision = false;
    private boolean collisionStay = false;

    public CollisionEngine() {
        objects = new ArrayList<>();
    }
    public void addObjects(GameObject object) {
        objects.add(object);
    }

    public void init() {
        super.init();
    }

    public void updateCollisionEngine() {
        for(GameObject obj : objects) {
            for(GameObject other : objects) {
                if(!obj.equals(other)) {
                    if(obj.boundingBox.isOverlapping(other.boundingBox)) {
                        obj.onCollision(other);
                        other.onCollision(obj);

                        if(!obj.aabb.isOnCollision) {
                            resolveCollisionEnter(obj, other);
                            resolveCollisionEnter(other, obj);
                            return;
                        } else {
                            resolveCollisionStay(obj,other);
                            resolveCollisionStay(other, obj);
                        }
                    } else {
                        if(obj.aabb.isOnCollision) {
                            resolveCollisionExit(obj, other);
                            resolveCollisionExit(other, obj);
                        }
                    }
                }


                /*if(!obj.equals(other)) {
                    if(obj.aabb.isColliding(other.aabb)) {
                        obj.onCollision(other);
                        other.onCollision(obj);

                        if(!obj.aabb.isOnCollision) {
                            resolveCollisionEnter(obj, other);
                            resolveCollisionEnter(other, obj);
                            return;
                        } else {
                            resolveCollisionStay(obj,other);
                            resolveCollisionStay(other, obj);
                        }
                    } else {
                        if(obj.aabb.isOnCollision) {
                            resolveCollisionExit(obj, other);
                            resolveCollisionExit(other, obj);
                        }
                    }
                }*/
            }
        }
    }

    private void resolveCollisionEnter(GameObject main, GameObject other) {
        if(!main.isTrigger)
            main.onCollisionEnter(other);
        else
            main.onTriggerEnter(other);

        main.aabb.isOnCollision = true;
    }

    private void resolveCollisionStay(GameObject main, GameObject other) {
        if(!main.isTrigger)
            main.onCollisionStay(other);
        else
            main.onTriggerStay(other);
    }

    private void resolveCollisionExit(GameObject main, GameObject other) {
        if(!main.isTrigger)
            main.onCollisionExit(other);
        else
            main.onTriggerExit(other);

        main.aabb.isOnCollision = false;
    }
}