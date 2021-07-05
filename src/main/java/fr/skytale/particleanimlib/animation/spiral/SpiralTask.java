package fr.skytale.particleanimlib.animation.spiral;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.attributes.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class SpiralTask extends AAnimationTask<Spiral> {

    private final boolean followEntity;

    private Vector entityRelativeLocation;
    private Location absoluteLocation;
    private double spiralParticlesChangingAngle;
    private Set<ParticleBuilder> currentIterationParticles;
    private final LinkedList<Set<ParticleBuilder>> trailParticlesPerIteration;

    public SpiralTask(Spiral spiral) {
        super(spiral);
        followEntity = animation.getDirection().getType() == AnimationDirection.Type.MOVE_VECTOR && animation.getPosition().getType() == APosition.Type.ENTITY;
        spiralParticlesChangingAngle = 0;
        trailParticlesPerIteration = new LinkedList<>();

        startTask();
    }

    @Override
    public void show(Location iterationBaseLocation) {

        // --- Stop if max duration reached
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        // --- Computing the current animation location
        Location animationCurrentLocation;

        if (followEntity) {
            /* If the spiral follows an entity, its current location (animationCurrentLocation) will be :
                animation.getPosition().getMovingEntity().getLocation()
                          .add(animation.getPosition().getRelativeLocation())
                          .add(entityRelativeLocation);
                entityRelativeLocation will evolve over time.
            */
            if (entityRelativeLocation == null) {
                //First iteration : initialization
                entityRelativeLocation = new Vector(0, 0, 0);

            }
            animationCurrentLocation = iterationBaseLocation.add(entityRelativeLocation);
        } else {
            //If the spiral does not follow an entity, it's current location will depend of the previous show location
            if (absoluteLocation == null) {
                //First iteration : initialization
                absoluteLocation = iterationBaseLocation;
            }
            animationCurrentLocation = absoluteLocation;
        }

        // --- Retrieves move Vector
        AnimationDirection.MoveData nextMoveData = animation.getDirection().getMoveVector(animationCurrentLocation, iterationCount);

        // --- Handling errors (if target can't be reached anymore)
        if (nextMoveData.hasError)
            stopAnimation(false);

        // --- Display trailing particles
        Integer nbTrailingParticles = animation.getNbTrailingParticles().getCurrentValue(iterationCount);
        displayTrail(nbTrailingParticles);

        // --- Reinitialize the Set that contains this iteration particles
        currentIterationParticles = new HashSet<>();

        // --- show the central particle (and add it to the Set to be able to show the particle again within the spiral trail during a future iteration)
        displayParticle(animationCurrentLocation, true);


        //Calculating radiusVector
        Vector directorVector = nextMoveData.move.clone().normalize();
        double radius = animation.getRadius().getCurrentValue(iterationCount);
        Vector radiusVector = computeRadiusVector(directorVector, radius);

        //Calculating each spiral particle locations
        Location firstSpiralParticleLocationBeforeRotation = animationCurrentLocation.clone().add(radiusVector);

        Integer nbSpirals = animation.getNbSpiral().getCurrentValue(iterationCount);
        double spiralParticlesGapAngle = 2 * Math.PI / nbSpirals;
        for (int i = 0; i < nbSpirals; i++) {
            double currentSpiralParticleAngle = i * spiralParticlesGapAngle + spiralParticlesChangingAngle;
            Location currentSpiralParticleLocation = rotateAroundAxis(firstSpiralParticleLocationBeforeRotation, directorVector, animationCurrentLocation, currentSpiralParticleAngle);
            // --- show each spiral particle (and add it to the Set to be able to show the particle again within the spiral trail during a future iteration)
            displayParticle(currentSpiralParticleLocation, false);
        }


        // --- Stop if target reached
        if (nextMoveData.willReachTarget) {
            stopAnimation();
            return;
        }

        // --- Prepare the next iteration
        spiralParticlesChangingAngle += animation.getAngleBetweenEachPoint().getCurrentValue(iterationCount);

        if (followEntity) {
            entityRelativeLocation.add(nextMoveData.move);
        } else {
            absoluteLocation.add(nextMoveData.move);
        }

        // We add the new trail particles at the beginning of the list. So bigger index = older
        trailParticlesPerIteration.add(0, currentIterationParticles);
    }

    private void displayTrail(Integer nbTrailingParticles) {

        /* Usage example :
            trailParticlesPerIteration = (newest) 0 , 1 , 2 , 3 , 4 (oldest)
            trailParticlesPerIteration.size() = 5
            nbTrailingParticles = 3
            ==> Show 0 , 1 , 2
            ==> Remove 3 , 4 , 5
         */
        Iterator<Set<ParticleBuilder>> it = trailParticlesPerIteration.iterator();
        int i = 0;
        while (it.hasNext()) {
            Set<ParticleBuilder> iterationParticles = it.next();
            if (i < nbTrailingParticles) {
                iterationParticles.forEach(ParticleBuilder::display);
            } else {
                it.remove();
            }
            i++;
        }
    }

    private void displayParticle(Location particleLocation, boolean isCentral) {
        ParticleTemplate template;
        if (isCentral) {
            template = animation.getCentralParticle();
            if (template == null) return;
        } else {
            template = animation.getMainParticle();
        }
        ParticleBuilder particleBuilder = template.getParticleBuilder(particleLocation.clone());

        // Add the built particle to the currentIterationParticles Set to be able to show the particle again within the spiral trail during a future iteration
        currentIterationParticles.add(particleBuilder);

        // Show the particle
        particleBuilder.display();
    }
}
