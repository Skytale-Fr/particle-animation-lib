package fr.skytale.particleanimlib.animation.image;

import fr.skytale.particleanimlib.attributes.CustomVector;
import fr.skytale.particleanimlib.parent.ARotatingAnimationTask;
import org.bukkit.Bukkit;
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
    private boolean hasColor;
    public HashMap<Vector, Color> currentImagePixels;

    public ImageTask(Image image) {
        super(image);

        this.currentImagePixels = (HashMap<Vector, Color>) animation.getImagePixels().entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().clone(), e -> new Color(e.getValue().getRGB())));
        this.hasColor = this.mainParticle.getParticleEffect() == ParticleEffect.REDSTONE;

        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    @Override
    public void showRotated(Location iterationBaseLocation) {
        Location currentLocation = iterationBaseLocation;

        currentImagePixels.forEach(((vector, color) -> {
            ParticleBuilder particleBuilder = mainParticle.getParticleBuilder(currentLocation.clone().add(vector));
            if (hasColor) {
                particleBuilder.setColor(color);
            }
            particleBuilder.display();
        }));
    }

    @Override
    protected void computeRotation(Vector currentAxis, double currentStepAngleAlpha) {
        currentImagePixels = (HashMap<Vector, Color>) currentImagePixels.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> new CustomVector(e.getKey()).rotateAroundAxis(currentAxis, currentStepAngleAlpha), Map.Entry::getValue));
    }

}
