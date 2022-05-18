package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticlePointDefinition extends APointDefinition {

    private ParticleTemplate particleTemplate;

    public ParticlePointDefinition(ParticleTemplate particleTemplate) {
        super(ShowMethodParameters.LOCATION, false);
        this.particleTemplate = particleTemplate;
    }

    public ParticleTemplate getParticleTemplate() {
        return particleTemplate;
    }

    @Override
    public void show(AAnimation animation, Location loc, AAnimationTask<?> parentTask) {
        particleTemplate.getParticleBuilder(loc).display(animation.getViewers().getPlayers(loc));
    }

    @Override
    public void show(AAnimation animation, Location loc, Vector fromCenterToPoint, AAnimationTask<?> parentTask) {
        show(animation, loc, parentTask);
    }

    @Override
    public ParticlePointDefinition clone() {
        ParticlePointDefinition obj = (ParticlePointDefinition) super.clone();
        obj.particleTemplate = new ParticleTemplate(particleTemplate);
        return obj;
    }

}
