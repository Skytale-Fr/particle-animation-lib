package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attribute.*;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.attr.PositionType;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.IPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.CollisionHandler;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.Collection;
import java.util.function.BiPredicate;

public abstract class AAnimationBuilder<T extends AAnimation, K extends AAnimationTask<T>> {
    public static final String POSITION_SHOULD_NOT_BE_NULL = "Position should not be null";
    public static final String POINT_DEFINITION_SHOULD_NOT_BE_NULL = "Point Definition (or Main particle) should not be null";
    protected static final String VIEWERS_SHOULD_NOT_BE_NULL = "viewers should not be null";

    protected T animation;

    protected AAnimationBuilder() {
        animation = initAnimation();
        animation.setShowPeriod(new Constant<>(0));
        animation.setTicksDuration(60);
        animation.setViewers(AViewers.fromNearbyPlayers(300));
        animation.setRotation(new Constant<>(PARotation.DEFAULT_ROTATION));
        animation.setPointDefinition(new ParticlePointDefinition(
                new ParticleTemplate(ParticleEffect.REDSTONE, new Color(255, 170, 0)))
        );
    }

    // --------------------- CHECK SYSTEM ---------------------

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

    protected static void checkNotNullOrZero(IVariable<? extends Number> number, String checkFailureMessage) {
        checkNotNullOrEquals(number, 0, checkFailureMessage);
    }

    protected static void checkNotNullOrEquals(IVariable<? extends Number> number, double forbiddenValue, String checkFailureMessage) {
        checkNotNull(number, checkFailureMessage);
        if (number.isConstant()) {
            double doubleValue = number.getCurrentValue(0).doubleValue();
            if (doubleValue == forbiddenValue) {
                throw new IllegalArgumentException(checkFailureMessage);
            }
        }
    }

    protected static void checkSuperior(IVariable<? extends Number> superior, IVariable<? extends Number> inferior, String checkFailureMessage, boolean allowEqual) {
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


    // --------------------- FINAL BUILD ---------------------
    protected abstract T initAnimation();

    public T getAnimation() {
        checkNotNull(animation.getPosition(), POSITION_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getPointDefinition(), POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getPlugin(), "The plugin should be defined");
        checkNotNull(animation.getViewers(), VIEWERS_SHOULD_NOT_BE_NULL);
        if (animation.getTicksDuration() <= 0) {
            throw new IllegalArgumentException("Position should be positive");
        }
        //noinspection unchecked
        return (T) animation.clone();
    }

    // --------------------- APPLY PRESET ---------------------

    public void applyPreset(AnimationPreset animationPreset, JavaPlugin plugin) {
        animationPreset.apply(this, plugin);
    }

    // --------------------- Attributes ---------------------

    public IPosition getPosition() {
        return animation.getPosition();
    }

    public void setPosition(IPosition position) {
        checkNotNull(position, POSITION_SHOULD_NOT_BE_NULL);
        animation.setPosition(position);
    }

    public void setRotation(IVariable<PARotation> rotation) {
        animation.setRotation(rotation);
    }

    public void setRotation(PARotation rotation) {
        setRotation(new Constant<>(rotation));
    }

    public void setRotation(Vector axis, Double rotationAngleAlpha) {
        setRotation(new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    public void setRotation(IVariable<Vector> axis, Double rotationAngleAlpha) {
        setRotation(axis, new Constant<>(rotationAngleAlpha));
    }

    public void setRotation(Vector axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(new Constant<>(axis), rotationAngleAlpha);
    }

    public void setRotation(IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(PARotation.DEFAULT_ROTATION, axis, rotationAngleAlpha);
    }

    public void setRotation(PARotation initialRotation, Vector axis, Double rotationAngleAlpha) {
        setRotation(initialRotation, new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }
    public void setRotation(PARotation initialRotation, Vector axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(initialRotation, new Constant<>(axis), rotationAngleAlpha);
    }
    public void setRotation(PARotation initialRotation, IVariable<Vector> axis, Double rotationAngleAlpha) {
        setRotation(initialRotation, axis, new Constant<>(rotationAngleAlpha));
    }

    public void setRotation(PARotation initialRotation, IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(new CallbackWithPreviousValueVariable<>(
                initialRotation,
                (iterationCount, previousValue) -> {
                    Vector axisCurrentValue = axis.getCurrentValue(iterationCount);
                    Double rotationAngleAlphaCurrentValue = rotationAngleAlpha.getCurrentValue(iterationCount);

                    PARotation newRotation = new PARotation(previousValue);
                    newRotation.rotate(axisCurrentValue, rotationAngleAlphaCurrentValue);
                    return newRotation;
                }
        ));
    }

    public void setDirectorVectors(Vector u, Vector v) {
        setRotation(new Constant<>(new PARotation(
                AAnimationTask.U, u,
                AAnimationTask.V, v)));
    }

    public void setDirectorVectorsAndRotation(Vector u, Vector v, Vector axis, Double rotationAngleAlpha) {
        setDirectorVectorsAndRotation(u, v, new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    public void setDirectorVectorsAndRotation(Vector u, Vector v, Vector axis, IVariable<Double> rotationAngleAlpha) {
        setDirectorVectorsAndRotation(u, v, new Constant<>(axis), rotationAngleAlpha);
    }

    public void setDirectorVectorsAndRotation(Vector u, Vector v, IVariable<Vector> axis, Double rotationAngleAlpha) {
        setDirectorVectorsAndRotation(u, v, axis, new Constant<>(rotationAngleAlpha));
    }

    public void setDirectorVectorsAndRotation(Vector u, Vector v, IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(new CallbackWithPreviousValueVariable<>(
                new PARotation(AAnimationTask.U, u, AAnimationTask.V, v),
                (iterationCount, previousValue) -> {
                    Vector axisCurrentValue = axis.getCurrentValue(iterationCount);
                    Double rotationAngleAlphaCurrentValue = rotationAngleAlpha.getCurrentValue(iterationCount);

                    PARotation newRotation = new PARotation(previousValue);
                    newRotation.rotate(axisCurrentValue, rotationAngleAlphaCurrentValue);
                    return newRotation;
                }
        ));
    }

    public void setPlaneFromNormalVector(Vector normal) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        setDirectorVectors(plane.u, plane.v);
    }

    public void setPlaneFromNormalVectorAndRotation(Vector normal, Vector axis, Double rotationAngleAlpha) {
        setPlaneFromNormalVectorAndRotation(normal, new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    public void setPlaneFromNormalVectorAndRotation(Vector normal, Vector axis, IVariable<Double> rotationAngleAlpha) {
        setPlaneFromNormalVectorAndRotation(normal, new Constant<>(axis), rotationAngleAlpha);
    }

    public void setPlaneFromNormalVectorAndRotation(Vector normal, IVariable<Vector> axis, Double rotationAngleAlpha) {
        setPlaneFromNormalVectorAndRotation(normal, axis, new Constant<>(rotationAngleAlpha));
    }

    public void setPlaneFromNormalVectorAndRotation(Vector normal, IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        setDirectorVectorsAndRotation(plane.u, plane.v, axis, rotationAngleAlpha);
    }

    public void setOrientation(Orientation direction) {
        setDirectorVectors(direction.getU(), direction.getV());
    }

    public void setOrientationAndRotation(Orientation direction, Vector axis, Double rotationAngleAlpha) {
        setOrientationAndRotation(direction, new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    public void setOrientationAndRotation(Orientation direction, Vector axis, IVariable<Double> rotationAngleAlpha) {
        setOrientationAndRotation(direction, new Constant<>(axis), rotationAngleAlpha);
    }

    public void setOrientationAndRotation(Orientation direction, IVariable<Vector> axis, Double rotationAngleAlpha) {
        setOrientationAndRotation(direction, axis, new Constant<>(rotationAngleAlpha));
    }

    public void setOrientationAndRotation(Orientation direction, IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        setDirectorVectorsAndRotation(direction.getU(), direction.getV(), axis, rotationAngleAlpha);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        checkNotNull(particleTemplate, "ParticleTemplate should not be null");
        setPointDefinition(new ParticlePointDefinition(particleTemplate));
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
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

    public void setShowPeriod(int showPeriod) {
        setShowPeriod(new Constant<>(showPeriod));
    }

    public void setShowPeriod(IVariable<Integer> showPeriod) {
        checkPositiveAndNotNull(showPeriod, "showPeriod should be positive or zero", true);
        animation.setShowPeriod(showPeriod);
    }

    public void setViewers(AViewers viewers) {
        checkNotNull(viewers, VIEWERS_SHOULD_NOT_BE_NULL);
        animation.setViewers(viewers);
    }

    public void setViewers(double distance) {
        animation.setViewers(AViewers.fromNearbyPlayers(distance));
    }

    public void setViewers(BiPredicate<Player, Location> biPredicate) {
        animation.setViewers(AViewers.fromPredicateMatchingPlayers(biPredicate));
    }

    public void setViewers(Collection<? extends Player> viewers) {
        checkNotNull(viewers, VIEWERS_SHOULD_NOT_BE_NULL);
        animation.setViewers(AViewers.fromCustomPlayers(viewers));
    }

    public void setStopCondition(AnimationStopCondition<K> stopCondition) {
        this.setStopCondition(stopCondition, false);
    }

    public void setStopCondition(AnimationStopCondition<K> stopCondition, boolean infiniteTickDuration) {
        animation.setStopCondition(stopCondition, infiniteTickDuration);
    }

    public void addCollisionHandler(CollisionHandler<?, K> collisionHandler) {
        if (collisionHandler == null) return;
        animation.addCollisionHandler((CollisionHandler<?, AAnimationTask<?>>) collisionHandler);
    }

    public void addAnimationEndedCallback(AnimationEndedCallback callback) {
        if (callback == null) return;
        animation.addAnimationEndedCallback(callback);
    }

    public void clearAnimationEndedCallbacks() {
        animation.clearAnimationEndedCallbacks();
    }

    public void setAnimationEndedCallback(AnimationEndedCallback callback) {
        if (callback == null) return;
        animation.setAnimationEndedCallback(callback);
    }

    public Location getOriginLocation() {
        if (animation.getPosition() == null) {
            throw new IllegalStateException("the animation position should be defined before calling the getOriginLocation method");
        }
        if (animation.getPosition().getType().equals(PositionType.TRAIL)) {
            throw new IllegalArgumentException("Since a trail position depends of the player evolving location, you can not get its origin location");
        }
        return ((AAnimationPosition)animation.getPosition()).getCurrentValue(0).getAfterMoveLocation();
    }
}