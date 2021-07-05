package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attributes.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.attributes.var.Constant;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AAnimationBuilder<T extends AAnimation> {
    public static final String POSITION_SHOULD_NOT_BE_NULL = "Position should not be null";
    protected T animation;

    public AAnimationBuilder() {
        animation = initAnimation();
        animation.setShowFrequency(new Constant<>(0));
        animation.setTicksDuration(60);
    }

    protected abstract T initAnimation();

    /*********SETTERS des éléments généraux d'une animation ***********/

    public void setPosition(APosition position) {
        checkNotNull(position, POSITION_SHOULD_NOT_BE_NULL);
        animation.setPosition(position);
    }

    public void setMainParticle(ParticleTemplate mainParticle) {
        animation.setMainParticle(mainParticle);
    }

    public void setJavaPlugin(JavaPlugin javaPlugin) {
        animation.setPlugin(javaPlugin);
    }

    public void setTicksDuration(int ticksDuration) {
        animation.setTicksDuration(ticksDuration);
    }

    public void setShowFrequency(IVariable<Integer> showFrequency) {
        checkPositiveAndNotNull(showFrequency, "showFrequency should be positive or zero", true);
        animation.setShowFrequency(showFrequency);
    }

    public void setCallback(AnimationEndedCallback callback) {
        animation.setCallback(callback);
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
                throw new IllegalArgumentException("Animations must contain a moving entity or a location.");

            }
        }
        checkNotNull(animation.getMainParticle(), "Main particle should not be null");
        checkNotNull(animation.getPlugin(), "The plugin should be defined");
        if (animation.getTicksDuration() <= 0) {
            throw new IllegalArgumentException("Position should be positive");
        }
        return (T) animation.clone();
    }

    protected static void checkPositiveAndNotNull(IVariable<? extends Number> number, String checkFailureMessage, boolean allowZero) {
        checkNotNull(number, checkFailureMessage);
        checkPositive(number, checkFailureMessage, allowZero);
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

    protected static void checkNotNull(Object obj, String checkFailureMessage) {
        if (obj == null) {
            throw new IllegalArgumentException(checkFailureMessage);
        }
    }

    protected void checkNotNullOrZero(IVariable<? extends Number> number, String checkFailureMessage) {
        checkNotNull(number, checkFailureMessage);
        if (number.isConstant()) {
            double doubleValue = number.getCurrentValue(0).doubleValue();
            if (doubleValue == 0) {
                throw new IllegalArgumentException(checkFailureMessage);
            }
        }
    }

    protected void checkSuperior(IVariable<? extends Number> superior, IVariable<? extends Number> inferior, String checkFailureMessage, boolean allowEqual) {
        if (superior != null && superior.isConstant() && inferior != null && inferior.isConstant()) {
            if (allowEqual) {
                if (superior.getCurrentValue(0).doubleValue() <= inferior.getCurrentValue(0).doubleValue()) {
                    throw new IllegalArgumentException(checkFailureMessage);
                }
            } else {
                if (superior.getCurrentValue(0).doubleValue() < inferior.getCurrentValue(0).doubleValue()) {
                    throw new IllegalArgumentException(checkFailureMessage);
                }
            }
        }
    }
}