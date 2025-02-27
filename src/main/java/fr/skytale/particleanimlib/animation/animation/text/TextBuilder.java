package fr.skytale.particleanimlib.animation.animation.text;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.apache.commons.lang.Validate;

public class TextBuilder extends AAnimationBuilder<Text, TextTask> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    public static final String BASE_STRING_SHOULD_NOT_BE_NULL = "baseString should not be null";
    public static final String FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY = "fontFileName should not be null or empty";
    public static final String FONT_SIZE_SHOULD_NOT_BE_NULL_OR_EMPTY = "fontSize should not be null or empty";
    public static final String DETAILS_LEVEL_SHOULD_BE_POSTIIVE_AND_NOT_NULL = "detailsLevel should be positive and not null";


    public TextBuilder() {
        super();
        // Default values
        animation.setFontFileName("Minecraft.ttf");
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
        animation.setText(new Constant<>("Coucou"));
        animation.setFontSize(new Constant<>(3.0d));
        animation.setDetailsLevel(new Constant<>(1.0d));
        animation.setAlignCenter(true);
    }

    @Override
    protected Text initAnimation() {
        return new Text();
    }

    @Override
    public Text getAnimation() {
        checkNotNull(animation.getText(), BASE_STRING_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getFontSize(), FONT_SIZE_SHOULD_NOT_BE_NULL_OR_EMPTY);
        checkNotNull(animation.getFontFileName(), FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY);
        checkNotNull(animation.getDetailsLevel(), DETAILS_LEVEL_SHOULD_BE_POSTIIVE_AND_NOT_NULL);
        Validate.notEmpty(animation.getFontFileName(), FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY);

        return super.getAnimation();
    }

    /********* Line specific setters ***********/

    /**
     * Set the text to display
     * @param string the text to display
     */
    public void setText(String string) {
        setText(new Constant<>(string));
    }

    /**
     * Set the text to display
     * @param string the text to display
     */
    public void setText(IVariable<String> string) {
        checkNotNull(string, BASE_STRING_SHOULD_NOT_BE_NULL);
        animation.setText(string);
    }

    /**
     * Set the font size
     * @param fontSize the font size
     */
    public void setFontSize(Double fontSize) {
        setFontSize(new Constant<>(fontSize));
    }

    /**
     * Set the font size
     * @param fontSize the font size
     */
    public void setFontSize(IVariable<Double> fontSize) {
        checkNotNull(fontSize, FONT_SIZE_SHOULD_NOT_BE_NULL_OR_EMPTY);
        animation.setFontSize(fontSize);
    }

    /**
     * Set the details level
     * @param detailsLevel the details level
     */
    public void setDetailsLevel(Double detailsLevel) {
        setDetailsLevel(new Constant<>(detailsLevel));
    }

    /**
     * Set the details level
     * @param detailsLevel the details level
     */
    public void setDetailsLevel(IVariable<Double> detailsLevel) {
        checkPositiveAndNotNull(detailsLevel, DETAILS_LEVEL_SHOULD_BE_POSTIIVE_AND_NOT_NULL, true);
        animation.setDetailsLevel(detailsLevel);
    }

    /**
     * Set the font file name
     * @param fontFileName the font file name
     */
    public void setFontFileName(String fontFileName) {
        checkNotNull(fontFileName, FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY);
        Validate.notEmpty(fontFileName, FONT_FILE_NAME_SHOULD_NOT_BE_NULL_OR_EMPTY);
        animation.setFontFileName(fontFileName);
    }

    /**
     * Align the text to the center
     * @param alignCenter true to align the text to the center
     */
    public void setAlignCenter(boolean alignCenter) {
        animation.setAlignCenter(alignCenter);
    }
}
