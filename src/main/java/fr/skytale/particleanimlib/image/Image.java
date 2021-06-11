package fr.skytale.particleanimlib.image;


import fr.skytale.particleanimlib.attributes.CustomVector;
import fr.skytale.particleanimlib.parent.AAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Image extends AAnimation {


    /******** Inner class ********/

    private static class ImageTaskData {
        //If rotation is required
        public Vector currentAxis;
        public double currentStepAngleAlpha;
        public int iterationCounter;
        public int changeRotationCounter;
        public HashMap<Vector, Color> currentImagePixels;
        public Integer taskId;
    }

    /******** Static Attributes ********/

    private static final String STEP_ANGLE_ALPHA_0 = "If axis defined, stepAngleAlpha must not be equal to 0.";

    /******** Static Methods ********/

    public static File getImagesDirectory(JavaPlugin plugin) {
        File imagesDir = new File(plugin.getDataFolder(), "images");
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
    //If rotation is required
    private Vector axis;
    private double stepAngleAlpha;
    //If rotation changes
    private Integer axisChangeFrequency = null;
    private double stepAngleAlphaChangeFactor;
    private Integer stepAngleAlphaChangeFrequency = null;
    private HashMap<Vector, Color> imagePixels;
    private ImageTaskData taskData;
    private boolean resetBeforeShow;
    //Randomness generator
    private Random random;
    //Does particle type support color
    private boolean hasColor;

    /******** Constructor ********/

    public Image() {
    }

    /******** Methods ********/

    @Override
    public void show() {
        if ((taskData != null) && taskData.taskId != null) {
            throw new IllegalStateException("Could not show this animation twice. Please create another animation.");
        }
        init();
        reset(taskData);
        taskData.taskId = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            //increment counters
            taskData.iterationCounter++;
            taskData.changeRotationCounter++;

            //Stop if required
            if (taskData.iterationCounter > ticksDuration) {
                taskData.taskId = null;
                return; //TODO export to task
            }

            //Do nothing if not shown
            if (taskData.iterationCounter % showFrequency != 0) {
                return;
            }

            boolean changeRotation = hasRotation();

            //Modify axis if required
            if (hasChangingRotationAxis() && taskData.changeRotationCounter % axisChangeFrequency == 0) {
                changeRotation = true;
                taskData.currentAxis = new Vector(random.nextDouble(),random.nextDouble(), random.nextDouble()).normalize().add(taskData.currentAxis.multiply(3)).normalize();
            }

            //Modify stepAngle if required
            if (hasChangingRotationStepAngle() && taskData.changeRotationCounter % stepAngleAlphaChangeFrequency == 0) {
                changeRotation = true;
                taskData.currentStepAngleAlpha += Math.PI / 20 * this.stepAngleAlphaChangeFactor * (random.nextInt(20) - 11);
            }

            //Compute rotation
            if (taskData.currentStepAngleAlpha != 0 && changeRotation) {
                taskData.currentImagePixels = (HashMap<Vector, Color>) taskData.currentImagePixels.entrySet()
                        .stream()
                        .collect(Collectors.toMap(e -> new CustomVector(e.getKey()).rotateAroundAxis(taskData.currentAxis, taskData.currentStepAngleAlpha), Map.Entry::getValue));
            }

            //show the result
            Location currentLocation = getBaseLocation();
            taskData.currentImagePixels.forEach(((vector, color) -> {
                ParticleBuilder particleBuilder = mainParticle.getParticleBuilder(currentLocation.clone().add(vector));
                if (hasColor) {
                    particleBuilder.setColor(color);
                }
                particleBuilder.display();
            }));

        },1L, 1L).getTaskId();
    }

    private ImageTaskData reset(ImageTaskData taskData) {
        if (resetBeforeShow || taskData == null) {
            if (taskData == null) {
                taskData = new ImageTaskData();
                random = new Random();
            }
            taskData.currentImagePixels = (HashMap<Vector, Color>) imagePixels.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey().clone(), Map.Entry::getValue));
            taskData.currentAxis = axis.clone();
            taskData.currentStepAngleAlpha = stepAngleAlpha;
        }
        taskData.iterationCounter = 0;
        taskData.changeRotationCounter = 0;
        return taskData;
    }

    private void init() {
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
            Vector center = u.clone().multiply(width - 1).add(v.clone().multiply(height -1)).divide(new Vector(2,2,2));

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

    public boolean hasRotation() {
        return this.axis != null;
    }
    public boolean hasChangingRotationAxis() {
        return hasRotation() && this.axisChangeFrequency != null;
    }
    public boolean hasChangingRotationStepAngle() {
        return hasRotation() && this.stepAngleAlphaChangeFrequency != null;
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

    public Vector getAxis() {
        return axis;
    }

    public void setAxis(Vector axis) {
        this.axis = axis;
    }

    public double getStepAngleAlpha() {
        return stepAngleAlpha;
    }

    public void setStepAngleAlpha(double stepAngleAlpha) {
        this.stepAngleAlpha = stepAngleAlpha;
    }

    public int getAxisChangeFrequency() {
        return axisChangeFrequency;
    }

    public void setAxisChangeFrequency(int axisChangeFrequency) {
        this.axisChangeFrequency = axisChangeFrequency;
    }

    public double getStepAngleAlphaChangeFactor() {
        return stepAngleAlphaChangeFactor;
    }

    public void setStepAngleAlphaChangeFactor(double stepAngleAlphaChangeFactor) {
        this.stepAngleAlphaChangeFactor = stepAngleAlphaChangeFactor;
    }

    public int getStepAngleAlphaChangeFrequency() {
        return stepAngleAlphaChangeFrequency;
    }

    public void setStepAngleAlphaChangeFrequency(int stepAngleAlphaChangeFrequency) {
        this.stepAngleAlphaChangeFrequency = stepAngleAlphaChangeFrequency;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public boolean isResetBeforeShow() {
        return resetBeforeShow;
    }

    public void setResetBeforeShow(boolean resetBeforeShow) {
        this.resetBeforeShow = resetBeforeShow;
    }

}
