package fr.skytale.particleanimlib.testing.attributes;

import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;

public class AnimationLibPlayerData {
    private boolean showAnimationOnClick;
    private String animationSampleName;
    private String trailSampleName;

    public AnimationLibPlayerData(boolean showAnimationOnClick, String animationSampleName, String trailSampleName) {
        this.showAnimationOnClick = showAnimationOnClick;
        this.animationSampleName = animationSampleName;
        this.trailSampleName = trailSampleName;
    }

    public AnimationLibPlayerData() {
        this.showAnimationOnClick = ParticleAnimLibTest.DEFAULT_SHOW_ON_CLICK;
        this.animationSampleName = ParticleAnimLibTest.DEFAULT_ANIMATION_TYPE;
        this.trailSampleName = ParticleAnimLibTest.DEFAULT_TRAIL_TYPE;
    }

    public boolean isShowAnimationOnClick() {
        return showAnimationOnClick;
    }

    public void setShowAnimationOnClick(boolean showAnimationOnClick) {
        this.showAnimationOnClick = showAnimationOnClick;
    }

    public String getAnimationType() {
        return animationSampleName;
    }

    public void setAnimationType(String animationSample) {
        this.animationSampleName = animationSample;
    }

    public String getTrailSampleName() {
        return trailSampleName;
    }

    public void setTrailSampleName(String trailSampleName) {
        this.trailSampleName = trailSampleName;
    }
}
