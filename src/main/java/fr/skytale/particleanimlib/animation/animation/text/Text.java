package fr.skytale.particleanimlib.animation.animation.text;


import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;
import fr.skytale.ttfparser.TTFAlphabet;
import fr.skytale.ttfparser.TTFParser;
import fr.skytale.ttfparser.TTFString;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

public class Text extends AAnimation implements IPlaneSubAnimation, ISubAnimationContainer {

    public static final String FONTS_FOLDER = "fonts";

    private Vector u;
    private Vector v;
    private String baseString;
//    private IVariable<String> baseString;
//    private IVariable<Double> fontSize;
    private String fontFileName;
    // private FontDecoration fontDecoration
//    private TTFAlphabet ttfAlphabet;
    // A virer:
    private TTFString ttfString; // Built when init() is called.

    private double scaleX;
    private double scaleY;

    private APointDefinition pointDefinition;

    public Text() {
    }

    @Override
    public void show() {
        init();
        new TextTask(this);
    }

    protected void init() {
        if(ttfString != null) {
            // Already initialized.
            return;
        }

        File fontFile = getFontFile();
        TTFParser ttfParser = new TTFParser(fontFile);
        TTFAlphabet ttfAlphabet = ttfParser.parse();
        ttfString = ttfAlphabet.getString(baseString);
        ttfString = ttfString.scale(scaleX, scaleY);
    }

    public static File getFontsDirectory(JavaPlugin plugin) {
        File pluginDir = plugin.getDataFolder();
        if (!pluginDir.exists()) {
            boolean result = pluginDir.mkdir();
            if (!result) {
                throw new IllegalStateException("The plugin directory could not be created. It is probably a permission issue.");
            }
        }
        File fontsDir = new File(pluginDir, FONTS_FOLDER);
        if (!fontsDir.exists()) {
            boolean result = fontsDir.mkdir();
            if (!result) {
                throw new IllegalStateException("The " + FONTS_FOLDER + " directory could not be created. It is probably a permission issue.");
            }
        }
        return fontsDir;
    }

    public static File getFontFile(JavaPlugin plugin, String fontFileName) {
        File fontsDir = getFontsDirectory(plugin);

        File fontFile = new File(fontsDir, fontFileName);
        if (!fontFile.exists()) {
            throw new IllegalArgumentException(String.format("The image %s does not exist.", fontFile.getAbsolutePath()));
        }
        if (!fontFile.isFile()) {
            throw new IllegalArgumentException(String.format("%s should not be a directory.", fontFile.getAbsolutePath()));
        }
        return fontFile;
    }

    /***********GETTERS & SETTERS***********/

    public TTFString getTTFString() {
        return ttfString;
    }
    public String getBaseString() { return baseString; }

    public void setString(String string) {
        this.baseString = string;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public File getFontFile() {
        return getFontFile(plugin, fontFileName);
    }

    public String getFontFileName() {
        return fontFileName;
    }

    public void setFontFileName(String fontFileName) {
        this.fontFileName = fontFileName;
    }

    @Override
    public Vector getU() {
        return u;
    }

    @Override
    public void setU(Vector u) {
        this.u = u;
    }

    @Override
    public Vector getV() {
        return v;
    }

    @Override
    public void setV(Vector v) {
        this.v = v;
    }

    @Override
    public APointDefinition getPointDefinition() {
        return pointDefinition;
    }

    @Override
    public void setPointDefinition(APointDefinition pointDefinition) {
        this.pointDefinition = pointDefinition;
    }

    @Override
    public ParticleTemplate getMainParticle() {
        if (this.pointDefinition instanceof ParticlePointDefinition) {
            return ((ParticlePointDefinition) pointDefinition).getParticleTemplate();
        }
        throw new IllegalStateException("ParticleTemplate is not defined since this animation PointDefinition defines a sub animation");
    }

    @Override
    public void setMainParticle(ParticleTemplate mainParticle) {
        setPointDefinition(APointDefinition.fromParticleTemplate(mainParticle));
    }

    @Override
    public Text clone() {
        Text obj = (Text) super.clone();
        obj.ttfString = ttfString == null ? null : ttfString.clone();
        obj.baseString = baseString; // Immutable object
        obj.fontFileName = fontFileName;
        obj.u = u.clone();
        obj.v = v.clone();
        obj.scaleX = scaleX;
        obj.scaleY = scaleY;
        obj.pointDefinition = pointDefinition.clone();
        return obj;
    }

}
