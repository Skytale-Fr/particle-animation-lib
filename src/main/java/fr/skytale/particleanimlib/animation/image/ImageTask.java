package fr.skytale.particleanimlib.animation.image;

import fr.skytale.particleanimlib.attributes.CustomVector;
import fr.skytale.particleanimlib.parent.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ImageTask extends AAnimationTask<Image> {
    //If rotation changes
    private Integer axisChangeFrequency = null;
    private double stepAngleAlphaChangeFactor;
    private Integer stepAngleAlphaChangeFrequency = null;
    //Randomness generator
    private Random random;
    //Does particle type support color
    private boolean hasColor;
    public Vector currentAxis;
    public double currentStepAngleAlpha;
    public HashMap<Vector, Color> currentImagePixels;
    public int taskId;

    public ImageTask(Image image) {
        super(image);
        init();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    protected void init() {
        super.init();
        this.random = new Random();
        this.currentImagePixels = (HashMap<Vector, Color>) animation.getImagePixels().entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().clone(), Map.Entry::getValue));
        if (animation.getAxis() != null)
            this.currentAxis = animation.getAxis().clone();
        this.currentStepAngleAlpha = animation.getStepAngleAlpha();
        this.axisChangeFrequency = animation.getAxisChangeFrequency();
        this.stepAngleAlphaChangeFrequency = animation.getStepAngleAlphaChangeFrequency();
        this.stepAngleAlphaChangeFactor = animation.getStepAngleAlphaChangeFactor();
        this.hasColor = this.mainParticle.getParticleEffect() == ParticleEffect.REDSTONE;
    }

    @Override
    public void show(Location iterationBaseLocation) {

        //Stop if required
        if (iterationCount > ticksDuration) {
stopAnimation(taskId);
            return;
        }

        boolean changeRotation = hasRotation();

        //Modify axis if required
        if (hasChangingRotationAxis() && (axisChangeFrequency == 0 || iterationCount % axisChangeFrequency == 0)) {
            changeRotation = true;
            currentAxis = new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble()).normalize().add(currentAxis.multiply(3)).normalize();
        }

        //Modify stepAngle if required
        if (hasChangingRotationStepAngle() && (stepAngleAlphaChangeFrequency == 0 || iterationCount % stepAngleAlphaChangeFrequency == 0)) {
            changeRotation = true;
            currentStepAngleAlpha += Math.PI / 20 * this.stepAngleAlphaChangeFactor * (random.nextInt(20) - 11);
        }

        //Compute rotation
        if (currentStepAngleAlpha != 0 && changeRotation) {
            currentImagePixels = (HashMap<Vector, Color>) currentImagePixels.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> new CustomVector(e.getKey()).rotateAroundAxis(currentAxis, currentStepAngleAlpha), Map.Entry::getValue));
        }

        //show the result
        Location currentLocation = iterationBaseLocation;

        currentImagePixels.forEach(((vector, color) -> {
            ParticleBuilder particleBuilder = mainParticle.getParticleBuilder(currentLocation.clone().add(vector));
            if (hasColor) {
                particleBuilder.setColor(color);
            }
            particleBuilder.display();
        }));
    }

    /*public void run() {
        //increment counters
        iterationCounter++;
        changeRotationCounter++;

        //Stop if required
        if (iterationCounter > ticksDuration) {
stopAnimation(taskId);
            return;
        }

        //Do nothing if not shown
        if (showFrequency != 0 && (iterationCounter % showFrequency != 0)) {
            return;
        }

        boolean changeRotation = hasRotation();

        //Modify axis if required
        if (hasChangingRotationAxis() && (axisChangeFrequency == 0 || changeRotationCounter % axisChangeFrequency == 0)) {
            changeRotation = true;
            currentAxis = new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble()).normalize().add(currentAxis.multiply(3)).normalize();
        }

        //Modify stepAngle if required
        if (hasChangingRotationStepAngle() && (stepAngleAlphaChangeFrequency == 0 || changeRotationCounter % stepAngleAlphaChangeFrequency == 0)) {
            changeRotation = true;
            currentStepAngleAlpha += Math.PI / 20 * this.stepAngleAlphaChangeFactor * (random.nextInt(20) - 11);
        }

        //Compute rotation
        if (currentStepAngleAlpha != 0 && changeRotation) {
            currentImagePixels = (HashMap<Vector, Color>) currentImagePixels.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> new CustomVector(e.getKey()).rotateAroundAxis(currentAxis, currentStepAngleAlpha), Map.Entry::getValue));
        }

        //show the result
        Location currentLocation = animation.getBaseLocation();

        if (moveVector != null && moveFrequency != 0 && (iterationCounter % moveFrequency == 0))
            currentLocation.add(moveStepVector);

        currentImagePixels.forEach(((vector, color) -> {
            ParticleBuilder particleBuilder = mainParticle.getParticleBuilder(currentLocation.clone().add(vector));
            if (hasColor) {
                particleBuilder.setColor(color);
            }
            particleBuilder.display();
        }));

        animation.setLocation(currentLocation);

    }*/

    public boolean hasRotation() {
        return this.currentAxis != null;
    }

    public boolean hasChangingRotationAxis() {
        return hasRotation() && this.axisChangeFrequency != null;
    }

    public boolean hasChangingRotationStepAngle() {
        return hasRotation() && this.stepAngleAlphaChangeFrequency != null;
    }

}
