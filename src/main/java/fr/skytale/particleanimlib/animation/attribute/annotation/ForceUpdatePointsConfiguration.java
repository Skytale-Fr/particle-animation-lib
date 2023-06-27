package fr.skytale.particleanimlib.animation.attribute.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ForceUpdatePointsConfiguration {
    /**
     * Call computePoints method even if the current value of the IVariableCurrentValueFields has not changed
     * @return true if computePoints method should be called even if the current value of the IVariableCurrentValueFields has not changed
     */
    boolean alwaysUpdate() default false;

    /**
     * Call computePoints method if the current value of the IVariableCurrentValueFields has changed
     * @return true if computePoints method should be called if the current value of the IVariableCurrentValueFields has changed
     */
    boolean ifIVariableCurrentValueFieldsChanges() default true;

    /**
     * Call computePoints method if the shouldUpdatePointsMethod method returns true
     * @return true if computePoints method should be called if the shouldUpdatePointsMethod method returns true
     */
    boolean ifShouldUpdatePointsMethod() default true;
}
