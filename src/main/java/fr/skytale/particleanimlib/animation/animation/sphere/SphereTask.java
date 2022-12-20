package fr.skytale.particleanimlib.animation.animation.sphere;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SphereTask extends AAnimationTask<Sphere> {

    private static final double GOLDEN_RATIO = (1 + Math.sqrt(5)) / 2;

    double currentMinPercent;

    @IVariableCurrentValue
    private Float percentShownWhilePropagate;

    @IVariableCurrentValue
    private Float percentStepWhilePropagate;

    @IVariableCurrentValue
    private Double radius;

    @IVariableCurrentValue
    private Integer nbPoints;

    public SphereTask(Sphere sphere) {
        super(sphere);
        currentMinPercent = 0;
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {

        Sphere.PropagationType propagationType = animation.getPropagationType();
        if (propagationType != null && currentMinPercent >= 1) {
            stopAnimation();
            return null;
        }


        List<Vector> animationPointsDataVector = new ArrayList<>();

        Sphere.Type type = animation.getType();
        double phi, theta, sinPhi;


        double typeDeducedShift;
        double typeDeducedFactor;
        switch (type) {
            case HALF_TOP:
                typeDeducedFactor = 1;
                typeDeducedShift = 1;
                break;
            case HALF_BOTTOM:
                typeDeducedFactor = 1;
                typeDeducedShift = 0;
                break;
            default:
                typeDeducedFactor = 2;
                typeDeducedShift = 1;
                break;
        }

        for (int i = 0; i < nbPoints; i++) {
            phi = Math.acos(typeDeducedShift - typeDeducedFactor * (i + 0.5d) / nbPoints);
            theta = (2 * Math.PI * i) / GOLDEN_RATIO;
            sinPhi = Math.sin(phi);

            animationPointsDataVector.add(new Vector(
                    radius * Math.cos(theta) * sinPhi,
                    radius * Math.cos(phi),
                    radius * Math.sin(theta) * sinPhi
            ));
        }

        if (propagationType != null) {

            if (propagationType == Sphere.PropagationType.BOTTOM_TO_TOP) {
                Collections.reverse(animationPointsDataVector);
            }

            double currentMaxPercent = Math.min((currentMinPercent + percentShownWhilePropagate), 1);

            int minPointIndex = (int) Math.floor(currentMinPercent * nbPoints);
            int maxPointIndex = (int) Math.floor(currentMaxPercent * nbPoints);

            animationPointsDataVector = animationPointsDataVector.subList(minPointIndex, maxPointIndex);

            currentMinPercent += percentStepWhilePropagate;
        }

        return animationPointsDataVector.stream()
                .map(AnimationPointData::new)
                .collect(Collectors.toList());

    }

    @Override
    protected boolean shouldUpdatePoints() {
        return animation.getPropagationType() != null;
    }

    public double getCurrentRadius() {
        return radius;
    }


}
