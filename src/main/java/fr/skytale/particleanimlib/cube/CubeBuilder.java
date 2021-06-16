package fr.skytale.particleanimlib.cube;


import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class CubeBuilder extends AAnimationBuilder<Cube> {

    public CubeBuilder() {
        super();
        animation = new Cube();
        animation.setSideLength(2);
        animation.setStep(0.5);
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
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
