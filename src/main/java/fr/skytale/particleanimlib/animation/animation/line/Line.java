package fr.skytale.particleanimlib.animation.animation.line;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.util.Vector;

public class Line extends AAnimation {

    private IVariable<Vector> fromPositionToPoint1; // First point of the line.
    private IVariable<Vector> fromPositionToPoint2; // Second point of the line
    private IVariable<Integer> nbPoints; // The number of point on the line

    public Line() {
    }

    @Override
    public LineTask show() {
        return new LineTask(this);
    }

    @Override
    public Line clone() {
        Line obj = (Line) super.clone();
        obj.fromPositionToPoint1 = fromPositionToPoint1.copy();
        obj.fromPositionToPoint2 = fromPositionToPoint2.copy();
        obj.nbPoints = nbPoints.copy();
        return obj;
    }

    /***********GETTERS & SETTERS***********/


    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
    }

    public IVariable<Vector> getFromPositionToPoint1() {
        return fromPositionToPoint1;
    }

    public void setFromPositionToPoint1(IVariable<Vector> fromPositionToPoint1) {
        this.fromPositionToPoint1 = fromPositionToPoint1;
    }

    public IVariable<Vector> getFromPositionToPoint2() {
        return fromPositionToPoint2;
    }

    public void setFromPositionToPoint2(IVariable<Vector> fromPositionToPoint2) {
        this.fromPositionToPoint2 = fromPositionToPoint2;
    }
}
