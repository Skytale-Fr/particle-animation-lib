package fr.skytale.particleanimlib.animation.animation.pyramid;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.animation.parent.task.AnimationTaskUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PyramidTask extends AAnimationTask<Pyramid> {

    @IVariableCurrentValue
    private Vector fromCenterToApex;

    @IVariableCurrentValue
    private Double distanceToAnyBaseApex;

    @IVariableCurrentValue
    private Integer nbBaseApex;

    @IVariableCurrentValue
    private Double distanceBetweenParticles;

    public PyramidTask(Pyramid pyramid) {
        super(pyramid);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        List<Vector> fromCenterToBaseApexList = computeBaseApexList();

        return drawLines(fromCenterToBaseApexList, fromCenterToApex);
    }

    private List<Vector> computeBaseApexList() {
        List<Vector> fromCenterToBaseApexList = new ArrayList<>();
        /*
        Compute a vector to one of the base's apex:
         - its normal vector : fromCenterToApex
         - a point of the plane : iterationBaseLocation
         */
        Vector normalVector = fromCenterToApex.clone().normalize();

        Vector radiusVector = AnimationTaskUtils.computeRadiusVector(normalVector, animation.getDistanceToAnyBaseApex().getCurrentValue(iterationCount));

        /*
        Compute each point of the pyramid's base with :
        - the circle
        - the number of points : nbBaseApex
        */

        double theta = 2 * Math.PI / nbBaseApex;

        fromCenterToBaseApexList.add(radiusVector);

        for (int i = 1; i < nbBaseApex; i++) {
            RotatableVector rotatableVector = new RotatableVector(radiusVector);
            rotatableVector.rotateAroundAxis(normalVector, theta * i);
            fromCenterToBaseApexList.add(rotatableVector);
        }

        return fromCenterToBaseApexList;
    }

    private List<AnimationPointData> drawLines(List<Vector> baseApexList, Vector apex) {
        List<AnimationPointData> animationPoints = new ArrayList<>();

        //Link base apexes together
        animationPoints.addAll(getLinePoints(baseApexList.get(0), baseApexList.get(baseApexList.size() - 1), distanceBetweenParticles));
        for (int i = 0; i < baseApexList.size() - 1; i++) {
            animationPoints.addAll(getLinePoints(baseApexList.get(i), baseApexList.get(i + 1), distanceBetweenParticles));
        }

        //Link base apexes with the main apex.
        baseApexList.forEach(baseApex -> animationPoints.addAll(getLinePoints(baseApex, apex, distanceBetweenParticles)));

        return animationPoints;
    }

}
