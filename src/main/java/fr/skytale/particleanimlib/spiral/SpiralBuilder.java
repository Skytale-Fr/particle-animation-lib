package fr.skytale.particleanimlib.spiral;

import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.Location;

public class SpiralBuilder extends AAnimationBuilder<Spiral> {

    public SpiralBuilder() {
        super();
        animation = new Spiral();
        animation.setStep(0.1);
        animation.setRadius(1);
        animation.setStepAngle(Math.toRadians(30));
        animation.setGrowthSpeed(0);
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

    public void setTarget(Location target) {
        if(target==null)
            throw new IllegalArgumentException("A target has to be defined.");
        animation.setTarget(target);
    }

    public void setStepAngle(double a){
        if(a==0)
            throw new IllegalArgumentException("Step angle should not be equal to zero.");
        animation.setStepAngle(a);
    }

    public void setGrowthSpeed(double g){
        animation.setGrowthSpeed(g);
    }

    @Override
    public Spiral getAnimation() {
        return animation;
    }
}
