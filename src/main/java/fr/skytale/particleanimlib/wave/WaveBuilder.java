package fr.skytale.particleanimlib.wave;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class WaveBuilder extends AAnimationBuilder<Wave> {

    public WaveBuilder() {
        animation = new Wave();
        animation.setRadius(1.0);
        animation.setMaxRadius(20);
        animation.setNbPoints((int) animation.getRadius() * 20);
        animation.setStep(0.3);
        animation.setStepAngle(2 * Math.PI / animation.getNbPoints());
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

    public void setCircle(AAnimation subAnimation){
        animation.setCircleAnim(subAnimation);
    }


    @Override
    public Wave getAnimation() {
        if(animation.getRadius()>=animation.getMaxRadius())
            throw new IllegalArgumentException("MaxRadius must be greater than radius.");

        if(animation.getCircleAnim()==null)
            throw new IllegalArgumentException("Image animation has to be defined.");

        return super.getAnimation();
    }
}
