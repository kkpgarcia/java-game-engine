package Editor;

import java.awt.Graphics2D;
import java.awt.Color;

import SKEngine.Core.Camera;
import SKEngine.Core.GameObject;
import SKEngine.Core.Renderer;
import SKEngine.Core.Vector2;
import SKEngine.Collision.BoundingBox2D;

public class EditorCellGrid extends GameObject {
    public int cellSize;
    
    private java.awt.geom.AffineTransform originalTransform;

    public EditorCellGrid(int size, Vector2 position) {
        super();
        this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(size,size));
        initialize(size, position);
    }

    public void initialize(int size, Vector2 position) {
        this.transform.position = position;
        this.cellSize = size;
        Vector2 correctedPos = new Vector2(this.transform.position.x + cellSize/2, this.transform.position.y + cellSize/2);
        this.boundingbox.translate(correctedPos);
    }


    @Override
    public void render(Graphics2D g) {
        originalTransform = g.getTransform();

        /*if(this.boundingbox != null) {
            g.setColor (Color.BLUE);
            g.drawRect((int)(((this.boundingbox.min.x + this.boundingbox.max.x)/2) - cellSize/2), (int)(((this.boundingbox.min.y + this.boundingbox.max.y)/2) - cellSize/2), (int)cellSize, (int)cellSize);
        }*/

        g.setColor(Color.GRAY);
        g.drawRect((int)this.transform.position.x,(int)this.transform.position.y, cellSize, cellSize);
        g.setTransform(originalTransform);
    }
}