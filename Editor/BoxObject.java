package Editor;

import java.awt.Graphics2D;
import java.awt.Color;

import SKEngine.Core.GameObject;
import SKEngine.Core.Vector2;
import SKEngine.Core.Screen;

public class BoxObject extends EditorObject {

    private java.awt.geom.AffineTransform originalTransform;

    public Vector2 min;
    public Vector2 max;

    public BoxObject(Vector2 initialMin) {
        super();
        min = initialMin;
        max = initialMin;
    }

    public void updateMaximum(Vector2 max) {
        this.max = min.add(max);
        /*if(min.x < this.max.x) {
            this.max.x = max.x;
        } else {
            this.min.x = max.x;
        }
        if(min.y < this.max.y) {
            this.max.y = max.y;
        } else {
            this.min.y = max.y;
        }*/
    }

    @Override
    public void render(Graphics2D g) {
        originalTransform = g.getTransform();
        //g.translate((Screen.width/2), (Screen.height/2));
        g.setColor(Color.RED);
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;
        
        if(min.x < max.x) {
            x = (int)(min.x + max.x /2);
            width = (int)(min.x + max.x);
        } else {
            x = (int)(max.x - min.x / 2);
            width = (int)(max.x + min.x);
        }
        if(min.y < max.y) {
            x = (int)(min.y + max.y /2);
            height = (int)(min.y + max.y);
        } else {
            x = (int)(max.y - min.y / 2);
            height = (int)(max.y + min.y);
        }

        g.drawRect(x,y,Math.abs(width),Math.abs(height));


        g.setTransform(originalTransform);
    }
}