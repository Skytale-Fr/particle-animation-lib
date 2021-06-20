package fr.skytale.particleanimlib.parent;

import fr.skytale.particleanimlib.attributes.AnimationEndedCallback;
import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public abstract class AAnimationBuilder<T extends AAnimation> {
    protected T animation;

    public AAnimationBuilder() {
        animation = initAnimation();
        animation.setMoveStepVector(null);
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
    }

    protected abstract T initAnimation();

    /*********SETTERS des éléments généraux d'une animation ***********/

    public void setLocation(Location location) {
        animation.setLocation(location);
    }

    public void setMovingEntity(Entity movingEntity) {
        animation.setMovingEntity(movingEntity);
    }

    public void setRelativeLocation(Vector relativeLocation) {
        animation.setRelativeLocation(relativeLocation);
    }

    public void setMainParticle(ParticleTemplate mainParticle) {
        animation.setMainParticle(mainParticle);
    }

    public void setJavaPlugin(JavaPlugin javaPlugin) {
        animation.setPlugin(javaPlugin);
    }

    public void setTicksDuration(int ticksDuration) {
        if (ticksDuration <= 0)
            throw new IllegalArgumentException("ticks duration should be positive");
        animation.setTicksDuration(ticksDuration);
    }

    public void setShowFrequency(int showFrequency) {
        if (showFrequency < 0)
            throw new IllegalArgumentException("showFrequency should be positive or equal to 0");
        animation.setShowFrequency(showFrequency);
    }

    public void setCallback(AnimationEndedCallback callback) {
        animation.callback = callback;
    }

    public void setMoveStepVector(Vector moveStepVector) {
        animation.setMoveStepVector(moveStepVector);
    }

    public void setMoveFrequency(Integer moveFrequency) {
        animation.setMoveFrequency(moveFrequency);
    }

    public T getAnimation() {
        boolean hasMovingEntity = animation.getMovingEntity() != null;
        if (animation.getLocation() != null && hasMovingEntity) {
            throw new IllegalArgumentException("Fixed location and movingEntity should not be both defined");
        }
        if (hasMovingEntity && animation.getRelativeLocation() == null) {
            animation.setRelativeLocation(new Vector(0, 0, 0));
        }
        if (animation.getMainParticle() == null) {
            throw new IllegalArgumentException("Main particle should not be null");
        }
        if (animation.getPlugin() == null) {
            throw new IllegalArgumentException("The plugin should be defined");
        }

        if (animation.getMoveStepVector() != null && animation.getMoveFrequency() == null)
            throw new IllegalArgumentException("If move vector is defined then move frequency must be strictly positive.");

        return (T)animation.clone();
    };
}
