package fr.skytale.particleanimlib.animation.animation.randompoints;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

import java.util.List;

//TODO:
// - replace radius & nbPoints by IVariable<ParticlesToSpawn>
// - Add a IVariable<IArea> boundingArea
// - creates a RandomPointsInSphereSpawner class that extends CallbackVariable<ParticlesToSpawn>
// - rename RandomPoints en RandomlyMovingPoints
public class RandomPoints extends AAnimation {

    /**
     * Where to spawn and move the points
     */
    private float radius;

    /**
     * The number of points
     */
    private int nbPoints;

    /**
     * The period between 2 direction change
     */
    private IVariable<Integer> directionChangePeriod;

    /**
     * The points move speed
     */
    private IVariable<Double> speed;

    public RandomPoints() {
    }

    @Override
    public RandomPointsTask show() {
        return new RandomPointsTask(this);
    }

    public RandomPointsTask show(boolean pointsSpawned, List<RandomPointsTask.RandomPointData> randomPointsData) {
        return new RandomPointsTask(this, pointsSpawned, randomPointsData);
    }

    @Override
    public RandomPoints clone() {
        RandomPoints obj = (RandomPoints) super.clone();
        obj.radius = radius;
        obj.nbPoints = nbPoints;
        obj.directionChangePeriod = directionChangePeriod.copy();
        obj.speed = speed.copy();
        return obj;
    }

    /***********GETTERS & SETTERS***********/

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public IVariable<Integer> getDirectionChangePeriod() {
        return directionChangePeriod;
    }

    public void setDirectionChangePeriod(Integer directionChangePeriod) {
        setDirectionChangePeriod(new Constant<>(directionChangePeriod));
    }

    public void setDirectionChangePeriod(IVariable<Integer> directionChangePeriod) {
        this.directionChangePeriod = directionChangePeriod;
    }

    public IVariable<Double> getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        setSpeed(new Constant<>(speed));
    }

    public void setSpeed(IVariable<Double> speed) {
        this.speed = speed;
    }
}
