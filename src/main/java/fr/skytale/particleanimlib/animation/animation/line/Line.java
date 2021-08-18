package fr.skytale.particleanimlib.animation.animation.line;


import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Line extends ARotatingAnimation implements IDirectionSubAnimation, ISubAnimationContainer {

    private AnimationDirection direction; // The direction of the line
    private IVariable<Integer> nbPoints; // The number of point on the line
    private IVariable<Double> length; // The length of the line
    private APointDefinition pointDefinition;

    // Possible bind end location
    // The line will always have this location has end location.
    // (this variable can be null)
    private IVariable<Location> bindEndLocation;

    public Line() {
    }

    @Override
    public void show() {
        new LineTask(this);
    }


    /***********GETTERS & SETTERS***********/

    public void bindEndLocation(IVariable<Location> location) {
        this.bindEndLocation = location;
    }
    public IVariable<Location> getBindEndLocation() {
        return bindEndLocation;
    }
    public boolean hasEndLocationBinded() {
        return bindEndLocation != null;
    }
    public void updateBindedEndLocation(int iterationCount) {
        // Nearly the same process as the one done in LineBuilde#setEndLocation(...)
        if(!hasEndLocationBinded()) return;

        // Compute the direction vector
        Location startLocation = getPosition().getLocation().getCurrentValue(iterationCount);
        Vector toVector = bindEndLocation.getCurrentValue(iterationCount).clone().subtract(startLocation).toVector();
        // Get it's length and update the animation's length
        double length = toVector.length();
        setLength(new Constant<>(length));

        // Normalize and set the direction
        Vector direction = toVector.normalize();
        setDirection(direction);
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
        this.direction = AnimationDirection.fromMoveVector(direction);
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
        obj.direction = direction.clone();
        obj.nbPoints = nbPoints.copy();
        obj.length = length.copy();
        obj.pointDefinition = pointDefinition.clone();
        return obj;
    }
}
