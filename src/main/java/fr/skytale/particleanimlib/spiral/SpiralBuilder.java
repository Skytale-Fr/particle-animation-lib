package fr.skytale.particleanimlib.spiral;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.Location;

public class SpiralBuilder extends AAnimationBuilder {
    private Spiral spiral;

    public SpiralBuilder() {
        super();
        spiral = new Spiral();
    }

    /*********SETTERS des éléments spécifiques a la spirale ***********/
    public void setStep(double s) {
        if (s == 0)
            s = 0.1;

        if (s < 0)
            s = -s;

        spiral.setStep(s);
    }

    public void setRadius(double r) {
        spiral.setRadius(r);
    }

    public void setTarget(Location location) {
        spiral.setTarget(location);
    }

    public void setStepAngle(double s) {
        spiral.setStepAngle(s);
    }

    public void setGrowthSpeed(double g){
        spiral.setGrowthSpeed(g);
    }

    @Override
    public AAnimation getAnimation() {
        return spiral;
    }
}
