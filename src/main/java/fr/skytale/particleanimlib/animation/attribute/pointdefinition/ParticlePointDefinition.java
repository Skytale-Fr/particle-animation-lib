package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
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
    public void show(Location loc) {
        particleTemplate.getParticleBuilder(loc).display();
    }

    @Override
    public void show(Location loc, Vector fromCenterToPoint) {
        show(loc);
    }

    @Override
    public ParticlePointDefinition clone() {
        ParticlePointDefinition obj = (ParticlePointDefinition) super.clone();
        obj.particleTemplate = new ParticleTemplate(particleTemplate);
        return obj;
    }
}
