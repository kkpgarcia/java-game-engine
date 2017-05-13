package SKEngine.Utility;

import java.io.File;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * <h2>Resources</h2>
 * This class is a utility class that helps the user
 * obtain resources from the resource folder
 * @author Kyle Kristopher P. Garcia
 * @since 2017-02-02
 */
public class Resources {

    /**
     * Loads an image with the given directory
     * @param String directory
     * @return BufferedImage image loaded
     * */
    public static BufferedImage loadImage(String name) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(getMain() + "/" + name));
        } catch(IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * Get the absolute file path of the project
     * @return String absolute file path
     * */
    private static String getMain() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }
}