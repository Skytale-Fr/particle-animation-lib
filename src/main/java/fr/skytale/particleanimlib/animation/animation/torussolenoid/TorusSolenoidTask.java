package fr.skytale.particleanimlib.animation.animation.torussolenoid;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TorusSolenoidTask extends AAnimationTask<TorusSolenoid> {
    @IVariableCurrentValue
    private Integer nbPoints;

    @IVariableCurrentValue
    private Double torusSolenoidModifierNumerator;

    @IVariableCurrentValue
    private Integer torusSolenoidModifierDenominator;

    @IVariableCurrentValue
    private Double torusRadius;

    @IVariableCurrentValue
    private Double solenoidRadius;

    public TorusSolenoidTask(TorusSolenoid torusSolenoid) {
        super(torusSolenoid);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        List<AnimationPointData> animationPointsData = new ArrayList<>();
        double torusModifier = torusSolenoidModifierNumerator / torusSolenoidModifierDenominator;
        double maxTheta;
        //defining maxTheta according to https://mathworld.wolfram.com/TorusSolenoidCurve.html
        // If torusModifier is a rational number, then the curve closes at a computable polar angle.
        if ((torusModifier * 100000) % 1 == 0) { // if torusModifier is approximately a rational number
            maxTheta = Math.PI * torusSolenoidModifierDenominator * 2;
        } else {
            // If torusrModifier is an irrational number, the curve never closes.
            maxTheta = 16 * Math.PI;
        }

        double stepTheta = maxTheta / nbPoints;
        for (double theta = 0; theta <= maxTheta; theta += stepTheta) {

            final double cosTorusModifierTheta = Math.cos(torusModifier * theta);
            double x = (torusRadius + solenoidRadius * cosTorusModifierTheta) * Math.cos(theta);
            double y = (torusRadius + solenoidRadius * cosTorusModifierTheta) * Math.sin(theta);
            double z = solenoidRadius * Math.sin(torusModifier * theta);

            animationPointsData.add(new AnimationPointData(new Vector(x, y, z)));
        }

        return animationPointsData;
    }
}
