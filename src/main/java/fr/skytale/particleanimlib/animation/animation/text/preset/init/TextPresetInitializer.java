package fr.skytale.particleanimlib.animation.animation.text.preset.init;

import fr.skytale.particleanimlib.animation.animation.text.parser.FontParsingUtils;
import fr.skytale.particleanimlib.animation.parent.preset.APresetInitializer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class TextPresetInitializer extends APresetInitializer {

    private static final String FONT_FOLDER_NAME = FontParsingUtils.FONTS_FOLDER;
    private static final String FONT_FOLDER_PATH_IN_JAR = FONT_FOLDER_NAME + "/";
    private static final String FONT_FOLDER_CREATION_ERROR = "Error creating " + FONT_FOLDER_NAME + " folder.";
    private static final String FONT_EXTRACTION_ERROR =
            "Error extracting default font into " + FONT_FOLDER_NAME + " folder.";

    @Override
    protected void initialize(JavaPlugin plugin) {
        extractDefaultFont(plugin);
    }

    private void extractDefaultFont(JavaPlugin plugin) {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        final File fontsFolder = createFontFolder(plugin);
        if (jarFile.isFile() && fontsFolder != null) {
                final Enumeration<JarEntry> entries;
                try (JarFile jar = new JarFile(jarFile)) {
                    entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        final String name = jarEntry.getName();
                        if (!name.startsWith(FONT_FOLDER_PATH_IN_JAR) || name.endsWith("/")) {
                            continue;
                        }
                        final File newFile = new File(fontsFolder, StringUtils.substringAfterLast(name, "/"));
                        final InputStream inStream = TextPresetInitializer.class.getResourceAsStream("/" + name);
                        FileUtils.copyInputStreamToFile(inStream, newFile);
                    }
                } catch (IOException ex) {
                    Bukkit.getLogger().log(Level.SEVERE, FONT_EXTRACTION_ERROR, ex);
                }
        } else {
            Bukkit.getLogger().log(Level.SEVERE, FONT_EXTRACTION_ERROR);
        }
    }

    private File createFontFolder(JavaPlugin plugin) {
        File pluginDataFolder = plugin.getDataFolder();
        if (!pluginDataFolder.exists() || !pluginDataFolder.isDirectory()) {
            try {
                if (!pluginDataFolder.mkdir()) {
                    Bukkit.getLogger().log(Level.SEVERE, FONT_FOLDER_CREATION_ERROR);
                    return null;
                }
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, FONT_FOLDER_CREATION_ERROR, e);
                return null;
            }
        }

        File fontsFolder = new File(pluginDataFolder, FONT_FOLDER_NAME);
        if (!fontsFolder.exists() || !fontsFolder.isDirectory()) {
            try {
                if (fontsFolder.mkdir()) {
                    return fontsFolder;
                } else {
                    Bukkit.getLogger().log(Level.SEVERE, FONT_FOLDER_CREATION_ERROR);
                }
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, FONT_FOLDER_CREATION_ERROR, e);
            }
        } else if (fontsFolder.exists() && fontsFolder.isDirectory()) {
            return fontsFolder;
        }
        return null;
    }

}
