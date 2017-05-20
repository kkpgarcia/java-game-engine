package Editor;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.Arrays;

import SKEngine.Core.Vector2;
import SKEngine.Core.GameObject;

public class EditorInteraction implements MouseListener, MouseMotionListener {
    public Pointer pointer;
    private boolean isPressed = false;
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
                                                
            System.out.println("Mouse X: " + mouseX);
            System.out.println("Mouse Y: " + mouseY);
            //System.out.println(optional.isPresent());
            if(optional.isPresent()) {
                EditorObject p = optional.get();
                if(p == null)
                    return;
                System.out.println("Object X: " + p.transform.position.x);
                System.out.println("Object Y: " + p.transform.position.y);
                GameObject.destroy(p);
            }
        }                        
    }

    public void mouseDragged(MouseEvent evt) {

    }

    public void mouseReleased(MouseEvent evt) {
        if(isPressed)
            isPressed = false;
    }

    public void mouseEntered(MouseEvent evt) {}
    public void mouseExited(MouseEvent evt) {}
    public void mouseClicked(MouseEvent evt) {}
    public void mouseMoved(MouseEvent evt) {
        int mouseX = evt.getX();
        int mouseY = evt.getY();
        pointer.updatePointer(new Vector2(mouseX, mouseY));
    }
}