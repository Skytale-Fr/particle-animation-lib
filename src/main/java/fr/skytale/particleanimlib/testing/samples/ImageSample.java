package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.attributes.var.Constant;
import fr.skytale.particleanimlib.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
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

public class ImageSample implements IPAnimSample {

    private static final String IMAGES_FOLDER_NAME = "images";
    private static final String IMAGES_FOLDER_PATH_IN_JAR = IMAGES_FOLDER_NAME + "/";
    private static final String IMAGES_FOLDER_CREATION_ERROR = "Error creating " + IMAGES_FOLDER_NAME + " folder.";
    private static final String IMAGES_EXTRACTION_ERROR = "Error extracting default image into " + IMAGES_FOLDER_NAME + " folder.";

    private boolean hasBeenInitialized = false;

    @Override
    public AAnimationBuilder<?> getInitializedBuilder(APosition position, JavaPlugin plugin) {
        if (!hasBeenInitialized) {
            extractDefaultImages();
            hasBeenInitialized = true;
        }
        ImageBuilder imageBuilder = new ImageBuilder();
        if (position.getType() == APosition.Type.ENTITY) {
            imageBuilder.setPosition(APosition.fromEntity(position.getMovingEntity(), new Constant<>(new Vector(6, 6, 0))));
        } else {
            imageBuilder.setPosition(position);
        }
        imageBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(Math.PI / 12));
        imageBuilder.setDirectorVectors(new Vector(0, 0, 0.2), new Vector(0, 0.2, 0));
        imageBuilder.setImageFileName("skytale.png");

        imageBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        imageBuilder.setTicksDuration(400);
        imageBuilder.setShowFrequency(new Constant<>(5));
        imageBuilder.setJavaPlugin(plugin);

        return imageBuilder;
    }

    @Override
    public String getName() {
        return "image";
    }

    private File createImagesFolder() {
        File pluginDataFolder = ParticleAnimLibTest.getInstance().getPlugin().getDataFolder();
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
            Bukkit.getLogger().log(Level.SEVERE, IMAGES_EXTRACTION_ERROR);
        }
    }
}
