package fr.skytale.particleanimlib.animation.animation.pyramid;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;

public class PyramidBuilder extends AAnimationBuilder<Pyramid, PyramidTask> {

    public PyramidBuilder() {
        super();
        animation.setDistanceBetweenParticles(new Constant<>(0.3));
        animation.setShowPeriod(new Constant<>(0));
        animation.setTicksDuration(60);
        animation.setNbBaseApex(new Constant<>(3));
        animation.setFromCenterToApex(new Constant<>(new Vector(0, 5, 0)));
        animation.setDistanceFromCenterToAnyBaseVertex(new Constant<>(3.0));
    }

    @Override
    protected Pyramid initAnimation() {
        return new Pyramid();
    }

    @Override
    public Pyramid getAnimation() {
        return super.getAnimation();
    }

    /********* Pyramid specific setters ***********/

    /**
     * Set the apex point of the pyramid according to a vector from the animation center to this apex
     * @param fromCenterToApex the vector from the animation center to the apex point
     */
    public void setFromCenterToApex(IVariable<Vector> fromCenterToApex) {
        checkNotNull(fromCenterToApex, "Apex point of pyramid has to be defined.");
        animation.setFromCenterToApex(fromCenterToApex);
    }

    /**
     * Set the apex point of the pyramid according to a vector from the animation center to this apex
     * @param fromCenterToApex the vector from the animation center to the apex point
     */
    public void setFromCenterToApex(Vector fromCenterToApex) {
        setFromCenterToApex(new Constant<>(fromCenterToApex));
    }

    /**
     * Set the number of base apex of the pyramid
     * @param nbBaseApex the number of base apex of the pyramid
     */
    public void setNbBaseApex(IVariable<Integer> nbBaseApex) {
        if (nbBaseApex.isConstant() && nbBaseApex.getCurrentValue(0) <= 2)
            throw new IllegalArgumentException("A pyramid should have at least 3 base apex.");
        animation.setNbBaseApex(nbBaseApex);
    }

    /**
     * Set the number of base apex of the pyramid
     * @param nbBaseApex the number of base apex of the pyramid
     */
    public void setNbBaseApex(int nbBaseApex) {
        setNbBaseApex(new Constant<>(nbBaseApex));
    }

    /**
     * Set the distance between two particles on the pyramid edges
     * @param distanceBetweenParticles the distance between two particles on the pyramid edges
     */
    public void setDistanceBetweenParticles(IVariable<Double> distanceBetweenParticles) {
        if (distanceBetweenParticles.isConstant() && distanceBetweenParticles.getCurrentValue(0) <= 0)
            throw new IllegalArgumentException("The distanceBetweenParticles has to be strictly positive.");
        animation.setDistanceBetweenParticles(distanceBetweenParticles);
    }

    /**
     * Set the distance between two particles on the pyramid edges
     * @param distanceBetweenParticles the distance between two particles on the pyramid edges
     */
    public void setDistanceBetweenParticles(double distanceBetweenParticles) {
        setDistanceBetweenParticles(new Constant<>(distanceBetweenParticles));
    }

    /**
     * Set the distance between the pyramid base center and its base apexes
     * @param distanceToAnyBaseApex the distance between the pyramid base center and its base apexes
     */
    public void setDistanceFromCenterToAnyBaseVertex(IVariable<Double> distanceToAnyBaseApex) {
        if (distanceToAnyBaseApex.isConstant() && distanceToAnyBaseApex.getCurrentValue(0) <= 0.5)
            throw new IllegalArgumentException("The distance between the pyramid center and its base apexes (distanceToAnyBaseApex) should be greater than 0.5.");
        animation.setDistanceFromCenterToAnyBaseVertex(distanceToAnyBaseApex);
    }

    /**
     * Set the distance between the pyramid base center and its base apexes
     * @param distanceToAnyBaseApex the distance between the pyramid base center and its base apexes
     */
    public void setDistanceFromCenterToAnyBaseVertex(double distanceToAnyBaseApex) {
        setDistanceFromCenterToAnyBaseVertex(new Constant<>(distanceToAnyBaseApex));
    }
}
