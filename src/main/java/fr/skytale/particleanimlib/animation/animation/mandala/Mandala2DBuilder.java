package fr.skytale.particleanimlib.animation.animation.mandala;

import fr.skytale.particleanimlib.animation.attribute.curve.CurvePointsGenerator;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.Arrays;
import java.util.List;

public class Mandala2DBuilder extends AAnimationBuilder<Mandala2D, Mandala2DTask> {

    public Mandala2DBuilder() {
        super();
        animation.setPoints(
                Arrays.asList(
                        new Vector2D(0, 1),
                        new Vector2D(0, 2),
                        new Vector2D(0, 3),
                        new Vector2D(0, 4),
                        new Vector2D(0, 5)
                )
        );
        animation.setNbCircleSection(8);
    }

    @Override
    protected Mandala2D initAnimation() {
        return new Mandala2D();
    }

    @Override
    public Mandala2D getAnimation() {
        checkNotNull(animation.getPoints(), "points should not be null");
        checkPositive(animation.getNbCircleSection(), "nbCircleSectionPairs should be positive", false);
        return super.getAnimation();
    }

    /**
     * Defines the number of circle section pairs.
     * Each point will be displayed nbCircleSectionPairs times.
     *
     * @param nbCircleSectionPairs the number of circle section pairs
     */
    public void setNbCircleSection(int nbCircleSectionPairs) {
        setNbCircleSection(new Constant<>(nbCircleSectionPairs));
    }

    /**
     * Defines the number of circle section pairs.
     * Each point will be displayed nbCircleSectionPairs times.
     *
     * @param nbCircleSectionPairs the number of circle section pairs
     */
    public void setNbCircleSection(IVariable<Integer> nbCircleSectionPairs) {
        checkPositive(nbCircleSectionPairs, "nbCircleSectionPairs should be positive", false);
        animation.setNbCircleSection(nbCircleSectionPairs);
    }

    /**
     * Defines the points to show (and that will be shown nbCircleSectionPairs times).
     *
     * @param points the points to show
     */
    public void setPoints(List<Vector2D> points) {
        setPoints(new Constant<>(points));
    }


    /**
     * Defines the points to show (and that will be shown nbCircleSectionPairs times).
     * The fact it is a IVariable allows to change the points during the animation.
     * The usage of a {@link CurvePointsGenerator} is recommended.
     *
     * @param points the points to show
     */
    public void setPoints(IVariable<List<Vector2D>> points) {
        checkNotNull(points, "points should not be null");
        animation.setPoints(points);
    }

    /**
     * Defines the points to show (and that will be shown nbCircleSectionPairs times).
     *
     * @param curve2DGenerator every data needed to generate the points
     */
    public void setPoints(CurvePointsGenerator<?, Vector2D> curve2DGenerator) {
        animation.setPoints(curve2DGenerator);
    }
}
