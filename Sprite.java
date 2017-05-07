/**
* Author: Kyle Kristopher P. Garcia
* Date: February 2, 2017
*/

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
* This class contains the image loaded from the resource utility. Aside from the
* image, it also contains some basic information about the image itself
*/
public class Sprite {
    public BufferedImage image;
    public int width;
    public int height;

    /**
    * This is a basic constructor of the class.
    */
    public Sprite() {
        image = null;
        width = 20;
        height = 20;
    }

    /**
    * This is a constructor that recieves an image from the outside
    * source.
    * @param image image to be contained
    */
    public Sprite(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
    * This method helps create a sprite that was made without any
    * parameters.
    * @param image image to be contained
    */
    public static Sprite create(BufferedImage image) {
        if(image != null) {
            System.out.println("Sprite already has an image!");
            return null;
        }
        Sprite sprite = new Sprite();
        sprite.image = image;
        sprite.width = image.getWidth();
        sprite.height = image.getHeight();
        return sprite;
    }
}