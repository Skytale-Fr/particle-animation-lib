package fr.skytale.particleanimlib.image;

import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.util.Vector;

public class ImageBuilder extends AAnimationBuilder<Image> {

    public ImageBuilder() {
        super();
        animation = new Image();
        animation.setU(new Vector(1, 0, 0));
        animation.setV(new Vector(0, 1, 0));
        animation.setTicksDuration(60);
        animation.setShowFrequency(2);
        animation.setResetBeforeShow(true);
        animation.setStepAngleAlpha(0);
        animation.setAxisChangeFrequency(3);
        animation.setStepAngleAlphaChangeFactor(1);
        animation.setStepAngleAlphaChangeFrequency(3);
    }

    /*********SETTERS des éléments spécifiques au cercle ***********/
    public void setDirectorVectors(Vector u, Vector v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException("Director vectors should not be null");
        }
        u.normalize();
        v.normalize();

        animation.setU(u);
        animation.setV(v);
    }

    public void setAxis(Vector axis) {
        animation.setAxis(axis);
    }

    public void setStepAngleAlpha(double s) {
        animation.setStepAngleAlpha(s);
    }

    public void setTickDuration(int tickDuration) {
        animation.setTicksDuration(tickDuration);
    }

    public void setResetBeforeShow(boolean resetBeforeShow) {
        animation.setResetBeforeShow(resetBeforeShow);
    }

    public void setShowFrequency(int showFrequency) {
        animation.setShowFrequency(showFrequency);
    }

    public void setImageFileName(String imageFileName) {
        animation.setImageFileName(imageFileName);
    }

    public void setStepAngleAlphaChangeFrequency(int stepAngleAlphaChangeFrequency) {
        animation.setStepAngleAlphaChangeFrequency(stepAngleAlphaChangeFrequency);
    }

    public void setStepAngleAlphaChangeFactor(double stepAngleAlphaChangeFactor) {
        animation.setStepAngleAlphaChangeFactor(stepAngleAlphaChangeFactor);
    }

    public void setAxisChangeFrequency(int axisChangeFrequency) {
        animation.setAxisChangeFrequency(axisChangeFrequency);
    }


    @Override
    public Image getAnimation() {
        if (animation.getAxis() != null && animation.getStepAngleAlpha() == 0) {
            throw new IllegalArgumentException("The rotation animation should have a stepAngleAlpha");
        }
        return super.getAnimation();
    }
}
