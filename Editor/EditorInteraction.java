package Editor;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.Arrays;

import SKEngine.Core.Vector2;
import SKEngine.Core.GameObject;
import SKEngine.Core.Screen;
import SKEngine.Collision.BoundingBox2D;

public class EditorInteraction implements MouseListener, MouseMotionListener {
    public Pointer pointer;
    private boolean isPressed = false;
    private boolean isDrawing = false;

    private BoxObject currentDrawing;

    public EditorInteraction(java.awt.Component comp) {
        comp.addMouseListener(this);
        comp.addMouseMotionListener(this);
        pointer = new Pointer();
    }

    public void mousePressed(MouseEvent evt) {

        if(isPressed)
            return;

        isPressed = true;

        int mouseX = evt.getX();
        int mouseY = evt.getY();
        String currentTool = EditorToolkit.currentTool;
        
        if(currentTool.equals("BRUSH")) {
            Optional<EditorCellGrid> optional = EditorGrid.cellGrid.stream()
                                                    .filter((x) -> x.boundingbox.contains(new Vector2(mouseX, mouseY)))
                                                    .findFirst();

            if(optional.isPresent()) {
                EditorCellGrid p = optional.get();
                if(EditorToolkit.currentSprite == null)
                    return;
                Viewport.instance.paintSprite(EditorToolkit.currentSprite, p);
            }
        } else if (currentTool.equals("ERASER")) {
            Optional<EditorObject> optional = Viewport.objects.stream()
                                                .filter((x) -> x.boundingbox.isOverlapping(pointer.boundingbox))
                                                .findFirst();

            if(optional.isPresent()) {
                EditorObject p = optional.get();
                if(p == null)
                    return;
                GameObject.destroy(p);
            }
        } else if (currentTool.equals("RBODY")) {
            if(isDrawing)
                return;

            isDrawing = true;
            Vector2 position = new Vector2(mouseX - Screen.width/2, mouseY - Screen.height/2);
            BoxObject boxObject = new BoxObject(position);
            currentDrawing = boxObject;
        }                    
    }

    public void mouseDragged(MouseEvent evt) {
        int mouseX = evt.getX();
        int mouseY = evt.getY();

        if(isDrawing) {
            Vector2 position = new Vector2(mouseX - Screen.width/2, mouseY - Screen.height/2);
            currentDrawing.updateMaximum(position);
        }
    }

    public void mouseReleased(MouseEvent evt) {
        if(isPressed)
            isPressed = false;

        if(isDrawing) {
            isDrawing = false;
            currentDrawing = null;
        }
    }

    public void mouseEntered(MouseEvent evt) {}
    public void mouseExited(MouseEvent evt) {}
    public void mouseClicked(MouseEvent evt) {}
    public void mouseMoved(MouseEvent evt) {
        int mouseX = evt.getX();
        int mouseY = evt.getY();
        pointer.updatePointer(new Vector2(mouseX , mouseY ));
    }
}