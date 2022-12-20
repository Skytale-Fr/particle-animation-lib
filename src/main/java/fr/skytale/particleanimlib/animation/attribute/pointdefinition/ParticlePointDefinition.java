package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticlePointDefinition implements APointDefinition {

    private final ParticleTemplate particleTemplate;

    public ParticlePointDefinition(ParticlePointDefinition particlePointDefinition) {
        this.particleTemplate = new ParticleTemplate(particlePointDefinition.getParticleTemplate());
    }

    public ParticlePointDefinition(ParticleTemplate particleTemplate) {
        this.particleTemplate = particleTemplate;
    }

    public ParticleTemplate getParticleTemplate() {
        return particleTemplate;
    }

    @Override
    public void show(Location pointLocation, AAnimation animation, AAnimationTask<?> task, Vector fromAnimCenterToPoint, Vector fromPreviousToCurrentAnimBaseLocation) {
        particleTemplate.getParticleBuilder(pointLocation).display(animation.getViewers().getPlayers(pointLocation));
    }

    @Override
    public ParticlePointDefinition copy() {
        return new ParticlePointDefinition(this);
    }

}
