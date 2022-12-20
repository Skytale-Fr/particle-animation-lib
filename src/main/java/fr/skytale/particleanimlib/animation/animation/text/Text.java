package fr.skytale.particleanimlib.animation.animation.text;


import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.ttfparser.TTFAlphabet;
import fr.skytale.ttfparser.TTFParser;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Text extends AAnimation implements ISubAnimation {

    public static final String FONTS_FOLDER = "fonts";

    private IVariable<String> baseString;
    private IVariable<Double> fontSize;
    private IVariable<Double> detailsLevel;
    private String fontFileName;
    // private FontDecoration fontDecoration;
    private TTFAlphabet ttfAlphabet;
    // private Anchor anchor;

    public Text() {
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

    @Override
    public TextTask show() {
        init();
        return new TextTask(this);
    }

    @Override
    public Text clone() {
        Text obj = (Text) super.clone();
        obj.ttfAlphabet = ttfAlphabet == null ? null : ttfAlphabet; // Clone a TTFAlphabet seems to be weird (there is only getters).
        obj.baseString = baseString.copy();
        obj.fontSize = fontSize.copy();
        obj.fontFileName = fontFileName;
        obj.detailsLevel = detailsLevel.copy();
        return obj;
    }

    protected void init() {
        if (ttfAlphabet != null) {
            // Already initialized.
            return;
        }

        File fontFile = getFontFile();
        TTFParser ttfParser = new TTFParser(fontFile);
        ttfAlphabet = ttfParser.parse();
    }

    /***********GETTERS & SETTERS***********/

    public TTFAlphabet getTTFAlphabet() {
        return ttfAlphabet;
    }

    public String getFontFileName() {
        return fontFileName;
    }

    public void setFontFileName(String fontFileName) {
        this.fontFileName = fontFileName;
    }

    public File getFontFile() {
        return getFontFile(plugin, fontFileName);
    }

    public IVariable<String> getBaseString() {
        return baseString;
    }

    public void setString(IVariable<String> string) {
        this.baseString = string;
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

}
