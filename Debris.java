import java.awt.Graphics2D;
import java.awt.Color;

public class Debris extends GameObject {
    public Debris() {
        super();
    }

    public void render(Graphics2D g) {
        super.render(g);
        if(color == null) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(color);
        }

        g.translate(this.transform.position.x, this.transform.position.y);
        g.fillOval((int)(-this.renderer.sprite.width/2),(int)(-this.renderer.sprite.height/2),(int)(this.renderer.sprite.width),(int)(this.renderer.sprite.height));
        super.reset(g);
    }
}