package fr.skytale.particleanimlib.animation.animation.mandala;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mandala2DTask extends AAnimationTask<Mandala2D> {

    @IVariableCurrentValue
    private int nbCircleSections;

    @IVariableCurrentValue
    private List<Vector2D> points;

    protected Mandala2DTask(Mandala2D animation) {
        super(animation);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        double angleStep = Math.PI * 2 / nbCircleSections;
        return points.stream()
                .flatMap(point -> {
                    Stream.Builder<Vector> builder = Stream.builder();
                    builder.add(new Vector(point.getX(), 0, point.getY()));
                    for(int i = 1; i < nbCircleSections; i++) {
                        RotatableVector vector = new RotatableVector(point.getX(), 0, point.getY());
                        builder.add(vector.rotateAroundAxis(AAnimationTask.W, i * angleStep));
                    }
                    return builder.build();
                })
                .map(AnimationPointData::new)
                .collect(Collectors.toList());
    }
}
