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

    // TODO:
    // 1. Definition des deux points (positions relatives) définie par IVariable<Vector>
    // 2. Rotation par l'object rotation (yaw, pitch, roll) -> voir Discord

    // Those properties are not important to be "cached" but it might
    // be important for optimization efforts ...
    // (instead of computing them every time getDirection() or getLength() is called,
    //  we can compute them every time setDirection(...) or setLength(...) is called).
    private AnimationDirection direction; // The direction of the line
    private IVariable<Double> length; // The length of the line

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
        updateAttributes();
    }

    public IVariable<Vector> getPoint2() {
        return point2;
    }

    public void setPoint2(IVariable<Vector> point2) {
        this.point2 = point2;
        updateAttributes();
    }

    /**
     * Updates length and direction attributes
     */
    private void updateAttributes() {
        IVariable<Vector> newDirection = new CallbackVariable<>(iterationCount -> {
            Vector point1Value = point1.getCurrentValue(iterationCount);
            Vector point2Value = point2.getCurrentValue(iterationCount);
            Vector directionVector = point2Value.clone().subtract(point1Value);
            return directionVector;
        });
        this.direction = AnimationDirection.fromMoveVector(newDirection);

        IVariable<Double> newLength = new CallbackVariable<>(iterationCount -> {
            double length = direction.getMoveVector().getCurrentValue(iterationCount).length();
            return length;
        });
        this.length = newLength;
    }

    @Override
    public AnimationDirection getDirection() {
        return direction;
    }

    @Override
    public void setDirection(AnimationDirection direction) {
        // Question for the reviewer:
        // Which of the following statement is correct ?
        //    I think the first can be better over time and is handling
        //    variable evolution (of length, point1 and direction).

        // Statement 1:
        IVariable<Vector> newPoint2 = new CallbackVariable<>(iterationCount -> {
            double lengthValue = length.getCurrentValue(iterationCount);
            Vector directionVector = direction.getMoveVector().getCurrentValue(iterationCount).clone();
            Vector toVector = directionVector.normalize().multiply(lengthValue);
            Vector newPosition2 = point1.getCurrentValue(iterationCount).clone().add(toVector);
            return newPosition2;
        });
        this.point2 = newPoint2;

        // Statement 2:
//        double lengthValue = length.getCurrentValue(0); // Is this statement have any sense ?
//        Vector directionVector = direction.getMoveVector().getCurrentValue(0).clone();
//        Vector toVector = directionVector.normalize().multiply(lengthValue);
//        Vector newPoint2 = point1.getCurrentValue(0).clone().add(toVector);
//        this.point2 = new Constant<>(newPoint2);


        this.direction = direction;
    }

    public void setDirection(Vector direction) {
        AnimationDirection animationDirection = AnimationDirection.fromMoveVector(direction);
        setDirection(animationDirection);
    }

    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
    }

    public IVariable<Double> getLength() {
        return length;
    }

    public void setLength(IVariable<Double> length) {
        // Same question as in setDirection(AnimationDirection)

        IVariable<Vector> newPoint2 = new CallbackVariable<>(iterationCount -> {
            double lengthValue = length.getCurrentValue(iterationCount);
            Vector directionVector = direction.getMoveVector().getCurrentValue(iterationCount).clone();
            Vector toVector = directionVector.normalize().multiply(lengthValue);
            Vector newPosition2 = point1.getCurrentValue(iterationCount).clone().add(toVector);
            return newPosition2;
        });
        this.point2 = newPoint2;

        this.length = length;
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
        obj.direction = direction.clone();
        obj.nbPoints = nbPoints.copy();
        obj.length = length.copy();
        obj.pointDefinition = pointDefinition.clone();
        return obj;
    }
}
