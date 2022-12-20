package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.function.Function;

public class AnimationPointData {
    private final Vector fromCenterToPoint;

    private final Function<APointDefinition, APointDefinition> pointDefinitionModifier;

    public AnimationPointData(Vector fromCenterToPoint) {
        this(fromCenterToPoint, null);
    }

    public AnimationPointData(Vector fromCenterToPoint, Function<APointDefinition, APointDefinition> pointDefinitionModifier) {
        this.fromCenterToPoint = fromCenterToPoint;
        this.pointDefinitionModifier = pointDefinitionModifier;
    }

    public Vector getFromCenterToPoint() {
        return fromCenterToPoint;
    }

    public Function<APointDefinition, APointDefinition> getPointDefinitionModifier() {
        return pointDefinitionModifier;
    }

    public APointDefinition applyModifier(APointDefinition pointDefinition) {
        if (this.pointDefinitionModifier == null) {
            return pointDefinition;
        }
        return this.pointDefinitionModifier.apply(pointDefinition);
    }

    public static Function<APointDefinition, APointDefinition> getPointModifierForColor(Color color) {
        return aPointDefinition -> {
            APointDefinition currentAPointDefinition = aPointDefinition;
            while (currentAPointDefinition instanceof SubAnimPointDefinition) {
                final AAnimation subAnimation = (AAnimation) ((SubAnimPointDefinition) aPointDefinition).getSubAnimation();
                currentAPointDefinition = subAnimation.getPointDefinition();
            }
            if (currentAPointDefinition instanceof ParticlePointDefinition) {
                ((ParticlePointDefinition) currentAPointDefinition).getParticleTemplate().setColor(color);
            }
            return currentAPointDefinition;
        };
    }
}
