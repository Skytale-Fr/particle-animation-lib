package fr.skytale.particleanimlib.animation.animation.wave;

import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;

public class WaveTask extends AAnimationTask<Wave> {

    private final PARotation rotation;
    private final double intermediateCachedResult;

    private double currentRadius;

    private Vector currentVerticalPositionInXYZRef;
    private Vector currentVerticalPositionInUVWRef;
    private double stepAngle;
    private int nbPoints;

    public WaveTask(Wave wave) {
        super(wave);

        this.rotation = new PARotation(animation.getU(), animation.getV());

        this.currentRadius = animation.getRadiusStart();

        this.intermediateCachedResult = -0.1 * 39 / (animation.getRadiusMax() - animation.getRadiusStart());

        startTask();
    }

    private void initWaveData() {
    }

    @Override
    public void show(Location iterationBaseLocation) {
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        if (currentRadius >= animation.getRadiusMax()) {
            stopAnimation();
            return;
        }

        // Computing the vertical coordinate of the wave's current circle
        currentVerticalPositionInXYZRef = new Vector(
                0,
                ((2 * Math.exp(intermediateCachedResult * currentRadius) * Math.sin(currentRadius)) + 1) * (animation.getPositiveHeight() ? 1 : - 1),
                0);

        // Passing to the XYZ geometric lair
        currentVerticalPositionInUVWRef = rotation.rotateVector(currentVerticalPositionInXYZRef);

        // Applying this to the iteration base Location
        iterationBaseLocation.add(currentVerticalPositionInUVWRef);


        stepAngle = animation.getAngleBetweenEachPoint().getCurrentValue(iterationCount);
        nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);

        ParticleTemplate particleTemplate = animation.getMainParticle();

        final Collection<? extends Player> players = animation.getViewers().getPlayers(iterationBaseLocation);
        
        // Tracing circle
        for (int pointIndex = 0; pointIndex < nbPoints; pointIndex++) {
            double theta = pointIndex * stepAngle;
            double cosThetaDotRadius = currentRadius * Math.cos(theta);
            double sinThetaDotRadius = currentRadius * Math.sin(theta);

            double x = iterationBaseLocation.getX() + (animation.getU().getX() * cosThetaDotRadius) + (animation.getV().getX() * sinThetaDotRadius);
            double y = iterationBaseLocation.getY() + (animation.getU().getY() * cosThetaDotRadius) + (animation.getV().getY() * sinThetaDotRadius);
            double z = iterationBaseLocation.getZ() + (animation.getU().getZ() * cosThetaDotRadius) + (animation.getV().getZ() * sinThetaDotRadius);

            Location particleLocation = new Location(iterationBaseLocation.getWorld(), x, y, z);

            particleTemplate.getParticleBuilder(particleLocation).display(players);
        }

        currentRadius += animation.getRadiusStep().getCurrentValue(iterationCount);
    }

    public double getCurrentRadius() {
        return currentRadius;
    }

    public Vector getCurrentVerticalPositionInXYZRef() {
        return currentVerticalPositionInXYZRef;
    }

    public Vector getCurrentVerticalPositionInUVWRef() {
        return currentVerticalPositionInUVWRef;
    }

    public double getCurrentStepAngle() {
        return stepAngle;
    }

    public int getCurrentNbPoints() {
        return nbPoints;
    }

}
