package fr.skytale.particleanimlib.spiralprojectile;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.Location;

public class SpiralProjectileBuilder extends AAnimationBuilder<SpiralProjectile> {

    public SpiralProjectileBuilder() {
        animation = new SpiralProjectile();
        animation.setStep(0.1);
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
        if(spiral==null)
            throw new IllegalArgumentException("First spiral has to be initialized");
        animation.setSpiral1(spiral);
    }

    public void setSpiral2(AAnimation spiral){
        if(spiral==null)
            throw new IllegalArgumentException("Second spiral has to be initialized");
        animation.setSpiral2(spiral);
    }

    @Override
    public SpiralProjectile getAnimation() {
        return animation;
    }
}
