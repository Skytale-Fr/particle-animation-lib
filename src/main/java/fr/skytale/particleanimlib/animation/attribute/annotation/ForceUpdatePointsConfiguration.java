package fr.skytale.particleanimlib.animation.attribute.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ForceUpdatePointsConfiguration {
    boolean alwaysUpdate() default false;

    boolean ifIVariableCurrentValueFieldsChanges() default true;

    boolean ifShouldUpdatePointsMethod() default true;
}
