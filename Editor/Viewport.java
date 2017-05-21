package Editor;

import SKEngine.Core.GameObject;
import SKEngine.Core.Sprite;
import SKEngine.Core.Screen;
import SKEngine.Core.Vector2;
import SKEngine.Collision.BoundingBox2D;

import SKEngine.Core.Input;
import SKEngine.Core.InputAction;

import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class Viewport extends GameObject {
    public static Viewport instance;
    public static ArrayList<EditorObject> objects;
    public Input input;
    
    public float movementSpeed = 10;

    private InputAction rightArrow = new InputAction("right");
    private InputAction leftArrow = new InputAction("left");
    private InputAction upArrow = new InputAction("up");
    private InputAction downArrow = new InputAction("down");

    public Viewport() {
        super();
        instance = this;
        objects = new ArrayList<EditorObject>();
    }

    public void bindInput() {
        input.mapToKey(rightArrow, KeyEvent.VK_RIGHT);
        input.mapToKey(leftArrow, KeyEvent.VK_LEFT);
        input.mapToKey(upArrow, KeyEvent.VK_UP);
        input.mapToKey(downArrow, KeyEvent.VK_DOWN);
    }

    public void update() {
        super.update();
        if (rightArrow.isPressed()) {
            this.transform.position.x += movementSpeed;
        }
        if (leftArrow.isPressed()) {
            this.transform.position.x -= movementSpeed;
        }
        if (upArrow.isPressed()) {
            this.transform.position.y -= movementSpeed;
        }
        if (downArrow.isPressed()) {
            this.transform.position.y += movementSpeed;
        }
    }

    public void paintSprite(Sprite sprite, EditorCellGrid grid, String spriteName) {
        if(sprite == null) {
            return;
        }
        EditorObject editorObject = new EditorObject();
        editorObject.spriteName = spriteName;
        editorObject.renderer.sprite = sprite;
        Vector2 position = new Vector2(grid.transform.position.x - Screen.width/2 + grid.cellSize/2, grid.transform.position.y - Screen.height/2 + grid.cellSize/2);
        editorObject.transform.position = position;
        editorObject.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(50,50));
        editorObject.boundingbox.translate(position);
        editorObject.transform.scale.set(3,3);
        //editorObject.transform.setParent(this);
        objects.add(editorObject);
    }
}