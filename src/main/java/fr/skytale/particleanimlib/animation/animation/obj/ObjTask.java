package fr.skytale.particleanimlib.animation.animation.obj;

import fr.skytale.particleanimlib.animation.animation.obj.parser.ObjParsingService;
import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.Collectors;

public class ObjTask extends AAnimationTask<Obj> {

    private static ObjParsingService objParsingService = ObjParsingService.getInstance();

    private List<Vector> objPoints;

    public ObjTask(Obj obj) {
        super(obj);
        objParsingService.getObjPoints(obj).thenAccept(vectors -> {
            this.objPoints = vectors;
            startTask();
        });
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        return objPoints.stream()
                .map(AnimationPointData::new)
                .collect(Collectors.toList());
    }

}
