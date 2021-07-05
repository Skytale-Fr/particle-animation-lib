package fr.skytale.particleanimlib.animation.wave;

import Jama.Matrix;
import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class WaveTask extends AAnimationTask<Wave> {

    private final Matrix matrixMInverse;
    private final double intermediateCachedResult;

    private double currentRadius;

    public WaveTask(Wave wave) {
        super(wave);

        currentRadius = animation.getRadiusStart();

        Vector directorU = animation.getU().clone().normalize();
        Vector directorV = animation.getV().clone().normalize();
        Vector directorW = directorU.clone().crossProduct(directorV).normalize();


        double[][] matrixMArray = {
                {directorU.getX(), directorW.getX(), directorV.getX()},
                {directorU.getY(), directorW.getY(), directorV.getY()},
                {directorU.getZ(), directorW.getZ(), directorV.getZ()}
        };

        Matrix matrixM = new Matrix(matrixMArray);

        this.matrixMInverse = matrixM.inverse();

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


        // the vectical vector in UVW geometric lair
        double[][] relativeLocationInUVW = {
                {0},
                {(2 * Math.exp(intermediateCachedResult * currentRadius) * Math.sin(currentRadius)) + 1},
                {0}
        };

        // Passing to the XYZ geometric lair
        Matrix relativeLocationInUVWMatrix = new Matrix(relativeLocationInUVW);

        Matrix relativeLocationInXYZMatrix = this.matrixMInverse.times(relativeLocationInUVWMatrix);

        Vector relativeLocationInXYZ = new Vector(
                relativeLocationInXYZMatrix.get(0, 0),
                relativeLocationInXYZMatrix.get(1, 0),
                relativeLocationInXYZMatrix.get(2, 0));

        // Applying this to the iteration base Location
        iterationBaseLocation.add(relativeLocationInXYZ);


        double stepAngle = animation.getAngleBetweenEachPoint().getCurrentValue(iterationCount);
        int nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);

        ParticleTemplate particleTemplate = animation.getMainParticle();

        // Tracing circle
        for (int pointIndex = 0; pointIndex < nbPoints; pointIndex ++) {
            double theta = pointIndex * stepAngle;
            double cosThetaDotRadius = currentRadius * Math.cos(theta);
            double sinThetaDotRadius = currentRadius * Math.sin(theta);

            double x = iterationBaseLocation.getX() + (animation.getU().getX() * cosThetaDotRadius) + (animation.getV().getX() * sinThetaDotRadius);
            double y = iterationBaseLocation.getY() + (animation.getU().getY() * cosThetaDotRadius) + (animation.getV().getY() * sinThetaDotRadius);
            double z = iterationBaseLocation.getZ() + (animation.getU().getZ() * cosThetaDotRadius) + (animation.getV().getZ() * sinThetaDotRadius);

            Location particleLocation = new Location(iterationBaseLocation.getWorld(), x, y, z);

            particleTemplate.getParticleBuilder(particleLocation).display();
        }

        currentRadius += animation.getRadiusStep().getCurrentValue(iterationCount);
    }
}
