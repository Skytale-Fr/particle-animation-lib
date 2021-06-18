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
        animation.setStepAngleAlpha(0);
        animation.setAxisChangeFrequency(null);
        animation.setStepAngleAlphaChangeFactor(1);
        animation.setStepAngleAlphaChangeFrequency(null);
        animation.setMoveVector(null);
    }

    /*********SETTERS des éléments spécifiques au cercle ***********/
    public void setDirectorVectors(Vector u, Vector v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException("Director vectors should not be null");
        }

        animation.setU(u);
        animation.setV(v.clone().multiply(-1));
    }

    public void setAxis(Vector axis) {
        animation.setAxis(axis);
    }

    public void setStepAngleAlpha(double s) {
        animation.setStepAngleAlpha(s);
    }

    public void setImageFileName(String imageFileName) {
        animation.setImageFileName(imageFileName);
    }

    public void setStepAngleAlphaChangeFrequency(Integer stepAngleAlphaChangeFrequency) {
        animation.setStepAngleAlphaChangeFrequency(stepAngleAlphaChangeFrequency);
    }

    public void setStepAngleAlphaChangeFactor(double stepAngleAlphaChangeFactor) {
        animation.setStepAngleAlphaChangeFactor(stepAngleAlphaChangeFactor);
    }

    public void setAxisChangeFrequency(Integer axisChangeFrequency) {
        animation.setAxisChangeFrequency(axisChangeFrequency);
    }

    public void setMoveVector(Vector moveVector) {
        animation.setMoveVector(moveVector);
    }

    public void setMoveFrequency(Integer moveFrequency) {
        animation.setMoveFrequency(moveFrequency);
    }

    public void setMoveStep(Double moveStep) {
        animation.setMoveStep(moveStep);
    }


    @Override
    public Image getAnimation() {
        if (animation.getAxis() != null && animation.getStepAngleAlpha() == 0) {
            throw new IllegalArgumentException("The rotation animation should have a stepAngleAlpha");
        }

        if (animation.getMoveVector() != null && animation.getMoveFrequency() == null)
            throw new IllegalArgumentException("If move vector is defined then move frequency must be strictly positive.");

        if (animation.getMoveVector() != null && animation.getMoveStep() <= 0)
            throw new IllegalArgumentException("If move vector is defined then move step must be strictly positive.");

        return super.getAnimation();
    }
}
