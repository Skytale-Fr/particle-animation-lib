package fr.skytale.particleanimlib.animation.animation.obj.preset;

import fr.skytale.particleanimlib.animation.animation.obj.parser.ObjParsingUtils;
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

public class ObjPresetInitializer extends APresetInitializer {

    private static final String OBJ_FOLDER_NAME = ObjParsingUtils.MODELS_FOLDER;
    private static final String OBJ_FOLDER_PATH_IN_JAR = OBJ_FOLDER_NAME + "/";
    private static final String OBJ_FOLDER_CREATION_ERROR = "Error creating " + OBJ_FOLDER_NAME + " folder.";
    private static final String OBJ_EXTRACTION_ERROR =
            "Error extracting default obj into " + OBJ_FOLDER_NAME + " folder.";

    @Override
    protected void initialize(JavaPlugin plugin) {
        extractDefaultImages(plugin);
    }

    private void extractDefaultImages(JavaPlugin plugin) {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        final File objsFolder = createImagesFolder(plugin);
        if (jarFile.isFile() && objsFolder != null) {
            try {
                JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    final String name = jarEntry.getName();
                    if (!name.startsWith(OBJ_FOLDER_PATH_IN_JAR) || name.endsWith("/")) {
                        continue;
                    }
                    final File newFile = new File(objsFolder, StringUtils.substringAfterLast(name, "/"));
                    final InputStream inStream = ObjPresetInitializer.class.getResourceAsStream("/" + name);
                    FileUtils.copyInputStreamToFile(inStream, newFile);

                }
            } catch (IOException ex) {
                Bukkit.getLogger().log(Level.SEVERE, OBJ_EXTRACTION_ERROR, ex);
            }
        } else {
            Bukkit.getLogger().log(Level.SEVERE, OBJ_EXTRACTION_ERROR);
        }
    }

    private File createImagesFolder(JavaPlugin plugin) {
        File pluginDataFolder = plugin.getDataFolder();
        if (!pluginDataFolder.exists() || !pluginDataFolder.isDirectory()) {
            try {
                if (!pluginDataFolder.mkdir()) {
                    Bukkit.getLogger().log(Level.SEVERE, OBJ_FOLDER_CREATION_ERROR);
                    return null;
                }
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, OBJ_FOLDER_CREATION_ERROR, e);
                return null;
            }
        }

        File objsFolder = new File(pluginDataFolder, OBJ_FOLDER_NAME);
        if (!objsFolder.exists() || !objsFolder.isDirectory()) {
            try {
                if (objsFolder.mkdir()) {
                    return objsFolder;
                } else {
                    Bukkit.getLogger().log(Level.SEVERE, OBJ_FOLDER_CREATION_ERROR);
                }
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, OBJ_FOLDER_CREATION_ERROR, e);
            }
        } else if (objsFolder.exists() && objsFolder.isDirectory()) {
            return objsFolder;
        }
        return null;
    }

}
