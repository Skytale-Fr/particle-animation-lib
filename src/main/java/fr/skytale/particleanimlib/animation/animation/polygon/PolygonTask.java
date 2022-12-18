package fr.skytale.particleanimlib.animation.animation.polygon;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PolygonTask extends AAnimationTask<Polygon> {

    Vector currentU, currentV;

    private double radius;
    private int nbVertices;
    private double stepAngle;
    private double distanceBetweenParticles;

    private Vector rotationAxis;
    private double rotationAngleAlpha;

    public PolygonTask(Polygon polygon) {
        super(polygon);
        currentU = animation.getU();
        currentV = animation.getV();

        startTask();
    }

    @Override
    public void show(Location iterationBaseLocation) {
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        radius = animation.getDistanceFromCenterToVertices().getCurrentValue(iterationCount);
        nbVertices = animation.getNbVertices().getCurrentValue(iterationCount);
        stepAngle = 2 * Math.PI / nbVertices;

        ParticleTemplate particleTemplate = animation.getMainParticle();

        List<Location> vertices = new ArrayList<>();

        for (int pointIndex = 0; pointIndex < nbVertices; pointIndex++) {
            double theta = pointIndex * stepAngle;

            double x = iterationBaseLocation.getX() + (currentU.getX() * radius * Math.cos(theta)) + (currentV.getX() * radius * Math.sin(theta));
            double y = iterationBaseLocation.getY() + (currentU.getY() * radius * Math.cos(theta)) + (currentV.getY() * radius * Math.sin(theta));
            double z = iterationBaseLocation.getZ() + (currentU.getZ() * radius * Math.cos(theta)) + (currentV.getZ() * radius * Math.sin(theta));

            Location particleLocation = new Location(iterationBaseLocation.getWorld(), x, y, z);

            vertices.add(particleLocation);
        }
        distanceBetweenParticles = animation.getDistanceBetweenPoints().getCurrentValue(iterationCount);

        //Link each vertices
        getLinePoints(vertices.get(0), vertices.get(vertices.size() - 1), distanceBetweenParticles);

        for (int i = 0; i < vertices.size() - 1; i++) {
            getLinePoints(vertices.get(i), vertices.get(i + 1), distanceBetweenParticles);
        }

        if (animation.getRotationAxis() != null) {
            rotationAxis = animation.getRotationAxis().getCurrentValue(iterationCount);
            rotationAngleAlpha = animation.getRotationAngleAlpha().getCurrentValue(iterationCount);

            currentU = new RotatableVector(currentU).rotateAroundAxis(rotationAxis, rotationAngleAlpha);
            currentV = new RotatableVector(currentV).rotateAroundAxis(rotationAxis, rotationAngleAlpha);
        }
    }

    public Vector getCurrentU() {
        return currentU;
    }

    public Vector getCurrentV() {
        return currentV;
    }

    public double getCurrentRadius() {
        return radius;
    }

    public int getCurrentNbVertices() {
        return nbVertices;
    }

    public double getCurrentStepAngle() {
        return stepAngle;
    }

    public double getCurrentDistanceBetweenParticles() {
        return distanceBetweenParticles;
    }

    public Vector getCurrentRotationAxis() {
        return rotationAxis;
    }

    public double getCurrentRotationAngleAlpha() {
        return rotationAngleAlpha;
    }

}
