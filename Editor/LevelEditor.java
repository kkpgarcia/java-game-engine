package Editor;

import java.awt.event.MouseListener;
import javax.swing.JPanel;

import SKEngine.Core.Camera;
import SKEngine.Core.GameEngine;
import SKEngine.Core.Screen;
import SKEngine.Core.Input;

public class LevelEditor {
    public static void main(String[] args) {
        new LevelEditor();
    }

    public LevelEditor() {
        Screen screen = new Screen("Level Editor", 1000, 1000);
        Input input = new Input(screen);
        GameEngine game = new GameEngine(screen);
        Camera camera = new Camera();

        EditorWindow editorWindow = new EditorWindow();
        editorWindow.createEditorScene();

        Viewport viewport = new Viewport();

        EditorInteraction interaction = new EditorInteraction(screen);
        screen.addMouseListener(interaction);

        AssetWindow assetWindow = new AssetWindow();
        EditorToolkit toolkit = new EditorToolkit();

        game.start();
    }
}