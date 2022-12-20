package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attribute.*;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.CollisionHandler;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.Collection;
import java.util.function.BiPredicate;

public abstract class AAnimationBuilder<T extends AAnimation, K extends AAnimationTask<T>> {
    public static final String POSITION_SHOULD_NOT_BE_NULL = "Position should not be null";
    public static final String POINT_DEFINITION_SHOULD_NOT_BE_NULL = "Point Definition (or Main particle) should not be null";

    protected T animation;

    public AAnimationBuilder() {
        animation = initAnimation();
        animation.setShowPeriod(new Constant<>(0));
        animation.setTicksDuration(60);
        animation.setViewers(AViewers.fromNearbyPlayers(300));
        animation.setRotation(new Constant<>(PARotation.DEFAULT_ROTATION));
        animation.setPointDefinition(APointDefinition.fromParticleTemplate(
                new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null)));
    }

    protected static void checkNotNull(Object obj, String checkFailureMessage) {
        if (obj == null) {
            throw new IllegalArgumentException(checkFailureMessage);
        }
    }

    protected static void checkPositive(IVariable<? extends Number> number, String checkFailureMessage, boolean allowZero) {
        if (number != null && number.isConstant()) {
            double doubleValue = number.getCurrentValue(0).doubleValue();
            if (allowZero) {
                if (doubleValue < 0) throw new IllegalArgumentException(checkFailureMessage);
            } else {
                if (doubleValue <= 0) throw new IllegalArgumentException(checkFailureMessage);
            }
        }
    }

    protected static void checkPositiveAndNotNull(IVariable<? extends Number> number, String checkFailureMessage, boolean allowZero) {
        checkNotNull(number, checkFailureMessage);
        checkPositive(number, checkFailureMessage, allowZero);
    }

    protected abstract T initAnimation();

    public APosition getPosition() {
        return animation.getPosition();
    }

    public void setPosition(APosition position) {
        checkNotNull(position, POSITION_SHOULD_NOT_BE_NULL);
        animation.setPosition(position);
    }

    /********* Generic AAnimation attributes setters ***********/

    // --------------------- Attributes ---------------------
    public void setRotation(IVariable<PARotation> rotation) {
        animation.setRotation(rotation);
    }

    public void setRotation(PARotation rotation) {
        setRotation(new Constant<>(rotation));
    }

    public void setRotation(IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        IVariable<PARotation> rotation = new CallbackWithPreviousValueVariable<>(PARotation.DEFAULT_ROTATION,(iterationCount, previousValue) -> {
            Vector axisCurrentValue = axis.getCurrentValue(iterationCount);
            Double rotationAngleAlphaCurrentValue = rotationAngleAlpha.getCurrentValue(iterationCount);

            PARotation newRotation = new PARotation(previousValue);
            newRotation.rotate(axisCurrentValue,rotationAngleAlphaCurrentValue);
            return newRotation;
        });
        setRotation(rotation);
    }

    public void setRotation(Vector axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(new Constant<>(axis), rotationAngleAlpha);
    }

    public void setRotation(IVariable<Vector> axis, double rotationAngleAlpha) {
        setRotation(axis, new Constant<>(rotationAngleAlpha));
    }

    public void setRotation(Vector axis, double rotationAngleAlpha) {
        setRotation(new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    public void setMainParticle(ParticleTemplate mainParticle) {
        checkNotNull(mainParticle, "Main particle should not be null");
        animation.setPointDefinition(APointDefinition.fromParticleTemplate(mainParticle));
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
    }

    public JavaPlugin getJavaPlugin() {
        return animation.getPlugin();
    }

    public void setJavaPlugin(JavaPlugin javaPlugin) {
        animation.setPlugin(javaPlugin);
    }

    public void setTicksDuration(int ticksDuration) {
        animation.setTicksDuration(ticksDuration);
    }

    public void setShowPeriod(IVariable<Integer> showPeriod) {
        checkPositiveAndNotNull(showPeriod, "showPeriod should be positive or zero", true);
        animation.setShowPeriod(showPeriod);
    }

    public void setViewers(AViewers viewers) {
        checkNotNull(viewers, "viewers should not be null");
        animation.setViewers(viewers);
    }

    public void setStopCondition(AnimationStopCondition<K> stopCondition) {
        this.setStopCondition(stopCondition, false);
    }

    public void setStopCondition(AnimationStopCondition<K> stopCondition, boolean infiniteTickDuration) {
        animation.setStopCondition(stopCondition, infiniteTickDuration);
    }

    // --------------------- FINAL BUILD ---------------------

    public void setViewers(Collection<? extends Player> viewers) {
        checkNotNull(viewers, "viewers should not be null");
        animation.setViewers(AViewers.fromCustomPlayers(viewers));
    }

    public void setViewers(double distance) {
        animation.setViewers(AViewers.fromNearbyPlayers(distance));
    }

    // --------------------- APPLY PRESET ---------------------

    public void setViewers(BiPredicate<Player, Location> biPredicate) {
        animation.setViewers(AViewers.fromPredicateMatchingPlayers(biPredicate));
    }

    // --------------------- CHECK SYSTEM ---------------------
    public void addCollisionHandler(CollisionHandler<?, K> collisionHandler) {
        if (collisionHandler == null) return;
        animation.addCollisionHandler((CollisionHandler<?, AAnimationTask<?>>) collisionHandler);
    }

    public void setShowPeriod(int showPeriod) {
        setShowPeriod(new Constant<>(showPeriod));
    }

    public void addAnimationEndedCallback(AnimationEndedCallback callback) {
        if (callback == null) return;
        animation.addAnimationEndedCallback(callback);
    }

    public T getAnimation() {
        return getAnimation(false);
    }

    public T getAnimation(boolean trailUsage) {
        checkNotNull(animation.getPosition(), POSITION_SHOULD_NOT_BE_NULL);
        if (trailUsage) {
            if (!animation.getPosition().getType().equals(APosition.Type.TRAIL))
                throw new IllegalArgumentException("Trail animations must not contain a moving entity or a location. Only the relative location can optionally be defined.");
        } else {
            if (animation.getPosition().getType().equals(APosition.Type.TRAIL)) {
                throw new IllegalArgumentException("Animations must contain a moving entity or a location. If you are in the context of trails, please call builder.getAnimation(true) instead.");

            }
        }

        checkNotNull(animation.getPointDefinition(), POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getPlugin(), "The plugin should be defined");
        checkNotNull(animation.getViewers(), "viewers should not be null");
        if (animation.getTicksDuration() <= 0) {
            throw new IllegalArgumentException("Position should be positive");
        }
        //noinspection unchecked
        return (T) animation.clone();
    }

    public void applyPreset(AnimationPreset animationPreset, JavaPlugin plugin) {
        animationPreset.apply(this, plugin);
    }

    protected void checkNotNullOrZero(IVariable<? extends Number> number, String checkFailureMessage) {
        checkNotNullOrEquals(number, 0, checkFailureMessage);
    }

    protected void checkNotNullOrEquals(IVariable<? extends Number> number, double forbiddenValue, String checkFailureMessage) {
        checkNotNull(number, checkFailureMessage);
        if (number.isConstant()) {
            double doubleValue = number.getCurrentValue(0).doubleValue();
            if (doubleValue == forbiddenValue) {
                throw new IllegalArgumentException(checkFailureMessage);
            }
        }
    }

    protected void checkSuperior(IVariable<? extends Number> superior, IVariable<? extends Number> inferior, String checkFailureMessage, boolean allowEqual) {
        if (superior != null && superior.isConstant() && inferior != null && inferior.isConstant()) {
            if (allowEqual) {
                if (superior.getCurrentValue(0).doubleValue() < inferior.getCurrentValue(0).doubleValue()) {
                    throw new IllegalArgumentException(checkFailureMessage);
                }
            } else {
                if (superior.getCurrentValue(0).doubleValue() <= inferior.getCurrentValue(0).doubleValue()) {
                    throw new IllegalArgumentException(checkFailureMessage);
                }
            }
        }
    }


}