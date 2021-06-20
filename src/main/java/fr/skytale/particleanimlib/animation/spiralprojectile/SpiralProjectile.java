package fr.skytale.particleanimlib.animation.spiralprojectile;


import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SpiralProjectile extends ARoundAnimation {
    private Vector target;
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
    public Vector getTarget() {
        return target;
    }

    public void setTarget(Vector target) {
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

    @Override
    public Object clone() {
        SpiralProjectile obj = (SpiralProjectile) super.clone();
        obj.target = target.clone();
        obj.spiral1 = (AAnimation) spiral1.clone();
        obj.spiral2 = (AAnimation) spiral2.clone();
        return obj;
    }
}
