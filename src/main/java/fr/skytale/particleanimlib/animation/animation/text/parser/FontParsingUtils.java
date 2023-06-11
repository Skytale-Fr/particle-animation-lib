package fr.skytale.particleanimlib.animation.animation.text.parser;

import fr.skytale.ttfparser.TTFAlphabet;
import fr.skytale.ttfparser.TTFParser;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FontParsingUtils {
    public static final String FONTS_FOLDER = "fonts";

    public static TTFAlphabet buildTTFAlphabet(JavaPlugin plugin, String fontFileName) {
        return new TTFParser(getFontFile(plugin, fontFileName)).parse();
    }

    private static File getFontsDirectory(JavaPlugin plugin) {
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
                throw new IllegalStateException(
                        "The " + FONTS_FOLDER + " directory could not be created. It is probably a permission issue.");
            }
        }
        return fontsDir;
    }

    private static File getFontFile(JavaPlugin plugin, String fontFileName) {
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

    private FontParsingUtils(){};
}
