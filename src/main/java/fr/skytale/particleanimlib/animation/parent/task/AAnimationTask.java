package fr.skytale.particleanimlib.animation.parent.task;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Objects;

public abstract class AAnimationTask<T extends AAnimation> implements Runnable {
    protected T animation;

    //Evolving variables
    protected Integer taskId;
    protected int iterationCount;

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

        APosition position = animation.getPosition();

        //Computing current animation location
        Location iterationBaseLocation;
        if (position.getType() == APosition.Type.ENTITY) {
            iterationBaseLocation = position.getMovingEntity().getLocation().clone()
                    .add(position.getRelativeLocation().getCurrentValue(iterationCount).clone());
        } else {
            iterationBaseLocation = position.getLocation().getCurrentValue(iterationCount).clone();
        }

        //We only show at the specified frequency
        Integer showPeriod = animation.getShowPeriod().getCurrentValue(iterationCount);
        if (showPeriod == 0 || iterationCount % showPeriod == 0) {
            show(iterationBaseLocation);
        }
        iterationCount++;
    }

    public abstract void show(Location iterationBaseLocation);

    /***Methods used in subclasses***/
    public boolean hasDurationEnded() {
        return iterationCount >= animation.getTicksDuration();
    }

    protected void stopAnimation() {
        stopAnimation(true);
    }

    protected void stopAnimation(boolean runCallback) {
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = null;
        }
        if (runCallback && animation.getCallback() != null) {
            animation.getCallback().run(animation);
        }
    }

    public void drawLine(Location point1, Location point2, double step) {
        double distance = point1.distance(point2);
        Vector stepVector = point2.toVector().subtract(point1.toVector()).normalize().multiply(step);
        Location currentLoc = point1.clone();
        final Collection<? extends Player> players = animation.getViewers().getPlayers(point1);
        for (double length = 0; length < distance; currentLoc.add(stepVector)) {
            animation.getMainParticle().getParticleBuilder(currentLoc).display(players);
            length += step;
        }
    }

    public void drawLine(Location point1, Location point2, double step, APointDefinition pointDefinition) {
        double distance = point1.distance(point2);
        Vector stepVector = point2.toVector().subtract(point1.toVector()).normalize().multiply(step);
        Location currentLoc = point1.clone();
        for (double length = 0; length < distance; currentLoc.add(stepVector)) {
            pointDefinition.show(animation, currentLoc);
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
    }

}
