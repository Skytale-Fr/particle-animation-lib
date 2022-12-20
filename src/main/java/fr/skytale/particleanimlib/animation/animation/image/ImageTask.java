package fr.skytale.particleanimlib.animation.animation.image;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImageTask extends AAnimationTask<Image> {
    //Does particle type support color

    public ImageTask(Image image) {
        super(image);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        return animation.getImagePixels().entrySet().stream()
                .map(vectorColorEntry -> new AnimationPointData(
                        vectorColorEntry.getKey(),
                        AnimationPointData.getPointModifierForColor(vectorColorEntry.getValue())
                ))
                .collect(Collectors.toList());
    }

}
