package fr.skytale.particleanimlib.parent;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Random;

public abstract class ARotatingAnimationTask<T extends ARotatingAnimation> extends AAnimationTask<T> {

    //If rotation changes
    private Integer axisChangeFrequency = null;
    private final double stepAngleAlphaChangeFactor;
    private final double stepAngleAlphaMax;
    private Integer stepAngleAlphaChangeFrequency = null;

    //Randomness generator
    private Random random;

    // Evolving variables
    private Vector currentAxis;
    private double currentStepAngleAlpha;

    public ARotatingAnimationTask(T animation) {
        super(animation);
        this.random = new Random();
        this.axisChangeFrequency = animation.getAxisChangeFrequency();
        this.stepAngleAlphaChangeFactor = animation.getStepAngleAlphaChangeFactor();
        this.stepAngleAlphaMax = animation.getStepAngleAlphaMax();
        this.stepAngleAlphaChangeFrequency = animation.getStepAngleAlphaChangeFrequency();
        if (animation.getAxis() != null)
            this.currentAxis = animation.getAxis().clone();
        this.currentStepAngleAlpha = animation.getStepAngleAlpha();
    }

    protected boolean hasRotation() {
        return this.currentAxis != null;
    }

    protected boolean hasChangingRotationAxis() {
        return hasRotation() && this.axisChangeFrequency != null;
    }

    protected boolean hasChangingRotationStepAngle() {
        return hasRotation() && this.stepAngleAlphaChangeFrequency != null;
    }

    @Override
    public final void show(Location iterationBaseLocation) {
        // Stop if required
        if (hasDurationEnded()) {
            stopAnimation();
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
            currentStepAngleAlpha += Math.PI / 500 * this.stepAngleAlphaChangeFactor * (random.nextInt(20) - 11);
            if (currentStepAngleAlpha > 0 && currentStepAngleAlpha > stepAngleAlphaMax) {
                currentStepAngleAlpha = stepAngleAlphaMax;
            } else if (currentStepAngleAlpha < 0 && currentStepAngleAlpha < -stepAngleAlphaMax) {
                currentStepAngleAlpha = -stepAngleAlphaMax;
            }
        }

        //Compute rotation
        if (currentStepAngleAlpha != 0 && changeRotation) {
            computeRotation(currentAxis, currentStepAngleAlpha);
        }

        //show the result
        showRotated(iterationBaseLocation);
    }

    protected abstract void showRotated(Location iterationBaseLocation);

    protected abstract void computeRotation(Vector currentAxis, double currentStepAngleAlpha);

}
