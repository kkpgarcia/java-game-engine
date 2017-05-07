import java.util.ArrayList;

public class RenderEngine extends Engine {
    public Screen mainScreen;

    public RenderEngine(Screen screen) {
        super();
        mainScreen = screen;
    }

    public void addObject(GameObject obj) {
        super.addObject(obj);
        mainScreen.addDrawingComponents(obj);
    }

    public void removeObject(GameObject obj) {
        super.removeObject(obj);
        mainScreen.removeDrawingComponents(obj);
    }

    public void updateRenderEngine() {
        mainScreen.update();
    }
}