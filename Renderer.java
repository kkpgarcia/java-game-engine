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

    public void render(Transform transform, Graphics2D g) {
        originalTransform = g.getTransform();
        g.translate((Screen.width/2), (Screen.height/2));

        if(sprite == null || sprite.image == null)
            return;
        
        int x = (int)((transform.position.x /*- Camera.transform.position.x*/ - sprite.width/2) + Screen.width/2);
        int y = (int)((transform.position.y /*- Camera.transform.position.y*/ - sprite.height/2) + Screen.height/2);
        if(!flipped) {
            g.drawImage(
                sprite.image,
                x,
                y,
                null
            );
        } else {
            g.drawImage(
                sprite.image,
                x,
                y,
                -sprite.width,
                sprite.height,
                null
            );
        }
    }

    public void reset(Graphics2D g) {
        g.setTransform(originalTransform);
    }
}