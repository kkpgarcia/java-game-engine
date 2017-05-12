import java.awt.image.BufferedImage;

public class NetDummy extends GameObject {
    public NetworkActor networkActor;

    public NetDummy() {
        super();
        this.transform.scale.set(4, 4);
        BufferedImage image = Resources.loadImage("Assets/pink-alien.png");
        this.renderer.sprite = new Sprite(image);
        this.rigidbody = new Rigidbody(new Polygon(40, 40), 0, 0);
    }
}