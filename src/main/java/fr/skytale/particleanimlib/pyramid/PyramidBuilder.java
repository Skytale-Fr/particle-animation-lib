package fr.skytale.particleanimlib.pyramid;

import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.Location;

public class PyramidBuilder extends AAnimationBuilder<Pyramid> {

    public PyramidBuilder() {
        super();
        animation = new Pyramid();
        animation.setStep(0.3);
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
    }

    /*********SETTERS des éléments spécifiques a la pyramide ***********/

    public void setApexPoint(Location apex) {
        if (apex == null)
            throw new IllegalArgumentException("Apex point of pyramid has to be defined.");
        animation.setApexPoint(apex);
    }

    public void setBasePointA(Location baseA) {
        if (baseA == null)
            throw new IllegalArgumentException("Point A of the pyramid's base has to be defined.");
        animation.setBasePointA(baseA);
    }

    public void setBasePointB(Location baseB) {
        if (baseB == null)
            throw new IllegalArgumentException("Point B of the pyramid's base has to be defined.");
        animation.setBasePointB(baseB);
    }

    public void setBasePointC(Location baseC) {
        if (baseC == null)
            throw new IllegalArgumentException("Point C of the pyramid's base has to be defined.");
        animation.setBasePointC(baseC);
    }

    public void setStep(double step) {
        if (step <= 0)
            throw new IllegalArgumentException("The step value has to be strictly positive.");
    }

    @Override
    public Pyramid getAnimation() {
        return super.getAnimation();
    }
}
