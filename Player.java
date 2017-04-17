import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
   
    Input input;

    private InputAction moveUp;
    private InputAction moveDown;
    private InputAction moveLeft;
    private InputAction moveRight;
    public Player() {
        super();
    }

    public void bindInput() {
        moveUp = new InputAction("up");
        moveDown = new InputAction("down");
        moveLeft = new InputAction("left");
        moveRight = new InputAction("right");

        input.mapToKey(moveUp, KeyEvent.VK_UP);
        input.mapToKey(moveDown, KeyEvent.VK_DOWN);
        input.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        input.mapToKey(moveRight, KeyEvent.VK_RIGHT);
    }

	public void update() {
        super.update();

        Vector2 axis = new Vector2();

        if(moveLeft.isPressed()) {
            this.transform.translate(new Vector2(-this.velocity.x,0));
            this.normalDirection.x = -1;
        } else if(!moveLeft.isPressed() && !moveRight.isPressed()) {
            this.normalDirection.x = 0;
        }
        if(moveRight.isPressed()) {
            this.transform.translate(new Vector2(this.velocity.x,0));
            this.normalDirection.x = 1;
        } else if(!moveLeft.isPressed() && !moveRight.isPressed()){
            this.normalDirection.x = 0;
        }
        if(moveUp.isPressed()) {
            this.transform.translate(new Vector2(0, -this.velocity.y));
            this.normalDirection.y = -1;
        } else if(!moveUp.isPressed() && !moveDown.isPressed()){
            this.normalDirection.y = 0;
        }
        if(moveDown.isPressed()) {
            this.transform.translate(new Vector2(0, this.velocity.y));
            this.normalDirection.y = 1;
        } else if(!moveUp.isPressed() && !moveDown.isPressed()){
            this.normalDirection.y = 0;
        }

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
		g.fillOval((int)(-this.renderer.sprite.width/2),(int)(-this.renderer.sprite.height/2),(int)(this.renderer.sprite.width),(int)(this.renderer.sprite.height));
        super.reset(g);
	}
}