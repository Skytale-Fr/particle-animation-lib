package fr.skytale.particleanimlib.animation.parent.task;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.DirectionSubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.collision.CollisionHandler;
import fr.skytale.particleanimlib.animation.collision.CollisionTestType;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AAnimationTask<T extends AAnimation> implements Runnable {
    protected T animation;

    //Evolving variables
    protected Integer taskId;
    protected int iterationCount;

    protected boolean hasStopCondition;

    protected APosition currentPosition;
    protected Location currentIterationBaseLocation;
    protected int currentShowPeriod;

    public AAnimationTask(T animation) {
        //noinspection unchecked
        this.animation = (T) animation.clone();
        if (this.animation.getPosition().getType() == APosition.Type.TRAIL) {
            throw new IllegalStateException("During execution, position type should not be TRAIL anymore");
        }
        this.iterationCount = 0;
    }

    public static Vector computeRadiusVector(Vector normalVector, double radius) {
        /*Let directorVector=(a,b,c).
        Then the equation of the plane containing the point (0,0,0) with directorVector as normal vector is ax + by + cz = 0.
        We want to find the vector radiusVector belonging to the plane*/
        double a = normalVector.getX();
        double b = normalVector.getY();
        double c = normalVector.getZ();

        Vector radiusVector;

        if (a == 0) {
            if (b == 0)
                radiusVector = new Vector(1, 1, 0);
            else if (c == 0)
                radiusVector = new Vector(1, 0, 1);
            else
                radiusVector = new Vector(1, 1, -b / c);
        } else if (b == 0) {
            if (c == 0)
                radiusVector = new Vector(0, 1, 1);
            else
                radiusVector = new Vector(1, 1, -a / c);
        } else if (c == 0)
            radiusVector = new Vector(1, -b / a, 1);
        else
            radiusVector = new Vector(1, 1, (-a - b) / c);

        return radiusVector.normalize().multiply(radius);
    }

    protected final void startTask() {
        this.taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(animation.getPlugin(), this, 0, 0).getTaskId();
    }

    public final void run() {
        // If a stop condition has been set, we need to check this condition
        // and stop the animation if true is returned.
        if(this.animation.getStopCondition() != null && this.animation.getStopCondition().get()) {
            stopAnimation(true);
            return;
        }

        APosition currentPosition = animation.getPosition();

        //Computing current animation location
        if (currentPosition.getType() == APosition.Type.ENTITY) {
            currentIterationBaseLocation = currentPosition.getMovingEntity().getLocation().clone()
                    .add(currentPosition.getRelativeLocation().getCurrentValue(iterationCount).clone());
        } else {
            currentIterationBaseLocation = currentPosition.getLocation().getCurrentValue(iterationCount).clone();
        }

        //We only show at the specified frequency
        currentShowPeriod = animation.getShowPeriod().getCurrentValue(iterationCount);

        this.animation.getCollisionHandlers().forEach(collisionHandler -> {
            collisionHandler.collect(iterationCount, this);
        });

        if (currentShowPeriod == 0 || iterationCount % currentShowPeriod == 0) {
            show(currentIterationBaseLocation);
        }

        this.animation.getCollisionHandlers().forEach(collisionHandler -> {
            collisionHandler.processCollision(iterationCount, CollisionTestType.MAIN_PARTICLE, currentIterationBaseLocation, this);
        });


        iterationCount++;
    }

    public abstract void show(Location iterationBaseLocation);

    /***Methods used in subclasses***/
    public boolean hasDurationEnded() {
        return iterationCount >= animation.getTicksDuration();
    }

    public int getIterationCount() { return iterationCount; }
    public Location getCurrentIterationBaseLocation() { return currentIterationBaseLocation; }
    public int getCurrentShowPeriod() { return currentShowPeriod; }

    public void stopAnimation() {
        stopAnimation(true);
    }

    protected void stopAnimation(boolean runCallback) {
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = null;
            if (runCallback && !animation.getCallbacks().isEmpty()) {
                animation.getCallbacks().forEach(animationEndedCallback -> animationEndedCallback.run(animation));
            }
//            animation.getCollisionHandlers().forEach(CollisionHandler::clearTimestamps);
        }
    }

    public void drawLine(Location point1, Location point2, double step) {
        drawLine(point1, point2, step, APointDefinition.fromParticleTemplate(animation.getMainParticle()));
    }

    public void drawLine(Location point1, Location point2, double step, APointDefinition pointDefinition) {
        double distance = point1.distance(point2);
        Vector stepVector = point2.toVector().subtract(point1.toVector()).normalize().multiply(step);
        Location currentLoc = point1.clone();
        for (double length = 0; length < distance; currentLoc.add(stepVector)) {
            showPoint(pointDefinition, currentLoc.clone(), new Vector(0, 0, 0));
            length += step;
        }
    }

    public Location rotateAroundAxis(Location point, Vector axis, Location pointAxis, double angle) {
        Vector v = point.clone().subtract(pointAxis).toVector(); //Vecteur de axis au point à translater
        RotatableVector rotatableVector = new RotatableVector(v.getX(), v.getY(), v.getZ());
        v = rotatableVector.rotateAroundAxis(axis, angle);
        v = v.add(pointAxis.toVector()); //Translation vers le point d'origine (La rotiation a été faite à l'origine du repère)
        return v.toLocation(Objects.requireNonNull(point.getWorld()));
    }

    public void showPoint(APointDefinition pointDefinition, Location pointLocation, Location centerLocation) {
        showPoint(pointDefinition, pointLocation, pointLocation.toVector().subtract(centerLocation.toVector()));
    }

    public void showPoint(APointDefinition pointDefinition, Location pointLocation, Vector pointDirection) {
        if (pointDefinition.getShowMethodParameters() == APointDefinition.ShowMethodParameters.LOCATION) {
            pointDefinition.show(animation, pointLocation);
        } else {
            pointDefinition.show(animation, pointLocation, pointDirection);
        }

        this.animation.getCollisionHandlers().forEach(collisionHandler -> {
            collisionHandler.processCollision(iterationCount, CollisionTestType.PER_PARTICLE, pointLocation, this);
        });

    }


}
