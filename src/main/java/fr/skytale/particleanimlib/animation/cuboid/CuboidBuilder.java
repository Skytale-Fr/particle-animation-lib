package fr.skytale.particleanimlib.animation.cuboid;


import fr.skytale.particleanimlib.animation.parent.AAnimationBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CuboidBuilder extends AAnimationBuilder<Cuboid> {

    public CuboidBuilder() {
        super();
        animation.setStep(0.5);
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
        animation.setAxisChangeFrequency(null);
        animation.setStepAngleAlphaChangeFrequency(null);
        animation.setStepAngleAlphaMax(Math.toRadians(30));
    }

    @Override
    protected Cuboid initAnimation() {
        return new Cuboid();
    }

    /*********SETTERS des éléments spécifiques au cube ***********/

    public void setFromLocationToFirstCorner(Vector fromLocationToFirstCorner) {
        animation.setFromLocationToFirstCorner(fromLocationToFirstCorner);
    }

    public void setFromLocationToSecondCorner(Vector fromLocationToSecondCorner) {
        animation.setFromLocationToSecondCorner(fromLocationToSecondCorner);
    }

    public void setCornersAndComputeCenter(Location firstCorner, Location secondCorner) {
        Vector fromFirstToSecond = secondCorner.toVector().subtract(firstCorner.toVector());
        Vector fromCenterToSecond = fromFirstToSecond.clone().multiply(0.5);
        Location center = firstCorner.clone().add(fromCenterToSecond);
        animation.setLocation(center);
        animation.setFromLocationToFirstCorner(fromCenterToSecond.clone().multiply(-1));
        animation.setFromLocationToSecondCorner(fromCenterToSecond.clone());
    }

    public void setAxis(Vector axis) {
        animation.setAxis(axis);
    }

    public void setStepAngleAlpha(double s) {
        animation.setStepAngleAlpha(s);
    }

    public void setStepAngleAlphaChangeFrequency(Integer stepAngleAlphaChangeFrequency) {
        if (stepAngleAlphaChangeFrequency != null && stepAngleAlphaChangeFrequency < 0)
            throw new IllegalArgumentException("StepAngleAlphaChangeFrequency can not be negative");
        animation.setStepAngleAlphaChangeFrequency(stepAngleAlphaChangeFrequency);
    }

    public void setStepAngleAlphaChangeFactor(double stepAngleAlphaChangeFactor) {
        animation.setStepAngleAlphaChangeFactor(stepAngleAlphaChangeFactor);
    }

    public void setStepAngleAlphaMax(double stepAngleAlphaMax) {
        animation.setStepAngleAlphaMax(stepAngleAlphaMax);
    }

    public void setAxisChangeFrequency(Integer axisChangeFrequency) {
        if (axisChangeFrequency != null && axisChangeFrequency < 0)
            throw new IllegalArgumentException("AxisChangeFrequency can not be negative");
        animation.setAxisChangeFrequency(axisChangeFrequency);
    }

    public void setStep(double step) {
        if (step <= 0)
            throw new IllegalArgumentException("Step must be strictly positive");
        animation.setStep(step);
    }

    @Override
    public Cuboid getAnimation() {
        if (animation.getFromLocationToFirstCorner() == null) {
            throw new IllegalArgumentException("fromLocationToFirstCorner must not be null.");
        }
        if (animation.getFromLocationToSecondCorner() == null) {
            throw new IllegalArgumentException("fromLocationToSecondCorner must not be null.");
        }
        return super.getAnimation();
    }
}
