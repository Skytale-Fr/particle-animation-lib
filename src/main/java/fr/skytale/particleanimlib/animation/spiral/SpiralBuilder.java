package fr.skytale.particleanimlib.animation.spiral;

import fr.skytale.particleanimlib.animation.parent.AAnimationBuilder;
import org.bukkit.util.Vector;

public class SpiralBuilder extends AAnimationBuilder<Spiral> {

    public SpiralBuilder() {
        super();
        animation.setStep(0.1);
        animation.setRadius(1);
        animation.setStepAngle(Math.toRadians(30));
        animation.setGrowthSpeed(0);
    }

    @Override
    protected Spiral initAnimation() {
        return new Spiral();
    }

    /*********SETTERS des éléments spécifiques a la spirale ***********/
    public void setStep(double s) {
        if (s <= 0)
            throw new IllegalArgumentException("Step must not be equal to zero.");
        animation.setStep(s);
    }

    public void setRadius(double radius) {
        if (animation.getRadius() <= 0) {
            throw new IllegalArgumentException("Radius should be positive.");
        }
        animation.setRadius(radius);
    }

    public void setTarget(Vector target) {
        animation.setTarget(target);
    }

    public void setStepAngle(double a) {
        if (a == 0)
            throw new IllegalArgumentException("Step angle should not be equal to zero.");
        animation.setStepAngle(a);
    }

    public void setGrowthSpeed(double g) {
        animation.setGrowthSpeed(g);
    }

    @Override
    public Spiral getAnimation() {
        if (animation.getTarget() == null)
            throw new IllegalArgumentException("A target has to be defined.");
        return super.getAnimation();
    }
}
