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

    @IVariableCurrentValue
    private Boolean axialSymmetryToHalf;

    protected Mandala2DTask(Mandala2D animation) {
        super(animation);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {

        final boolean invertHalfPointsFinal = Boolean.TRUE.equals(axialSymmetryToHalf);

        double angleStep = Math.PI * 2 / nbCircleSections;

        return points.stream()
                .flatMap(point -> {
                    Stream.Builder<Vector> builder = Stream.builder();
                    //player point
                    RotatableVector rotatableVector = new RotatableVector(point.getX(), 0, point.getY());
                    builder.add(rotatableVector.clone());

                    for (int i = 1; i < nbCircleSections; i++) {
                        //compute only first half if invertHalfPointsFinal is true because other points are symmetric
                        if (!invertHalfPointsFinal || i % 2 == 0) {
                            final Vector vector = rotatableVector.clone().rotateAroundAxis(AAnimationTask.W, i * angleStep);
                            builder.add(vector);
                        }
                    }
                    //compute the other half
                    if (invertHalfPointsFinal) {
                        return builder.build().flatMap(
                                vector -> Stream.of(
                                        vector,
                                        //axial symmetry
                                        new Vector(-vector.getX(), vector.getY(), vector.getZ())
                                )
                        );
                    }

                    return builder.build();
                })
                .map(AnimationPointData::new)
                .collect(Collectors.toList());
    }
}
