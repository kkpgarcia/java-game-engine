package Editor;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.Arrays;

import SKEngine.Core.Vector2;

public class EditorInteraction implements MouseListener {
    public EditorInteraction(java.awt.Component comp) {
        comp.addMouseListener(this);
    }

    public void mousePressed(MouseEvent evt) {
        int mouseX = evt.getX();
        int mouseY = evt.getY();
        Optional<EditorCellGrid> optional = EditorGrid.cellGrid.stream()
                                                .filter((x) -> x.boundingbox.contains(new Vector2(mouseX, mouseY)))
                                                .findFirst();

        if(optional.isPresent()) {
            EditorCellGrid p = optional.get();
            if(EditorToolkit.currentSprite == null)
                return;
                
            Viewport.instance.paintSprite(EditorToolkit.currentSprite, p);
        }                                  
    }

    public void mouseDragged(MouseEvent evt) {

    }

    public void mouseReleased(MouseEvent evt) {

    }

    public void mouseEntered(MouseEvent evt) {}
    public void mouseExited(MouseEvent evt) {}
    public void mouseClicked(MouseEvent evt) {}
    public void mouseMoved(MouseEvent evt) {}
}