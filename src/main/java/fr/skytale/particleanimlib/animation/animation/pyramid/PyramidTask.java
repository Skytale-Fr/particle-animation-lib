package fr.skytale.particleanimlib.animation.animation.pyramid;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.animation.parent.task.AnimationTaskUtils;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

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

        return getLinesPoints(fromCenterToBaseApexList, fromCenterToApex);
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
            fromCenterToBaseApexList.add(
                    new RotatableVector(radiusVector)
                            .rotateAroundAxis(normalVector, theta * i)
            );
        }

        return fromCenterToBaseApexList;
    }

    private List<AnimationPointData> getLinesPoints(List<Vector> baseApexList, Vector apex) {

        //Link base apexes together
        List<AnimationPointData> animationPoints = new ArrayList<>(AnimationTaskUtils.getLinePoints(
                baseApexList.get(0),
                baseApexList.get(baseApexList.size() - 1),
                distanceBetweenParticles
        ));
        for (int i = 0; i < baseApexList.size() - 1; i++) {
            animationPoints.addAll(AnimationTaskUtils.getLinePoints(
                            baseApexList.get(i),
                            baseApexList.get(i + 1),
                            distanceBetweenParticles
            ));
        }

        //Link base apexes with the main apex.
        baseApexList.forEach(baseApex ->
                animationPoints.addAll(AnimationTaskUtils.getLinePoints(
                        baseApex,
                        apex,
                        distanceBetweenParticles
                ))
        );

        return animationPoints;
    }

}
