package Editor;

import SKEngine.Core.GameObject;
import SKEngine.Core.Sprite;
import SKEngine.Core.Screen;
import SKEngine.Core.Vector2;

public class Viewport extends GameObject {
    public static Viewport instance;
    
    public Viewport() {
        super();
        instance = this;
    }

    public void update() {

    }

    public void paintSprite(Sprite sprite, EditorCellGrid grid) {
        if(sprite == null) {]
            return;
        }
        EditorObject editorObject = new EditorObject();
        editorObject.renderer.sprite = sprite;
        Vector2 position = new Vector2(grid.transform.position.x - Screen.width/2 + grid.cellSize/2, grid.transform.position.y - Screen.height/2 + grid.cellSize/2);
        editorObject.transform.position = position;
        editorObject.transform.scale.set(3,3);
        editorObject.transform.setParent(this);
    }
}