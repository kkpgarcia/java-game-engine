import java.awt.Graphics2D;
import java.awt.Color;

public class Enemy extends GameObject {
    
    public Enemy() {
        super();
    }

	public void update() {
        super.update();
		//this.transform.translate(this.velocity);
	}

	public void render(Graphics2D g) {
		super.render(g);
        if(color == null) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(color);
        }
		g.translate(this.transform.position.x, this.transform.position.y);
		g.fillRect((int)(0 - this.aabb.getCenter().x),(int)(0 - this.aabb.getCenter().y),(int)(this.aabb.width),(int)(this.aabb.height));
        super.reset(g);
	}
}