package fr.skytale.particleanimlib.animation.animation.line;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.animation.parent.task.AnimationTaskUtils;
import org.bukkit.util.Vector;

import java.util.List;

public class LineTask extends AAnimationTask<Line> {

    @IVariableCurrentValue
    private Integer nbPoints;

    @IVariableCurrentValue
    private Vector fromPositionToPoint1;

    @IVariableCurrentValue
    private Vector fromPositionToPoint2;

    public LineTask(Line line) {
        super(line);
        this.startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        return AnimationTaskUtils.getLinePoints(fromPositionToPoint1, fromPositionToPoint2, nbPoints);
    }

}