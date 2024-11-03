package fr.skytale.particleanimlib.testing.attribute;

import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.trail.attribute.TrailPreset;

public class AnimationLibPlayerData {

    public static final AnimationPreset DEFAULT_ANIMATION_TYPE = AnimationPreset.CIRCUIT_PULSE_3D;
    public static final TrailPreset DEFAULT_TRAIL_TYPE = TrailPreset.CIRCLE_MOVING_UP;
    public static final boolean DEFAULT_SHOW_ON_CLICK = false;

    private boolean showAnimationOnClick;
    private String animationSampleName;
    private String trailSampleName;

    public AnimationLibPlayerData() {
        this.showAnimationOnClick = DEFAULT_SHOW_ON_CLICK;
        this.animationSampleName = DEFAULT_ANIMATION_TYPE.name();
        this.trailSampleName = DEFAULT_TRAIL_TYPE.name();
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
