package fr.skytale.particleanimlib.spiralprojectile;

import fr.skytale.particleanimlib.attributes.AnimationEndedCallback;
import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.Location;

public class SpiralProjectileBuilder extends AAnimationBuilder<SpiralProjectile> {

    public SpiralProjectileBuilder() {
        animation = new SpiralProjectile();
        animation.setStep(0.1);
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
    }

    public void setTarget(Location location) {
        animation.setTarget(location);
    }

    public void setStep(double s) {
        if (s == 0)
            throw new IllegalArgumentException("Step must not be equal to zero.");
        animation.setStep(s);
    }

    public void setSpiral1(AAnimation spiral){
        animation.setSpiral1(spiral);
    }

    public void setSpiral2(AAnimation spiral){
        animation.setSpiral2(spiral);
    }    public void setCallback(AnimationEndedCallback callback) {
        throw new IllegalArgumentException("Only primitive animations have callback functions.");
    }


    @Override
    public SpiralProjectile getAnimation() {
        if(animation.getSpiral1()==null || animation.getSpiral2()==null)
            throw new IllegalArgumentException("Both spirals has to be initialized.");

        if(animation.getTarget()==null)
            throw new IllegalArgumentException("Target has to be initialized.");
        return super.getAnimation();
    }
}
