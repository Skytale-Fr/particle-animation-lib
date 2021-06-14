package fr.skytale.particleanimlib.spiralprojectile;


import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;

public class SpiralProjectile extends ARoundAnimation {
    private Location target;
    private AAnimation spiral1;
    private AAnimation spiral2;
    private double step;

    public SpiralProjectile() {
    }

    @Override
    public void show() {
        spiral1.show();
        spiral2.show();
        new SpiralProjectileTask(this);
    }

    /***********GETTERS & SETTERS***********/
    public Location getTarget() {
        return target;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public AAnimation getSpiral1() {
        return spiral1;
    }

    public void setSpiral1(AAnimation spiral1) {
        this.spiral1 = spiral1;
    }

    public AAnimation getSpiral2() {
        return spiral2;
    }

    public void setSpiral2(AAnimation spiral2) {
        this.spiral2 = spiral2;
    }
}
