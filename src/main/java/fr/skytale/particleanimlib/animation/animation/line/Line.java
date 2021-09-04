package fr.skytale.particleanimlib.animation.animation.line;


import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;
import org.bukkit.util.Vector;

public class Line extends ARotatingAnimation implements IDirectionSubAnimation, ISubAnimationContainer {

    // Boundary vectors of the line.
    // Those vectors are relative from the base location
    // of the created animation.
    private IVariable<Vector> point1;
    private IVariable<Vector> point2;

    private IVariable<Integer> nbPoints; // The number of point on the line
    private APointDefinition pointDefinition;

    public Line() {
    }

    @Override
    public void show() {
        new LineTask(this);
    }


    /***********GETTERS & SETTERS***********/

    public IVariable<Vector> getPoint1() {
        return point1;
    }

    public void setPoint1(IVariable<Vector> point1) {
        this.point1 = point1;
    }

    public IVariable<Vector> getPoint2() {
        return point2;
    }

    public void setPoint2(IVariable<Vector> point2) {
        this.point2 = point2;
    }

    @Override
    public AnimationDirection getDirection() {
        IVariable<Vector> direction = new CallbackVariable<>(iterationCount -> {
           Vector p1 = point1.getCurrentValue(iterationCount).clone();
           Vector p2 = point2.getCurrentValue(iterationCount).clone();
           return p1.subtract(p2);
        });
        return AnimationDirection.fromMoveVector(direction);
    }

    @Override
    public void setDirection(AnimationDirection direction) {
        IVariable<Vector> newPoint2 = new CallbackVariable<>(iterationCount -> {
            double lengthValue = this.getLength().getCurrentValue(iterationCount);
            Vector directionVector = direction.getMoveVector().getCurrentValue(iterationCount).clone();
            Vector toVector = directionVector.normalize().multiply(lengthValue);
            Vector newPosition2 = this.point1.getCurrentValue(iterationCount).clone().add(toVector);
            return newPosition2.clone();
        });
        setPoint2(newPoint2);
    }

    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
    }

    public IVariable<Double> getLength() {
        return new CallbackVariable<>(iterationCount -> {
            Vector p1 = point1.getCurrentValue(iterationCount).clone();
            Vector p2 = point2.getCurrentValue(iterationCount).clone();
            return p1.subtract(p2).length();
        });
    }

    @Override
    public APointDefinition getPointDefinition() {
        return pointDefinition;
    }

    @Override
    public void setPointDefinition(APointDefinition pointDefinition) {
        this.pointDefinition = pointDefinition;
    }

    @Override
    public ParticleTemplate getMainParticle() {
        if (this.pointDefinition instanceof ParticlePointDefinition) {
            return ((ParticlePointDefinition) pointDefinition).getParticleTemplate();
        }
        throw new IllegalStateException("ParticleTemplate is not defined since this animation PointDefinition defines a sub animation");
    }

    @Override
    public void setMainParticle(ParticleTemplate mainParticle) {
        setPointDefinition(APointDefinition.fromParticleTemplate(mainParticle));
    }

    @Override
    public Line clone() {
        Line obj = (Line) super.clone();
        obj.point1 = point1.copy();
        obj.point2 = point2.copy();
        obj.nbPoints = nbPoints.copy();
        obj.pointDefinition = pointDefinition.clone();
        return obj;
    }
}
