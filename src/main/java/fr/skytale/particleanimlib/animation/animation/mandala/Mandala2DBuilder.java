package fr.skytale.particleanimlib.animation.animation.mandala;

import fr.skytale.particleanimlib.animation.attribute.curve.CurvePointsGenerator;
import fr.skytale.particleanimlib.animation.attribute.range.GeomSpaceRange;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.Arrays;
import java.util.List;

public class Mandala2DBuilder extends AAnimationBuilder<Mandala2D, Mandala2DTask> {

    public Mandala2DBuilder() {
        super();
        setPoints(
                Arrays.asList(
                        new Vector2D(0, 1),
                        new Vector2D(0, 2),
                        new Vector2D(0, 3),
                        new Vector2D(0, 4),
                        new Vector2D(0, 5)
                )
        );
        /**/
        setNbCircleSectionPairs(8);

    }

    @Override
    protected Mandala2D initAnimation() {
        return new Mandala2D();
    }

    @Override
    public Mandala2D getAnimation() {
        checkNotNull(animation.getPoints(), "points should not be null");
        checkPositive(animation.getNbCircleSections(), "nbCircleSectionPairs should be positive", false);
        return super.getAnimation();
    }

    public void setNbCircleSectionPairs(int nbCircleSectionPairs) {
        setNbCircleSectionPairs(new Constant<>(nbCircleSectionPairs));
    }

    public void setNbCircleSectionPairs(IVariable<Integer> nbCircleSectionPairs) {
        checkPositive(nbCircleSectionPairs, "nbCircleSectionPairs should be positive", false);
        animation.setNbCircleSections(nbCircleSectionPairs);
    }

    public void setPoints(List<Vector2D> points) {
        setPoints(new Constant<>(points));
    }

    public void setPoints(IVariable<List<Vector2D>> points) {
        checkNotNull(points, "points should not be null");
        animation.setPoints(points);
    }

    public void setPoints(CurvePointsGenerator<?, Vector2D> curve2DGenerator) {
        animation.setPoints(curve2DGenerator);
    }
}
