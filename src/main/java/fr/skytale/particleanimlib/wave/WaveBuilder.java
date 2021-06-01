package fr.skytale.particleanimlib.wave;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class WaveBuilder extends AAnimationBuilder {
    private Wave wave;

    public WaveBuilder() {
        wave = new Wave();
    }

    /*********SETTERS des éléments spécifiques a la vague ***********/
    public void setRadius(double r) {
        wave.setRadius(r);
    }

    public void setMaxRadius(double r) {
        wave.setMaxRadius(r);
    }

    public void setStepAngle(double n) {
        wave.setStepAngle(n);
    }

    public void setStep(double s) {
        wave.setStep(s);
    }

    @Override
    public AAnimation getAnimation() {
        return wave;
    }
}
