package fr.skytale.particleanimlib.animation.attribute.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IVariableCurrentValue {
    /**
     * The name of the IVariable field in the animation class
     * @return the name of the IVariable field in the animation class
     */
    String animationIVariableFieldName() default "";

    /**
     * Call computePoints method if the current value of the IVariableCurrentValueFields has changed
     * @return true if computePoints method should be called if the current value of the IVariableCurrentValueFields has changed
     */
    boolean updatePointsIfChange() default true;
}
