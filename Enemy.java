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

        g.setColor(Color.RED);
        g.translate(this.rigidbody.position.x, this.rigidbody.position.y);
        g.drawOval((int)(-this.renderer.sprite.width/2),(int)(-this.renderer.sprite.height/2),(int)(this.renderer.sprite.width),(int)(this.renderer.sprite.height));


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