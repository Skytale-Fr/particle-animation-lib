package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.awt.*;
import java.util.function.Function;

public class AnimationPointData {
    private final Vector fromCenterToPoint;

    private final Function<APointDefinition, APointDefinition> pointDefinitionModifier;

    public AnimationPointData(Vector fromCenterToPoint) {
        this.fromCenterToPoint = fromCenterToPoint;
        this.pointDefinitionModifier = null;
    }

    public AnimationPointData(Vector fromCenterToPoint, Function<APointDefinition, APointDefinition> pointDefinitionModifier) {
        this.fromCenterToPoint = fromCenterToPoint;
        this.pointDefinitionModifier = pointDefinitionModifier;
    }

    public AnimationPointData(Vector fromCenterToPoint, Color color) {
        this(fromCenterToPoint, getPointModifierForColor(color));
    }

    public static Function<APointDefinition, APointDefinition> getPointModifierForColor(Color color) {
        return aPointDefinition -> {
            if (aPointDefinition instanceof ParticlePointDefinition) {
                final ParticlePointDefinition clonedPointDefinition = (ParticlePointDefinition) aPointDefinition.copy();
                clonedPointDefinition.getParticleTemplate().setAdditionalData(new RegularColor(color));
                return clonedPointDefinition;
            }
            return aPointDefinition;
        };
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
}
