package fr.skytale.particleanimlib.animation.image;

import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.util.Vector;

public class ImageBuilder extends AAnimationBuilder<Image> {

    public ImageBuilder() {
        super();
        animation.setU(new Vector(1, 0, 0));
        animation.setV(new Vector(0, 1, 0));
        animation.setStepAngleAlpha(0);
        animation.setAxisChangeFrequency(null);
        animation.setStepAngleAlphaChangeFactor(1);
        animation.setStepAngleAlphaChangeFrequency(null);
    }

    @Override
    protected Image initAnimation() {
        return new Image();
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


    @Override
    public Image getAnimation() {
        if (animation.getAxis() != null && animation.getStepAngleAlpha() == 0) {
            throw new IllegalArgumentException("The rotation animation should have a stepAngleAlpha");
        }

        return super.getAnimation();
    }
}
