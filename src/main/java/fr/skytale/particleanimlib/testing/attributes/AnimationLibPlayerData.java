package fr.skytale.particleanimlib.testing.attributes;

import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;

public class AnimationLibPlayerData {
    private boolean showAnimationOnClick;
    private AnimationType animationType;

    public AnimationLibPlayerData(boolean showAnimationOnClick, AnimationType animationType) {
        this.showAnimationOnClick = showAnimationOnClick;
        this.animationType = animationType;
    }

    public AnimationLibPlayerData() {
        this.showAnimationOnClick = ParticleAnimLibTest.DEFAULT_SHOW_ON_CLICK;
        this.animationType = ParticleAnimLibTest.DEFAULT_ANIMATION_TYPE;
    }

    public boolean isShowAnimationOnClick() {
        return showAnimationOnClick;
    }

    public void setShowAnimationOnClick(boolean showAnimationOnClick) {
        this.showAnimationOnClick = showAnimationOnClick;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }
}
