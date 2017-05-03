import java.util.ArrayList;

public class RenderEngine extends Engine {
    public Screen mainScreen;

    public RenderEngine(Screen screen) {
        super();
        mainScreen = screen;
    }

    /*public void addRenderers(GameObject renderer) {
        mainScreen.addDrawingComponents(renderer);
    }*/

    public void addObject(GameObject obj) {
        super.addObject(obj);
        mainScreen.addDrawingComponents(obj);
    }

    public void updateRenderEngine() {
        mainScreen.update();
    }
}