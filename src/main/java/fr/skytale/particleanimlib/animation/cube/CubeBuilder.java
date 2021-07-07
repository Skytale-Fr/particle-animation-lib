package fr.skytale.particleanimlib.animation.cube;


import fr.skytale.particleanimlib.animation.parent.AAnimationBuilder;

public class CubeBuilder extends AAnimationBuilder<Cube> {

    public CubeBuilder() {
        super();
        animation.setSideLength(2);
        animation.setStep(0.5);
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
    }

    @Override
    protected Cube initAnimation() {
        return new Cube();
    }

    /*********SETTERS des éléments spécifiques au cube ***********/

    public void setSideLength(double sideLength) {
        if (sideLength <= 0) {
            throw new IllegalArgumentException("The length of the sides must be strictly positive");
        }

        animation.setSideLength(sideLength);
    }

    public void setStep(double step) {
        if (step <= 0)
            throw new IllegalArgumentException("Step must be strictly positive");
        animation.setStep(step);
    }

    @Override
    public Cube getAnimation() {
        return super.getAnimation();
    }
}
