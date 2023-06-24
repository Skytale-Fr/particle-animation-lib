package fr.skytale.particleanimlib.animation.animation.mandala;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

import java.util.List;

public class Mandala2DTask extends AAnimationTask<Mandala2D> {

    protected Mandala2DTask(Mandala2D animation) {
        super(animation);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        return null;
    }
}
