package fr.skytale.particleanimlib.testing.attributes;

import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;

public class AnimationLibPlayerData {
    private boolean showAnimationOnClick;
    private AnimationSample animationSample;

    public AnimationLibPlayerData(boolean showAnimationOnClick, AnimationSample animationSample) {
        this.showAnimationOnClick = showAnimationOnClick;
        this.animationSample = animationSample;
    }

    public AnimationLibPlayerData() {
        this.showAnimationOnClick = ParticleAnimLibTest.DEFAULT_SHOW_ON_CLICK;
        this.animationSample = ParticleAnimLibTest.DEFAULT_ANIMATION_TYPE;
    }

    public boolean isShowAnimationOnClick() {
        return showAnimationOnClick;
    }

    public void setShowAnimationOnClick(boolean showAnimationOnClick) {
        this.showAnimationOnClick = showAnimationOnClick;
    }

    public AnimationSample getAnimationType() {
        return animationSample;
    }

    public void setAnimationType(AnimationSample animationSample) {
        this.animationSample = animationSample;
    }
}
