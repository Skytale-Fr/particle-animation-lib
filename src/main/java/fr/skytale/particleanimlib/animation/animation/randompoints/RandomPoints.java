package fr.skytale.particleanimlib.animation.animation.randompoints;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

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
    private IVariable<Float> speed;

    public RandomPoints() {
    }

    @Override
    public RandomPointsTask show() {
        return new RandomPointsTask(this);
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

    public void setDirectionChangePeriod(IVariable<Integer> directionChangePeriod) {
        this.directionChangePeriod = directionChangePeriod;
    }

    public IVariable<Float> getSpeed() {
        return speed;
    }

    public void setSpeed(IVariable<Float> speed) {
        this.speed = speed;
    }
}
