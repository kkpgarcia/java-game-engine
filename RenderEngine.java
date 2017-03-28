import java.util.ArrayList;

public class RenderEngine extends Engine {
    public Screen mainScreen;

    public RenderEngine(Screen screen) {
        mainScreen = screen;
    }

    public void addRenderers(GameObject renderer) {
        mainScreen.addDrawingComponents(renderer);
    }

    public void updateRenderEngine() {
        mainScreen.update();
    }
}