package Editor;

import SKEngine.Core.GameObject;
import SKEngine.Core.Vector2;
import SKEngine.Collision.BoundingBox2D;
import SKEngine.Core.Screen;

import java.awt.Graphics2D;
import java.awt.Color;

public class Pointer extends GameObject {
    private java.awt.geom.AffineTransform originalTransform;

    public Pointer() {
        super();
        this.boundingbox = new BoundingBox2D(new Vector2(), new Vector2(10,10));
    }

    public void updatePointer(Vector2 pos) {
        pos.x -= Screen.width/2 + 5;
        pos.y -= Screen.height/2 + 5;
        this.transform.position = pos;
        this.boundingbox.translate(pos);
    }

    @Override
    public void render(Graphics2D g) {
        originalTransform = g.getTransform();
        g.translate((Screen.width/2), (Screen.height/2));
        if(this.boundingbox != null) {
            g.setColor (Color.BLUE);
            g.drawRect((int)(((this.boundingbox.min.x + this.boundingbox.max.x)/2)), (int)(((this.boundingbox.min.y + this.boundingbox.max.y)/2)), 10, 10);
        }
        g.setColor(Color.BLACK);
        g.drawOval((int)this.transform.position.x,(int)this.transform.position.y, 10, 10);
        g.setTransform(originalTransform);
    }
}