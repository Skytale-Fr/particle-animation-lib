package fr.skytale.particleanimlib.animation.animation.image;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.ForceUpdatePointsConfiguration;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

import java.util.List;
import java.util.stream.Collectors;

@ForceUpdatePointsConfiguration
public class ImageTask extends AAnimationTask<Image> {

    @IVariableCurrentValue
    private Float scale;


    public ImageTask(Image image) {
        super(image);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        return animation.getImagePixels().entrySet().stream()
                .map(vectorColorEntry -> new AnimationPointData(
                        vectorColorEntry.getKey().clone().multiply(scale),
                        vectorColorEntry.getValue()
                ))
                .collect(Collectors.toList());
    }


}
