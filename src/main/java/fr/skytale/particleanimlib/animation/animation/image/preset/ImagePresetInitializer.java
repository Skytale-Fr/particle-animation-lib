package fr.skytale.particleanimlib.animation.animation.image.preset;

import fr.skytale.particleanimlib.animation.animation.image.Image;
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

public class ImagePresetInitializer extends APresetInitializer {

    private static final String IMAGES_FOLDER_NAME = Image.IMAGES_FOLDER;
    private static final String IMAGES_FOLDER_PATH_IN_JAR = IMAGES_FOLDER_NAME + "/";
    private static final String IMAGES_FOLDER_CREATION_ERROR = "Error creating " + IMAGES_FOLDER_NAME + " folder.";
    private static final String IMAGES_EXTRACTION_ERROR = "Error extracting default image into " + IMAGES_FOLDER_NAME + " folder.";

    @Override
    protected void initialize(JavaPlugin plugin) {
        extractDefaultImages(plugin);
    }

    private void extractDefaultImages(JavaPlugin plugin) {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        final File imagesFolder = createImagesFolder(plugin);
        if (jarFile.isFile() && imagesFolder != null) {
            try {
                JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    final String name = jarEntry.getName();
                    if (!name.startsWith(IMAGES_FOLDER_PATH_IN_JAR) || name.endsWith("/")) {
                        continue;
                    }
                    final File newFile = new File(imagesFolder, StringUtils.substringAfterLast(name, "/"));
                    final InputStream inStream = ImagePresetInitializer.class.getResourceAsStream("/" + name);
                    FileUtils.copyInputStreamToFile(inStream, newFile);

                }
            } catch (IOException ex) {
                Bukkit.getLogger().log(Level.SEVERE, IMAGES_EXTRACTION_ERROR, ex);
            }
        } else {
            Bukkit.getLogger().log(Level.SEVERE, IMAGES_EXTRACTION_ERROR);
        }
    }

    private File createImagesFolder(JavaPlugin plugin) {
        File pluginDataFolder = plugin.getDataFolder();
        if (!pluginDataFolder.exists() || !pluginDataFolder.isDirectory()) {
            try {
                if (!pluginDataFolder.mkdir()) {
                    Bukkit.getLogger().log(Level.SEVERE, IMAGES_FOLDER_CREATION_ERROR);
                    return null;
                }
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, IMAGES_FOLDER_CREATION_ERROR, e);
                return null;
            }
        }

        File imagesFolder = new File(pluginDataFolder, IMAGES_FOLDER_NAME);
        if (!imagesFolder.exists() || !imagesFolder.isDirectory()) {
            try {
                if (imagesFolder.mkdir()) {
                    return imagesFolder;
                } else {
                    Bukkit.getLogger().log(Level.SEVERE, IMAGES_FOLDER_CREATION_ERROR);
                }
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, IMAGES_FOLDER_CREATION_ERROR, e);
            }
        } else if (imagesFolder.exists() && imagesFolder.isDirectory()) {
            return imagesFolder;
        }
        return null;
    }

}
