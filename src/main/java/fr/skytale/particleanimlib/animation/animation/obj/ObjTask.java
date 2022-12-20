package fr.skytale.particleanimlib.animation.animation.obj;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

import java.util.List;
import java.util.stream.Collectors;

public class ObjTask extends AAnimationTask<Obj> {

    public ObjTask(Obj image) {
        super(image);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        return animation.getObjPoints().stream()
                .map(AnimationPointData::new)
                .collect(Collectors.toList());
    }

}
