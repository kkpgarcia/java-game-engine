import java.util.ArrayList;

public class PhysicsEngine extends Engine {
    private ArrayList<GameObject> objects;

    public PhysicsEngine() {
        objects = new ArrayList<>();
    }

    public void addObjects(GameObject obj) {
        if(objects == null) {
            objects = new ArrayList<>();
        }

        objects.add(obj);
    }

    public void init() {
        super.init();
    }

    public void updatePhysicsEngine() {
        for(GameObject obj : objects) {
            if(obj.rigidbody != null)
                obj.rigidbody.update();
        }
    }
}