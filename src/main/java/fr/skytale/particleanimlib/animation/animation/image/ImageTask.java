package fr.skytale.particleanimlib.animation.animation.image;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.task.ARotatingAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ImageTask extends ARotatingAnimationTask<Image> {
    //Does particle type support color
    private final boolean hasColor;
    private final ParticleTemplate particleTemplate;
    public HashMap<Vector, Color> currentImagePixels;

    public ImageTask(Image image) {
        super(image);

        this.currentImagePixels = (HashMap<Vector, Color>) animation.getImagePixels().entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().clone(), e -> new Color(e.getValue().getRGB())));
        this.particleTemplate = this.animation.getMainParticle();
        this.hasColor = particleTemplate.getParticleEffect() == ParticleEffect.REDSTONE;

        startTask();
    }

    @Override
    public void showRotated(boolean changeRotation, Location iterationBaseLocation) {
        if (changeRotation) {
            currentImagePixels = (HashMap<Vector, Color>) currentImagePixels.entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> new RotatableVector(e.getKey()).rotateAroundAxis(
                                    animation.getRotationAxis().getCurrentValue(iterationCount),
                                    animation.getRotationAngleAlpha().getCurrentValue(iterationCount)),
                            Map.Entry::getValue));
        }

        currentImagePixels.forEach(((vector, color) -> {
            ParticleBuilder particleBuilder = particleTemplate.getParticleBuilder(iterationBaseLocation.clone().add(vector));
            if (hasColor) {
                particleBuilder.setColor(color);
            }
            particleBuilder.display();
        }));
    }

}
