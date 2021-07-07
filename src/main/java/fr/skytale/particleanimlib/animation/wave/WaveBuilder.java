package fr.skytale.particleanimlib.animation.wave;

import fr.skytale.particleanimlib.animation.attributes.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.parent.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.ARoundAnimation;

public class WaveBuilder extends AAnimationBuilder<Wave> {

    public WaveBuilder() {
        animation.setRadius(1.0);
        animation.setMaxRadius(20);
        animation.setNbPoints((int) animation.getRadius() * 20);
        animation.setStep(0.3);
        animation.setStepAngle(2 * Math.PI / animation.getNbPoints());
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
    }

    @Override
    protected Wave initAnimation() {
        return new Wave();
    }

    /*********SETTERS des éléments spécifiques a la vague ***********/
    public void setRadius(double r) {
        if (animation.getRadius() <= 0) {
            throw new IllegalArgumentException("Radius should be positive");
        }
        animation.setRadius(r);
    }

    public void setMaxRadius(double r) {
        if (animation.getMaxRadius() <= 0) {
            throw new IllegalArgumentException("Maxradius should be positive");
        }
        animation.setMaxRadius(r);
    }

    public void setNbPoints(int nbPoints) {
        if (nbPoints <= 0) {
            throw new IllegalArgumentException("The number of point should be positive");
        }
        animation.setNbPoints(nbPoints);
    }

    public void setStep(double s) {
        if (s == 0)
            throw new IllegalArgumentException("Step must not be equal to zero.");
        animation.setStep(s);
    }

    public void setAnim(ARoundAnimation subAnimation) {
        animation.setCircleAnim(subAnimation);
    }

    public void setCallback(AnimationEndedCallback callback) {
        throw new IllegalArgumentException("Only primitive animations have callback functions.");
    }

    @Override
    public Wave getAnimation() {
        if (animation.getRadius() >= animation.getMaxRadius())
            throw new IllegalArgumentException("MaxRadius must be greater than radius.");

        if (animation.getCircleAnim() == null)
            throw new IllegalArgumentException("Image animation has to be defined.");

        return super.getAnimation();
    }
}
