package fr.skytale.particleanimlib.parent;

import fr.skytale.particleanimlib.attributes.AnimationType;
import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public abstract class AAnimationBuilder {
    protected AAnimation animation;

    public AAnimationBuilder() {
    }

    /*********SETTERS des éléments généraux d'une animation ***********/

    public void setAnimation(AAnimation animation) {
        this.animation = animation;
    }

    public void setAnimationType(AnimationType animationType) {
        animation.setAnimationType(animationType);
    }

    public void setLocation(Location location) {
        animation.setLocation(location);
    }

    public void setNpc(IMovingEntity movingEntity) {
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

    public abstract AAnimation getAnimation();
}
