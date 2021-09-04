package fr.skytale.particleanimlib.animation.animation.text;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class TextBuilder extends ARotatingAnimationBuilder<Text> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    public static final String BASE_STRING_SHOULD_NOT_BE_NULL = "baseString should not be null";
    public static final String FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY = "fontFileName should not be null or empty";
    public static final String FONT_SIZE_SHOULD_NOT_BE_NULL_OR_EMPTY = "fontSize should not be null or empty";
    public static final String DETAILS_LEVEL_SHOULD_BE_POSTIIVE_AND_NOT_NULL = "detailsLevel should be positive and not null";


    public TextBuilder() {
        super();
        // Default values
        animation.setU(new Vector(1, 0, 0));
        animation.setV(new Vector(0, 1, 0));
        animation.setFontFileName("Minecraft.ttf");
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
        animation.setString(new Constant<>("Coucou"));
        animation.setFontSize(new Constant<>(3.0d));
        animation.setDetailsLevel(new Constant<>(1.0d));
    }

    @Override
    protected Text initAnimation() {
        return new Text();
    }

    /********* Line specific setters ***********/
    public void setString(IVariable<String> string) {
        checkNotNull(string, BASE_STRING_SHOULD_NOT_BE_NULL);
        animation.setString(string);
    }

    public void setFontSize(IVariable<Double> fontSize) {
        checkNotNull(fontSize, FONT_SIZE_SHOULD_NOT_BE_NULL_OR_EMPTY);
        animation.setFontSize(fontSize);
    }

    public void setDetailsLevel(IVariable<Double> detailsLevel) {
        checkPositiveAndNotNull(detailsLevel, DETAILS_LEVEL_SHOULD_BE_POSTIIVE_AND_NOT_NULL, true);
        animation.setDetailsLevel(detailsLevel);
    }

    public void setFontFileName(String fontFileName) {
        checkNotNull(fontFileName, FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY);
        Validate.notEmpty(fontFileName, FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY);
        animation.setFontFileName(fontFileName);
    }

    public void setDirectorVectors(Vector u, Vector v) {
        checkNotNull(u, DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(v, DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        animation.setU(u);
        animation.setV(v);
    }

    public void setDirectorVectorsFromOrientation(Orientation direction, double length) {
        setDirectorVectors(direction.getU(length), direction.getV(length));
    }

    public void setDirectorVectorsFromNormalVector(Vector normal) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        animation.setU(plane.u);
        animation.setV(plane.v);
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
    }

    @Override
    public Text getAnimation() {
        checkNotNull(animation.getU(), DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getV(), DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getBaseString(), BASE_STRING_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getFontSize(), FONT_SIZE_SHOULD_NOT_BE_NULL_OR_EMPTY);
        checkNotNull(animation.getFontFileName(), FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY);
        checkNotNull(animation.getDetailsLevel(), DETAILS_LEVEL_SHOULD_BE_POSTIIVE_AND_NOT_NULL);
        Validate.notEmpty(animation.getFontFileName(), FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY);

        return super.getAnimation();
    }
}
