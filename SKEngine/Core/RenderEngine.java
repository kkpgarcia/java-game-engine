package SKEngine.Core;

/**
 * <h2>Render Engine</h2>
 * This engine is focused only on the animation, and drawing part
 * of the game.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */
public class RenderEngine extends Engine {
    public Screen mainScreen;

    /**
     * Constructs this render engine, and initializes it.
     * @param Screen main screen of the game
     * */
    public RenderEngine(Screen screen) {
        super();
        mainScreen = screen;
    }

    /**
     * Adds an object to be rendered.
     * @param GameObject new object
     * */
    public void addObject(GameObject obj) {
        super.addObject(obj);
        mainScreen.addDrawingComponents(obj);
    }

    /**
     * Removes an object from rendering
     * @param GameObject object to be removed
     * */
    public void removeObject(GameObject obj) {
        super.removeObject(obj);
        mainScreen.removeDrawingComponents(obj);
    }

    /**
     * {@inheritDoc}
     * */
    public void updateRenderEngine() {
        mainScreen.update();
    }
}