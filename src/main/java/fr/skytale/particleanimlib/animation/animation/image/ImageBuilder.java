package fr.skytale.particleanimlib.animation.animation.image;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class ImageBuilder extends AAnimationBuilder<Image, ImageTask> {

    public ImageBuilder() {
        super();
        animation.setScale(new Constant<>(0.2f));
    }

    @Override
    protected Image initAnimation() {
        return new Image();
    }

    @Override
    public Image getAnimation() {
        if (animation.getImageFileName() == null) {
            throw new IllegalArgumentException("imageFileName should be defined");
        }
        checkPositiveAndNotNull(animation.getScale(), "The scale must be positive", false);
        return super.getAnimation();
    }

    /********* Image specific setters ***********/

    /**
     * Defines the image file name (should be in the plugin's images directory)
     * @param imageFileName the image file name (with its extension)
     */
    public void setImageFileName(String imageFileName) {
        animation.setImageFileName(imageFileName);
    }

    /**
     * Defines the scale of the image
     * @param scale the scale of the image
     */
    public void setScale(float scale) {
        animation.setScale(new Constant<>(scale));
    }

    /**
     * Defines the scale of the image
     * @param scale the scale of the image
     */
    public void setScale(IVariable<Float> scale) {
        animation.setScale(scale);
        checkPositiveAndNotNull(animation.getScale(), "The scale must be positive", false);
    }
}
