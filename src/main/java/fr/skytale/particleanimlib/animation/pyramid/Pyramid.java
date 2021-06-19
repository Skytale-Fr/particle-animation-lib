package fr.skytale.particleanimlib.animation.pyramid;

import fr.skytale.particleanimlib.parent.AAnimation;
import org.bukkit.Location;

public class Pyramid extends AAnimation {
    private Location apexPoint;
    private Location basePointA;
    private Location basePointB;
    private Location basePointC;
    private double step;

    @Override
    public void show() {
        new PyramidTask(this);
    }

    /***********GETTERS & SETTERS***********/
    public Location getApexPoint() {
        return apexPoint;
    }

    public void setApexPoint(Location apexPoint) {
        this.apexPoint = apexPoint;
    }

    public Location getBasePointA() {
        return basePointA;
    }

    public void setBasePointA(Location basePointA) {
        this.basePointA = basePointA;
    }

    public Location getBasePointB() {
        return basePointB;
    }

    public void setBasePointB(Location basePointB) {
        this.basePointB = basePointB;
    }

    public Location getBasePointC() {
        return basePointC;
    }

    public void setBasePointC(Location basePointC) {
        this.basePointC = basePointC;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
