package fr.skytale.particleanimlib.animation.attribute.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IVariableCurrentValue {
    String animationIVariableFieldName() default "";

    boolean updatePointsIfChange() default true;
}
