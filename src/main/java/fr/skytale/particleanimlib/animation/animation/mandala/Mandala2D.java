package fr.skytale.particleanimlib.animation.animation.mandala;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;

public class Mandala2D extends AAnimation {

    private IVariable<List<Vector2D>> points;

    private IVariable<Integer> nbCircleSections;

    private IVariable<Boolean> axialSymmetryToHalf;

    public Mandala2D() {
    }

    @Override
    public Mandala2DTask show() {
        return new Mandala2DTask(this);
    }

    public IVariable<List<Vector2D>> getPoints() {
        return points;
    }

    public void setPoints(List<Vector2D> points) {
        setPoints(new Constant<>(points));
    }

    public void setPoints(IVariable<List<Vector2D>> points) {
        this.points = points;
    }

    public IVariable<Integer> getNbCircleSection() {
        return nbCircleSections;
    }

    public void setNbCircleSection(Integer nbCircleSections) {
        setNbCircleSection(new Constant<>(nbCircleSections));
    }

    public void setNbCircleSection(IVariable<Integer> nbCircleSections) {
        this.nbCircleSections = nbCircleSections;
    }

    public IVariable<Boolean> getAxialSymmetryToHalf() {
        return axialSymmetryToHalf;
    }

    public void setAxialSymmetryToHalf(IVariable<Boolean> axialSymmetryToHalf) {
        this.axialSymmetryToHalf = axialSymmetryToHalf;
    }

    public void setAxialSymmetryToHalf(boolean axialSymmetryToHalf) {
        setAxialSymmetryToHalf(new Constant<>(axialSymmetryToHalf));
    }

    @Override
    public Mandala2D clone() {
        Mandala2D obj = (Mandala2D) super.clone();
        obj.nbCircleSections = nbCircleSections.copy();
        obj.points = points.copy();
        obj.axialSymmetryToHalf = axialSymmetryToHalf.copy();
        return obj;
    }
}
