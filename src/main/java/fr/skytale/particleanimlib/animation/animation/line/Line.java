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

    public void setPoint2(IVariable<Vector> point2) {
        IVariable<Vector> directionVector = new CallbackVariable<Vector>(iterationCount -> {
            Vector p1 = this.point1.getCurrentValue(iterationCount).clone();
            Vector p2 = point2.getCurrentValue(iterationCount).clone();
            return p2.subtract(p1).normalize();
        });
        this.direction = AnimationDirection.fromMoveVector(directionVector);

        this.length = new CallbackVariable<Double>(iterationCount -> {
            Vector p1 = this.point1.getCurrentValue(iterationCount).clone();
            Vector p2 = point2.getCurrentValue(iterationCount).clone();
            return p2.subtract(p1).length();
        });
    }

    @Override
    public AnimationDirection getDirection() {
        return direction;
    }

    @Override
    public void setDirection(AnimationDirection direction) {
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
        obj.direction = direction.clone();
        obj.nbPoints = nbPoints.copy();
        obj.length = length.copy();
        obj.pointDefinition = pointDefinition.clone();
        return obj;
    }
}
