package Game;

import SKEngine.Core.GameObject;
import SKEngine.Utility.Resources;
import SKEngine.Core.Sprite;

import java.awt.image.BufferedImage;

public class Background extends GameObject {
    public Background() {
        super();
        BufferedImage image = Resources.loadImageFull("bg.jpg");
        this.renderer.sprite = new Sprite(image);
    }
}