package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attribute.*;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.attr.PositionType;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.IPosition;
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
        setRotation(PARotation.DEFAULT_ROTATION);
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

    /**
     * Retrieves the built animation
     * @return the animation
     */
    public T getAnimation() {
        checkNotNull(animation.getPosition(), POSITION_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getPointDefinition(), POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getPlugin(), "The plugin should be defined");
        checkNotNull(animation.getViewers(), VIEWERS_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getPointDefinition(), "The point definition should be defined");
        checkSubAnimPointDefinitionTicksDuration();
        if (animation.getTicksDuration() <= 0) {
            throw new IllegalArgumentException("Position should be positive");
        }
        //noinspection unchecked
        return (T) animation.clone();
    }

    // --------------------- APPLY PRESET ---------------------

    /**
     * Apply to this builder an animation preset. Then, this preset can potentially be modified using this builder.
     * @param animationPreset the animation preset
     * @param plugin the JavaPlugin instance
     */
    public void applyPreset(AnimationPreset animationPreset, JavaPlugin plugin) {
        animationPreset.apply(this, plugin);
    }

    // --------------------- Attributes ---------------------

    /**
     * Retrieves the changing position of the animation
     * @return the changing position of the animation
     */
    public IPosition getPosition() {
        return animation.getPosition();
    }

    /**
     * Defines the changing position of the animation
     * @param position the changing position of the animation
     */
    public void setPosition(IPosition position) {
        checkNotNull(position, POSITION_SHOULD_NOT_BE_NULL);
        animation.setPosition(position);
    }

    /**
     * Defines the changing rotation of the animation
     * @param rotation the changing rotation of the animation
     */
    public void setRotation(IVariable<PARotation> rotation) {
        animation.setRotation(rotation);
    }

    /**
     * Defines the rotation of the animation as a constant (the rotation will not change over time)
     * @param rotation the fixed rotation of the animation
     */
    public void setRotation(PARotation rotation) {
        setRotation(new Constant<>(rotation));
    }

    /**
     * Defines the changing rotation of the animation.
     * It will start from the default rotation (horizontal plane for 2D animation).
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(Vector axis, Double rotationAngleAlpha) {
        setRotation(new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * It will start from the default rotation (horizontal plane for 2D animation).
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(IVariable<Vector> axis, Double rotationAngleAlpha) {
        setRotation(axis, new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * It will start from the default rotation (horizontal plane for 2D animation).
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
    public void setRotation(Vector axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(new Constant<>(axis), rotationAngleAlpha);
    }

    /**
     * Defines the changing rotation of the animation.
     * It will start from the default rotation (horizontal plane for 2D animation).
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
    public void setRotation(IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(PARotation.DEFAULT_ROTATION, axis, rotationAngleAlpha);
    }

    /**
     * Defines the changing rotation of the animation.
     * It will start from the given initial rotation.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * @param initialRotation the initial animation rotation
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(PARotation initialRotation, Vector axis, Double rotationAngleAlpha) {
        setRotation(initialRotation, new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * It will start from the given initial rotation.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * @param initialRotation the initial animation rotation
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
    public void setRotation(PARotation initialRotation, Vector axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(initialRotation, new Constant<>(axis), rotationAngleAlpha);
    }

    /**
     * Defines the changing rotation of the animation.
     * It will start from the given initial rotation.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * @param initialRotation the initial animation rotation
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(PARotation initialRotation, IVariable<Vector> axis, Double rotationAngleAlpha) {
        setRotation(initialRotation, axis, new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * It will start from the given initial rotation.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * @param initialRotation the initial animation rotation
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
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

    /**
     * Defines the fixed rotation of the animation according to the given plane director vectors.
     * (used mainly for 2D animations that will be displayed in a plane)
     * @param u the first director vector of the plane containing the animation
     * @param v the second director vector of the plane containing the animation
     */
    public void setRotation(Vector u, Vector v) {
        setRotation(new Constant<>(new PARotation(
                AAnimationTask.U, u,
                AAnimationTask.V, v)));
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given plane director vectors.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param u the first director vector of the initial plane containing the animation
     * @param v the second director vector of the initial plane containing the animation
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(Vector u, Vector v, Vector axis, Double rotationAngleAlpha) {
        setRotation(u, v, new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given plane director vectors.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param u the first director vector of the initial plane containing the animation
     * @param v the second director vector of the initial plane containing the animation
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
    public void setRotation(Vector u, Vector v, Vector axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(u, v, new Constant<>(axis), rotationAngleAlpha);
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given plane director vectors.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param u the first director vector of the initial plane containing the animation
     * @param v the second director vector of the initial plane containing the animation
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(Vector u, Vector v, IVariable<Vector> axis, Double rotationAngleAlpha) {
        setRotation(u, v, axis, new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given plane director vectors.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param u the first director vector of the initial plane containing the animation
     * @param v the second director vector of the initial plane containing the animation
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
    public void setRotation(Vector u, Vector v, IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
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

    /**
     * Defines the fixed rotation of the animation according to the given normal vector.
     * (used mainly for 2D animations that will be displayed in a plane)
     * @param normal a vector normal to the plane containing the animation
     */
    public void setRotation(Vector normal) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        setRotation(plane.u, plane.v);
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given normal vector.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param normal a vector normal to the initial plane containing the animation
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(Vector normal, Vector axis, Double rotationAngleAlpha) {
        setRotation(normal, new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given normal vector.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param normal a vector normal to the initial plane containing the animation
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
    public void setRotation(Vector normal, Vector axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(normal, new Constant<>(axis), rotationAngleAlpha);
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given normal vector.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param normal a vector normal to the initial plane containing the animation
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(Vector normal, IVariable<Vector> axis, Double rotationAngleAlpha) {
        setRotation(normal, axis, new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given normal vector.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param normal a vector normal to the initial plane containing the animation
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
    public void setRotation(Vector normal, IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        setRotation(plane.u, plane.v, axis, rotationAngleAlpha);
    }
    
    /**
     * Defines the fixed rotation of the animation according to its orientation.
     * (used mainly for 2D animations that will be displayed in a plane)
     * @param direction the orientation of the animation
     */
    public void setRotation(Orientation direction) {
        setRotation(direction.getU(), direction.getV());
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given orientation.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param direction the initial orientation of the animation
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(Orientation direction, Vector axis, Double rotationAngleAlpha) {
        setRotation(direction, new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given orientation.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param direction the initial orientation of the animation
     * @param axis the fixed rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
    public void setRotation(Orientation direction, Vector axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(direction, new Constant<>(axis), rotationAngleAlpha);
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given orientation.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param direction the initial orientation of the animation
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the fixed rotation angle
     */
    public void setRotation(Orientation direction, IVariable<Vector> axis, Double rotationAngleAlpha) {
        setRotation(direction, axis, new Constant<>(rotationAngleAlpha));
    }

    /**
     * Defines the changing rotation of the animation.
     * The initial rotation is defined according to the given orientation.
     * The evolution of the rotation is defined according to the given axis and to the given rotationAngleAlpha.
     * At each tick, the animation will be rotated by the given rotationAngleAlpha around the given axis.
     * (used mainly for 2D animations that will be displayed in a plane)
     *
     * @param direction the initial orientation of the animation
     * @param axis the changing rotation axis
     * @param rotationAngleAlpha the changing rotation angle
     */
    public void setRotation(Orientation direction, IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(direction.getU(), direction.getV(), axis, rotationAngleAlpha);
    }

    /**
     * Defines how each point is displayed.
     *
     * @param particleTemplate the definition of particle that will be shown at each animation point
     */
    public void setPointDefinition(ParticleTemplate particleTemplate) {
        checkNotNull(particleTemplate, "ParticleTemplate should not be null");
        setPointDefinition(new ParticlePointDefinition(particleTemplate));
    }

    /**
     * Defines how each point is displayed.
     *
     * @param pointDefinition the point definition defining what is displayed at each point (sub animation, callback, ...)
     */
    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    /**
     * Retrieves the JavaPlugin executing this animation
     *
     * @return the JavaPlugin instance
     */
    public JavaPlugin getJavaPlugin() {
        return animation.getPlugin();
    }

    /**
     * Defines the JavaPlugin executing this animation
     * @param javaPlugin the JavaPlugin instance
     */
    public void setJavaPlugin(JavaPlugin javaPlugin) {
        animation.setPlugin(javaPlugin);
    }

    /**
     * Defines the duration of the animation (in ticks)
     * 20 ticks = 1 second
     * @param ticksDuration the duration of the animation (in ticks)
     */
    public void setTicksDuration(int ticksDuration) {
        animation.setTicksDuration(ticksDuration);
    }

    /**
     * Defines how often the animation is displayed.
     * The period is the tick interval between two displays.
     *
     * @param showPeriod the tick interval between two displays
     */
    public void setShowPeriod(int showPeriod) {
        setShowPeriod(new Constant<>(showPeriod));
    }


    /**
     * Defines how often the animation is displayed.
     * The period is the tick interval between two displays.
     *
     * @param showPeriod the changing tick interval between two displays
     */
    public void setShowPeriod(IVariable<Integer> showPeriod) {
        checkPositiveAndNotNull(showPeriod, "showPeriod should be positive or zero", true);
        animation.setShowPeriod(showPeriod);
    }

    /**
     * Defines who can see the animation
     * @param viewers the definition of which player will see the animation
     */
    public void setViewers(AViewers viewers) {
        checkNotNull(viewers, VIEWERS_SHOULD_NOT_BE_NULL);
        animation.setViewers(viewers);
    }

    /**
     * Defines who can see the animation according to a maximum distance in the world
     * @param distance the maximum distance at which the player can still see the animation
     */
    public void setViewers(double distance) {
        animation.setViewers(AViewers.fromNearbyPlayers(distance));
    }

    /**
     * Defines who can see the animation according to a predicate.
     * This BiPredicate takes a player and the location of the point to show as parameters.
     * This BiPredicate must return true if the player should see the point, false otherwise.
     * @param biPredicate A BiPredicate using the player and the point to show and then returning true if the point
     *                   should be displayed to this player.
     */
    public void setViewers(BiPredicate<Player, Location> biPredicate) {
        animation.setViewers(AViewers.fromPredicateMatchingPlayers(biPredicate));
    }

    /**
     * Defines who can see the animation according to a collection of players
     * @param viewers the collection of players that will see the animation
     */
    public void setViewers(Collection<? extends Player> viewers) {
        checkNotNull(viewers, VIEWERS_SHOULD_NOT_BE_NULL);
        animation.setViewers(AViewers.fromCustomPlayers(viewers));
    }

    /**
     * Defines a condition that will stops the animation
     * The animation will therefore stop if the duration is exceeded or if the stop condition is met.
     * @param stopCondition the stop condition
     */
    public void setStopCondition(AnimationStopCondition<K> stopCondition) {
        this.setStopCondition(stopCondition, false);
    }

    /**
     * Defines a condition that will stops the animation.
     * This method also allow to remove the duration of the animation.
     * Therefore, if infiniteTickDuration is true, the animation will end only if the given stop condition is met.
     * Else, the animation will stop if the duration is exceeded or if the stop condition is met.
     * @param stopCondition the stop condition
     * @param infiniteTickDuration true to avoid the stop of the animation according to its duration
     */
    public void setStopCondition(AnimationStopCondition<K> stopCondition, boolean infiniteTickDuration) {
        animation.setStopCondition(stopCondition, infiniteTickDuration);
    }

    /**
     * Defines how the animation and the entities collision will be handled
     * @param collisionHandler the definition of how the collision between the animation and entities will be handled
     */
    public void addCollisionHandler(CollisionHandler<?, K> collisionHandler) {
        if (collisionHandler == null) return;
        animation.addCollisionHandler((CollisionHandler<?, AAnimationTask<?>>) collisionHandler);
    }

    /**
     * Adds a callback that will be executed when the animation ends
     * @param callback another callback to run when the animation is stopped
     */
    public void addAnimationEndedCallback(AnimationEndedCallback callback) {
        if (callback == null) return;
        animation.addAnimationEndedCallback(callback);
    }

    /**
     * Clear the callback that would otherwise have been executed when the animation ends
     */
    public void clearAnimationEndedCallbacks() {
        animation.clearAnimationEndedCallbacks();
    }

    /**
     * Set a single callback that will be executed when the animation ends
     * @param callback the only callback ran when the animation is stopped
     */
    public void setAnimationEndedCallback(AnimationEndedCallback callback) {
        if (callback == null) return;
        animation.setAnimationEndedCallback(callback);
    }

    /**
     * Retrieves the first location where the animation will be displayed
     * @return the first location where the animation will be displayed
     */
    public Location getOriginLocation() {
        if (animation.getPosition() == null) {
            throw new IllegalStateException("the animation position should be defined before calling the getOriginLocation method");
        }
        if (animation.getPosition().getType().equals(PositionType.TRAIL)) {
            throw new IllegalArgumentException("Since a trail position depends of the player evolving location, you can not get its origin location");
        }
        return ((AAnimationPosition) animation.getPosition()).getCurrentValue(0).getAfterMoveLocation();
    }

    /**
     * This method aims to throw an exception when the server will obviously crash when showing the animation
     * (because of an excessive number of particles shown)
     */
    protected final void checkSubAnimPointDefinitionTicksDuration() {
        checkSubAnimPointDefinitionTicksDurationRecursive(
                animation,
                0d,
                "");
    }

    /**
     * This recursive method aims to throw an exception when the server will obviously crash when showing the animation
     * This crash happens when we try to show too much particles
     * <p>
     * The exception is based on the multiplication of:
     * =  parent anim (ticksDuration / showPeriod)
     * * sub anim (ticksDuration / showPeriod)
     * * sub sub anim (ticksDuration / showPeriod)
     * * ...
     * <p>
     * "Your animation is trying to show 50304304 (X/Y*X/Y*X/Y ticksDurations/showPeriod) points within N ticks. This would crash your server. Please modify the animation parameters."
     *
     * @param animation            the current animation
     * @param showPerTick       the actual multiplication
     * @param multiplicationString the displayed multiplication used in the final error message
     */
    private void checkSubAnimPointDefinitionTicksDurationRecursive(AAnimation animation, double showPerTick, String multiplicationString) {
        int ticksDuration = animation.getTicksDuration();

        //TODO should use average of every iterationCount showPeriod instead of the showPeriod having iterationTotal 0.
        int showPeriod = animation.getShowPeriod().getCurrentValue(0);

        double animNbShow = ticksDuration / (double) showPeriod;

        String newMultiplicationString = multiplicationString + " * (" + ticksDuration + " / " + showPeriod + ")";
        double newIterationTotal = showPerTick * animNbShow;

        APointDefinition pointDefinition = animation.getPointDefinition();

        if (pointDefinition instanceof SubAnimPointDefinition) {
            // recursively check pointDefinitions in depth
            checkSubAnimPointDefinitionTicksDurationRecursive(
                    ((SubAnimPointDefinition) pointDefinition).getSubAnimation(),
                    newIterationTotal,
                    newMultiplicationString
            );
        } else {
            // we are in the latest sub animation
            if (showPerTick > 10) {
                //if the number of animation displayed each tick is greater than 1,
                //it means the number of particles will increase exponentially.
                //This exponential growth can be correct if well configured so we will not throw the exception if it is <= 10.
                throw new IllegalArgumentException(
                        "With the current settings, your deepest sub animation would be shown " + newIterationTotal +
                        " times.\n" +
                        "This would cause a crash or, at least, this would slow down players.\n" +
                        "Please modify the animation parameters.\n" +
                        "Product of the ticksDuration/showPeriod ratios (from the parent animation to the deepest sub animation):\n" +
                        newMultiplicationString);
            }
        }
    }
}