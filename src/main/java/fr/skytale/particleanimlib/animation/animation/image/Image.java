package fr.skytale.particleanimlib.animation.animation.image;


import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Image extends AAnimation implements ISubAnimation {

    public static final String IMAGES_FOLDER = "images";
    /******** Attributes ********/

    private String imageFileName;

    /******** Constructor ********/

    public Image() {
    }

    /******** Static Methods ********/

    public static File getImagesDirectory(JavaPlugin plugin) {
        File pluginDir = plugin.getDataFolder();
        if (!pluginDir.exists()) {
            boolean result = pluginDir.mkdir();
            if (!result) {
                throw new IllegalStateException("The plugin directory could not be created. It is probably a permission issue.");
            }
        }
        File imagesDir = new File(pluginDir, IMAGES_FOLDER);
        if (!imagesDir.exists()) {
            boolean result = imagesDir.mkdir();
            if (!result) {
                throw new IllegalStateException("The " + IMAGES_FOLDER + " directory could not be created. It is probably a permission issue.");
            }
        }
        return imagesDir;
    }

    public static File getImageFile(JavaPlugin plugin, String imageFileName) {
        File imagesDir = getImagesDirectory(plugin);

        File imageFile = new File(imagesDir, imageFileName);
        if (!imageFile.exists()) {
            throw new IllegalArgumentException(String.format("The image %s does not exist.", imageFile.getAbsolutePath()));
        }
        if (!imageFile.isFile()) {
            throw new IllegalArgumentException(String.format("%s should not be a directory.", imageFile.getAbsolutePath()));
        }
        return imageFile;
    }

    /******** Methods ********/

    @Override
    public ImageTask show() {
        return new ImageTask(this);
    }

    @Override
    public Image clone() {
        Image obj = (Image) super.clone();
        return obj;
    }

    public Map<Vector, Color> getImagePixels() {
        try {
            Map<Vector, Color> imagePixels = new HashMap<>();

            //Load image
            BufferedImage bufferedImage = ImageIO.read(getImageFile(plugin, imageFileName));
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();

            //We compute the center of the figure in order for each pixel's vector to start from the center.
            Vector center = AAnimationTask.U.clone().multiply(width - 1).add(AAnimationTask.V.clone().multiply(height - 1)).divide(new Vector(2, 2, 2));

            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    Color pixelColor = new Color(bufferedImage.getRGB(x, y), true);

                    //we do not consider transparent pixels
                    if (pixelColor.getAlpha() == 0) continue;

                    //We define each pixel's vector from the center of the image in order to have better rotation ability later
                    Vector toPixelVector = AAnimationTask.U.clone().multiply(x).add(AAnimationTask.V.clone().multiply(y)).subtract(center);
                    imagePixels.put(toPixelVector, pixelColor);
                }
            }
            return imagePixels;
        } catch (IOException e) {
            throw new IllegalArgumentException("The image could not be read", e);
        }
    }

    /******** Getters & Setters ********/

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
