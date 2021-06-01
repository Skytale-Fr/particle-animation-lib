package fr.skytale.particleanimlib.spiralprojectile;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.Location;

public class SpiralProjectileBuilder extends AAnimationBuilder {
    private SpiralProjectile spiralProjectile;

    public SpiralProjectileBuilder() {
        spiralProjectile = new SpiralProjectile();
    }

    public void setTarget(Location location) {
        spiralProjectile.setTarget(location);
    }

    public void setStep(double s) {
        if (s == 0)
            s = 0.1;

        if (s < 0)
            s = -s;
        spiralProjectile.setStep(s);
    }

    public void setSpiral1(AAnimation spiral){
        spiralProjectile.setSpiral1(spiral);
    }

    public void setSpiral2(AAnimation spiral){
        spiralProjectile.setSpiral2(spiral);
    }

    @Override
    public AAnimation getAnimation() {
        return spiralProjectile;
    }
}
