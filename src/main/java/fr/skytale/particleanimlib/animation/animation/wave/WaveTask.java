package fr.skytale.particleanimlib.animation.animation.wave;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.ForceUpdatePointsConfiguration;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@ForceUpdatePointsConfiguration(alwaysUpdate = true)
public class WaveTask extends AAnimationTask<Wave> {

    private final double intermediateCachedResult;
    @IVariableCurrentValue
    protected Double angleBetweenEachPoint;
    @IVariableCurrentValue
    protected Integer nbPoints;
    @IVariableCurrentValue
    protected Double radiusStep;
    private double currentRadius;

    public WaveTask(Wave wave) {
        super(wave);
        this.currentRadius = animation.getRadiusStart();
        this.intermediateCachedResult = -0.1 * 39 / (animation.getRadiusMax() - animation.getRadiusStart());
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        List<AnimationPointData> animationPointsData = new ArrayList<>();
        // Computing the vertical coordinate of the wave's current circle
        double baseY = ((2 * Math.exp(intermediateCachedResult * currentRadius) * Math.sin(currentRadius)) + 1) *
                       (animation.getPositiveHeight() ? 1 : -1);

        // Tracing circle
        for (int pointIndex = 0; pointIndex < nbPoints; pointIndex++) {
            double theta = pointIndex * angleBetweenEachPoint;
            double cosThetaDotRadius = currentRadius * Math.cos(theta);
            double sinThetaDotRadius = currentRadius * Math.sin(theta);

            double x = (U.getX() * cosThetaDotRadius); // + (V.getX() * sinThetaDotRadius);
            double y = baseY; // + (U.getY() * cosThetaDotRadius) + (V.getY() * sinThetaDotRadius);
            double z = (V.getZ() * sinThetaDotRadius); // + (U.getZ() * cosThetaDotRadius);

            animationPointsData.add(new AnimationPointData(new Vector(x, y, z)));
        }

        currentRadius += radiusStep;

        return animationPointsData;
    }

    @Override
    protected boolean shouldStop() {
        return super.shouldStop() || currentRadius >= animation.getRadiusMax();
    }
}
