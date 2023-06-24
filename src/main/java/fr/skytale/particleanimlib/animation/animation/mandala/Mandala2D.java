package fr.skytale.particleanimlib.animation.animation.mandala;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.function.Function;

public class Mandala2D<T> extends AAnimation {

   /*

    public static class CurveDisplay<T,R> {
        private Function<T, R> generator;
        private IVariable<Range<T>> range;
    }

    public static plop() {
        Mandala2DBuilder builder = new Mandala2DBuilder();

        final int N = 50; // point count
        final double alpha = 3.41; // arbitrary

        builder.setPatrick(
            (params) -> {

                    // params.x = r
                    // params.y = theta
                    return Vector2D(Math.cos(params.y) * params.x * Math.sin(params.y), params.y);
            },
            Range.composite(
                    Range.fixed(alpha, N), // return a set containing alpha N times
                    Range.linspace(0.0, Math.PI * 2.0, N)
            );


                [
                    [ alpha, 0],
                    [ alpha, Math.PI * 2 / 50],
                    [ alpha, Math.PI * 2 / 50 * 2],
                    [ alpha, Math.PI * 2 / 50 * 3],
                    ...
                    [ alpha, Math.PI * 2 / 50 * 49]
                ]

            ]

        )
    }
*/


    private Function<Double, Vector2D> point;
    private IVariable<Integer> nbCircleSections;
    private IVariable<Double> minIterationCountForDisplayedPoint;
    private IVariable<Double> maxIterationCountForDisplayedPoint;
    private IVariable<Double> stepBetweenEachPoint;

    public Mandala2D() {
    }

    @Override
    public Mandala2DTask show() {
        return new Mandala2DTask(this);
    }

    @Override
    public Mandala2D clone() {
        Mandala2D obj = (Mandala2D) super.clone();
        obj.point = point;
        obj.nbCircleSections = nbCircleSections.copy();
        obj.minIterationCountForDisplayedPoint = minIterationCountForDisplayedPoint.copy();
        obj.maxIterationCountForDisplayedPoint = maxIterationCountForDisplayedPoint.copy();
        return obj;
    }

    public IVariable<Vector2D> getPoint() {
        return point;
    }

    public void setPoint(IVariable<Vector2D> point) {
        this.point = point;
    }

    public IVariable<Integer> getNbCircleSections() {
        return nbCircleSections;
    }

    public void setNbCircleSections(IVariable<Integer> nbCircleSections) {
        this.nbCircleSections = nbCircleSections;
    }

    public IVariable<Integer> getMinIterationCountForDisplayedPoint() {
        return minIterationCountForDisplayedPoint;
    }

    public void setMinIterationCountForDisplayedPoint(IVariable<Integer> minIterationCountForDisplayedPoint) {
        this.minIterationCountForDisplayedPoint = minIterationCountForDisplayedPoint;
    }

    public IVariable<Integer> getMaxIterationCountForDisplayedPoint() {
        return maxIterationCountForDisplayedPoint;
    }

    public void setMaxIterationCountForDisplayedPoint(IVariable<Integer> maxIterationCountForDisplayedPoint) {
        this.maxIterationCountForDisplayedPoint = maxIterationCountForDisplayedPoint;
    }
}
