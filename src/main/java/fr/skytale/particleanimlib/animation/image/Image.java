package fr.skytale.particleanimlib.animation.image;


import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Image extends ARotatingAnimation {

    /******** Static Methods ********/

    public static File getImagesDirectory(JavaPlugin plugin) {
        File pluginDir = plugin.getDataFolder();
        if (!pluginDir.exists()) {
            boolean result = pluginDir.mkdir();
            if (!result) {
                throw new IllegalStateException("The plugin directory could not be created. It is probably a permission issue.");
            }
        }
        File imagesDir = new File(pluginDir, "images");
        if (!imagesDir.exists()) {
            boolean result = imagesDir.mkdir();
            if (!result) {
                throw new IllegalStateException("The images directory could not be created. It is probably a permission issue.");
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

    /******** Attributes ********/

    //Starting plane
    private Vector u;
    private Vector v;
    //Image file name
    private String imageFileName;
    private HashMap<Vector, Color> imagePixels;
    private boolean hasColor;

    /******** Constructor ********/

    public Image() {
    }

    /******** Methods ********/

    @Override
    public void show() {
        init();
        new ImageTask(this);
    }

    protected void init() {
        if (imagePixels != null) {
            //Already initialized
            return;
        }

        //Load image
        imagePixels = new HashMap<>();
        hasColor = mainParticle.getParticleEffect() == ParticleEffect.REDSTONE;

        try {
            BufferedImage bufferedImage = ImageIO.read(getImageFile(plugin, imageFileName));
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();

            //We compute the center of the figure in order for each pixel's vector to start from the center.
            Vector center = u.clone().multiply(width - 1).add(v.clone().multiply(height - 1)).divide(new Vector(2, 2, 2));

            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    Color pixelColor = new Color(bufferedImage.getRGB(x, y), true);

                    //we do not consider transparent pixels
                    if (pixelColor.getAlpha() == 0) continue;

                    //We define each pixel's vector from the center of the image in order to have better rotation ability later
                    Vector toPixelVector = u.clone().multiply(x).add(v.clone().multiply(y)).subtract(center);
                    imagePixels.put(toPixelVector, pixelColor);
                }
            }


        } catch (IOException e) {
            throw new IllegalArgumentException("The image could not be read", e);
        }
    }

    /******** Getters & Setters ********/

    public void setU(Vector u) {
        this.u = u;
    }

    public void setV(Vector v) {
        this.v = v;
    }

    public Vector getV() {
        return v;
    }

    public Vector getU() {
        return u;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public HashMap<Vector, Color> getImagePixels() {
        return imagePixels;
    }

    public boolean isHasColor() {
        return hasColor;
    }

    @Override
    public Image clone() {
        Image obj = (Image) super.clone();
        obj.u = u.clone();
        obj.v = v.clone();
        obj.imagePixels = imagePixels == null ? null : (HashMap<Vector, Color>) imagePixels.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().clone(), e -> new Color(e.getValue().getRGB())));
        ;
        return obj;
    }
}
