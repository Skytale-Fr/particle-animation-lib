package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import fr.skytale.particleanimlib.testing.attributes.AnimationType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class ImageSample implements IParticleAnimSample {

    private static final String IMAGES_FOLDER_NAME = "images";
    private static final String IMAGES_FOLDER_PATH_IN_JAR = IMAGES_FOLDER_NAME + "/";
    private static final String IMAGES_FOLDER_CREATION_ERROR = "Error creating " + IMAGES_FOLDER_NAME + " folder.";
    private static final String IMAGES_EXTRACTION_ERROR = "Error extracting default image into " + IMAGES_FOLDER_NAME + " folder.";

    private boolean hasBeenInitialized = false;

    @Override
    public AAnimationBuilder<?> getInitializedBuilder(Player player, JavaPlugin plugin) {
        if (!hasBeenInitialized) {
            extractDefaultImages();
            hasBeenInitialized = true;
        }
        ImageBuilder imageBuilder = new ImageBuilder();
        imageBuilder.setAxis(new Vector(0, 1, 0));
        imageBuilder.setAxisChangeFrequency(0);
        imageBuilder.setStepAngleAlpha(Math.toRadians(3));
        imageBuilder.setStepAngleAlphaMax(Math.toRadians(30));
        imageBuilder.setStepAngleAlphaChangeFactor(2);
        imageBuilder.setStepAngleAlphaChangeFrequency(0);
        imageBuilder.setDirectorVectors(new Vector(0, 0, 0.2), new Vector(0, 0.2, 0));
        imageBuilder.setMovingEntity(player);
        imageBuilder.setRelativeLocation(new Vector(6, 0, 0));
        imageBuilder.setImageFileName("skytale.png");

        imageBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        imageBuilder.setTicksDuration(400);
        imageBuilder.setShowFrequency(5);
        imageBuilder.setJavaPlugin(plugin);

        return imageBuilder;
    }

    @Override
    public AnimationType getType() {
        return AnimationType.IMAGE;
    }

    private File createImagesFolder() {
        File pluginDataFolder = ParticleAnimLibTest.getInstance().getPlugin().getDataFolder();
        if (!pluginDataFolder.exists() && !pluginDataFolder.isDirectory()) {
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
        if (!imagesFolder.exists() && !imagesFolder.isDirectory()) {
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

    private void extractDefaultImages() {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        final File imagesFolder = createImagesFolder();
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
                    final InputStream inStream = ImageSample.class.getResourceAsStream("/" + name);
                    FileUtils.copyInputStreamToFile(inStream, newFile);

                }
            } catch (IOException ex) {
                Bukkit.getLogger().log(Level.SEVERE, IMAGES_EXTRACTION_ERROR, ex);
            }
        } else {

        }
    }
}
