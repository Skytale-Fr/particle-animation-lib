package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticlePointDefinition implements APointDefinition {

    private final ParticleTemplate particleTemplate;

    /**
     * Copy constructor
     * @param particlePointDefinition the particle point definition to copy
     */
    public ParticlePointDefinition(ParticlePointDefinition particlePointDefinition) {
        this.particleTemplate = new ParticleTemplate(particlePointDefinition.getParticleTemplate());
    }

    /**
     * Builds a particle point definition
     * @param particleTemplate the particle template to use
     */
    public ParticlePointDefinition(ParticleTemplate particleTemplate) {
        this.particleTemplate = particleTemplate;
    }

    /**
     * Retrieves the particle template
     * @return the particle template
     */
    public ParticleTemplate getParticleTemplate() {
        return particleTemplate;
    }

    /**
     * Displays the particle at the given point location
     * @param pointLocation                         The location of the point to show
     * @param animation                             The animation whose point is displayed
     * @param task                                  The animation task that calls this method
     * @param fromAnimCenterToPoint                 A vector that goes:
     *                                              - From the parent animation center (its current iteration base location)
     *                                              - To the point to show
     * @param fromPreviousToCurrentAnimBaseLocation A vector that goes:
     *                                              - From the parent animation previous base location (of the previous iteration)
     *                                              - To the parent animation current base location (of the current iteration)
     */
    @Override
    public void show(Location pointLocation, AAnimation animation, AAnimationTask<?> task, Vector fromAnimCenterToPoint, Vector fromPreviousToCurrentAnimBaseLocation) {
        particleTemplate.getParticleBuilder(pointLocation).display(animation.getViewers().getPlayers(pointLocation));
    }

    /**
     * Clone the particle point definition
     * @return a cloned particle point definition
     */
    @Override
    public ParticlePointDefinition copy() {
        return new ParticlePointDefinition(this);
    }

}
