

/**
* Author: Kyle Kristopher P. Garcia
* Date: February 2, 2017
*/

import java.io.File;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
* This class is a utility class that helps the user
* obtain resources from the resource folder
*/
public class Resources {
    public static void loadText(String name) {

    }

    public static BufferedImage loadImage(String name) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(getMain() + "/" + name));
        } catch(IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    private static String getMain() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }
}