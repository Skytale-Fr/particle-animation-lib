package fr.skytale.particleanimlib.animation.animation.text;


import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

public class Text extends AAnimation {

    private IVariable<String> text;
    private IVariable<Double> fontSize;
    private IVariable<Double> detailsLevel;
    private String fontFileName;
    private boolean alignCenter;

    public Text() {
    }


    @Override
    public TextTask show() {
        return new TextTask(this);
    }

    @Override
    public Text clone() {
        Text obj = (Text) super.clone();
        obj.text = text.copy();
        obj.fontSize = fontSize.copy();
        obj.fontFileName = fontFileName;
        obj.detailsLevel = detailsLevel.copy();
        return obj;
    }

    /***********GETTERS & SETTERS***********/

    public String getFontFileName() {
        return fontFileName;
    }

    public void setFontFileName(String fontFileName) {
        this.fontFileName = fontFileName;
    }

    public IVariable<String> getText() {
        return text;
    }

    public void setText(IVariable<String> string) {
        this.text = string;
    }

    public IVariable<Double> getFontSize() {
        return fontSize;
    }

    public void setFontSize(IVariable<Double> fontSize) {
        this.fontSize = fontSize;
    }

    public IVariable<Double> getDetailsLevel() {
        return detailsLevel;
    }

    public void setDetailsLevel(IVariable<Double> detailsLevel) {
        this.detailsLevel = detailsLevel;
    }

    public boolean isAlignCenter() {
        return alignCenter;
    }

    public void setAlignCenter(boolean alignCenter) {
        this.alignCenter = alignCenter;
    }
}
