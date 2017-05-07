import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Renderer {
    public Sprite sprite;
    public boolean flipped;

    private AffineTransform originalTransform;
    public Renderer() {
        this.sprite = null;
        this.flipped = false;
    }

    public Renderer(Sprite sprite) {
        this.sprite = sprite;
        this.flipped = false;
    }

    public void render(Transform transform, Graphics2D g, BoundingBox2D bb) {
        originalTransform = g.getTransform();
        g.translate((Screen.width/2), (Screen.height/2));
        
        g.setColor(java.awt.Color.RED);
        g.drawRect((int)((bb.min.x + bb.max.x)/2) - 50, (int)((bb.min.y + bb.max.y)/2) - 50, 100,100);
        
        /*g.setColor(java.awt.Color.BLUE);
        g.drawRect((int)bb.min.y, (int)bb.max.y, 40,40);*/

        if(sprite == null || sprite.image == null) {
            reset(g);
            return;
        }


        int width = sprite.width * (int)transform.scale.x;
        int height = sprite.height * (int)transform.scale.y;
        
        int x = (int)((transform.position.x/*- Camera.transform.position.x*/ - (width)/2));
        int y = (int)((transform.position.y/*- Camera.transform.position.y*/ - (height)/2));
        if(!flipped) {
            g.drawImage(
                sprite.image,
                x,
                y,
                width,
                height,
                null
            );
        } else {
            g.drawImage(
                sprite.image,
                x + width,
                y,
                -width,
                height,
                null
            );
        }
        reset(g);
    }

    public void reset(Graphics2D g) {
        g.setTransform(originalTransform);
    }
}