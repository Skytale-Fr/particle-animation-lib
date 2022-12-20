package fr.skytale.particleanimlib.animation.animation.image;

import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class ImageBuilder extends AAnimationBuilder<Image, ImageTask> {

    public ImageBuilder() {
        super();
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
        return super.getAnimation();
    }

    /********* Image specific setters ***********/

    public void setImageFileName(String imageFileName) {
        animation.setImageFileName(imageFileName);
    }
}
