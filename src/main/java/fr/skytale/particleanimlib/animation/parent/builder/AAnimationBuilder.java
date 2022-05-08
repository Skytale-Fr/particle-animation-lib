package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attribute.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.CollisionHandler;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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

    /********* Generic AAnimation attributes setters ***********/

    // --------------------- Attributes ---------------------
    public void setPosition(APosition position) {
        checkNotNull(position, POSITION_SHOULD_NOT_BE_NULL);
        animation.setPosition(position);
    }

    public void setMainParticle(ParticleTemplate mainParticle) {
        animation.setMainParticle(mainParticle);
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
        if(collisionHandler == null) return;
        animation.addCollisionHandler((CollisionHandler<?, AAnimationTask<?>>) collisionHandler);
    }

    public void setShowPeriod(int showPeriod) {
        setShowPeriod(new Constant<>(showPeriod));
    }

    public void addAnimationEndedCallback(AnimationEndedCallback callback) {
        if(callback == null) return;
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
        if (animation instanceof ISubAnimationContainer) {
            checkNotNull(((ISubAnimationContainer) animation).getPointDefinition(), POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        } else {
            checkNotNull(animation.getMainParticle(), "Main particle should not be null");
        }
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