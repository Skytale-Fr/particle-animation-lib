package fr.skytale.particleanimlib.animation.animation.mandala;

import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Mandala2DBuilder extends AAnimationBuilder<Mandala2D, Mandala2DTask> {

    public Mandala2DBuilder() {
        super();
        setPoint(new CallbackVariable<>(iterationCount -> ));

    }

    @Override
    protected Mandala2D initAnimation() {
        return new Mandala2D();
    }

    @Override
    public Mandala2D getAnimation() {
        checkNotNull(animation.getPoint(), "point should not be null");
        checkPositive(animation.getNbCircleSections(), "nbCircleSectionPairs should be positive", false);
        checkPositive(animation.getMinIterationCountForDisplayedPoint(), "minIterationCountForDisplayedPoint should be positive", true);
        checkSuperior(animation.getMaxIterationCountForDisplayedPoint(), animation.getMinIterationCountForDisplayedPoint(), "maxIterationCountForDisplayedPoint should be superior to minIterationCountForDisplayedPoint", true);
        return super.getAnimation();
    }

    public void setPoint(Vector2D point) {
        setPoint(new Constant<>(point));
    }

    public void setPoint(IVariable<Vector2D> point) {
        checkNotNull(point, "point should not be null");
        animation.setPoint(point);
    }

    public void setNbCircleSectionPairs(int nbCircleSectionPairs) {
        setNbCircleSectionPairs(new Constant<>(nbCircleSectionPairs));
    }

    public void setNbCircleSectionPairs(IVariable<Integer> nbCircleSectionPairs) {
        checkPositive(nbCircleSectionPairs, "nbCircleSectionPairs should be positive", false);
        animation.setNbCircleSections(nbCircleSectionPairs);
    }

    public void setMinIterationCountForDisplayedPoint(int minIterationCountForDisplayedPoint) {
        setMinIterationCountForDisplayedPoint(new Constant<>(minIterationCountForDisplayedPoint));
    }

    public void setMinIterationCountForDisplayedPoint(IVariable<Integer> minIterationCountForDisplayedPoint) {
        checkPositive(minIterationCountForDisplayedPoint, "minIterationCountForDisplayedPoint should be positive", true);
        animation.setMinIterationCountForDisplayedPoint(minIterationCountForDisplayedPoint);
    }

    public void setMaxIterationCountForDisplayedPoint(int maxIterationCountForDisplayedPoint) {
        setMaxIterationCountForDisplayedPoint(new Constant<>(maxIterationCountForDisplayedPoint));
    }

    public void setMaxIterationCountForDisplayedPoint(IVariable<Integer> maxIterationCountForDisplayedPoint) {
        checkSuperior(maxIterationCountForDisplayedPoint, animation.getMinIterationCountForDisplayedPoint(), "maxIterationCountForDisplayedPoint should be superior to minIterationCountForDisplayedPoint", true);
        animation.setMaxIterationCountForDisplayedPoint(maxIterationCountForDisplayedPoint);
    }
}
