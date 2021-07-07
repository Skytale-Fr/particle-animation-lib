package fr.skytale.particleanimlib.animation.animation.pyramid;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PyramidTask extends AAnimationTask<Pyramid> {

    private Vector fromCenterToApex = null;
    private List<Vector> fromCenterToBaseApexList;
    private final boolean baseDataAreVariable;

    public PyramidTask(Pyramid pyramid) {
        super(pyramid);
        this.baseDataAreVariable = !animation.getNbBaseApex().isConstant() || !animation.getDistanceToAnyBaseApex().isConstant()|| !animation.getFromCenterToApex().isConstant();

        startTask();
    }

    @Override
    public void show(Location iterationBaseLocation) {

        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        if (fromCenterToApex == null || baseDataAreVariable) {
            computePyramidBasicData();
        }

        // Compute the pyramid apex
        Location apex = iterationBaseLocation.clone().add(fromCenterToApex);

        // Compute each base apex
        List<Location> baseApexList = fromCenterToBaseApexList.stream()
                .map(fromCenterToBaseApex -> iterationBaseLocation.clone().add(fromCenterToBaseApex))
                .collect(Collectors.toList());


        drawLines(baseApexList, apex);
    }

    private void drawLines(List<Location> baseApexList, Location apex) {

        double distanceBetweenParticles = animation.getDistanceBetweenParticles().getCurrentValue(iterationCount);

        //Link base apexes together
        drawLine(baseApexList.get(0), baseApexList.get(baseApexList.size() - 1), distanceBetweenParticles);
        for (int i = 0; i < baseApexList.size() - 1; i++) {
            drawLine(baseApexList.get(i), baseApexList.get(i + 1), distanceBetweenParticles);
        }

        //Link base apexes with the main apex.
        baseApexList.forEach(baseApex -> drawLine(baseApex, apex, distanceBetweenParticles));
    }

    private void computePyramidBasicData() {
        fromCenterToApex = animation.getFromCenterToApex().getCurrentValue(iterationCount);
        /*
        Compute a vector to one of the base's apex:
         - its normal vector : fromCenterToApex
         - a point of the plane : iterationBaseLocation
         */
        Vector normalVector = fromCenterToApex.clone().normalize();

        Vector radiusVector = computeRadiusVector(normalVector, animation.getDistanceToAnyBaseApex().getCurrentValue(iterationCount));

        /*
        Compute each point of the pyramid's base with :
        - the circle
        - the number of points : nbBaseApex
        */
        int nbBaseApex = animation.getNbBaseApex().getCurrentValue(iterationCount);

        double theta = 2 * Math.PI / nbBaseApex;

        fromCenterToBaseApexList = new ArrayList<>();
        fromCenterToBaseApexList.add(radiusVector);

        for (int i = 1; i < nbBaseApex; i++) {
            RotatableVector rotatableVector = new RotatableVector(radiusVector);
            rotatableVector.rotateAroundAxis(normalVector, theta * i);
            fromCenterToBaseApexList.add(rotatableVector);
        }
    }
}
