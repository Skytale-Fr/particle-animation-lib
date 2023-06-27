package fr.skytale.particleanimlib.animation.animation.cuboid;


import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CuboidBuilder extends AAnimationBuilder<Cuboid, CuboidTask> {

    public CuboidBuilder() {
        super();
        animation.setDistanceBetweenPoints(new Constant<>(0.5));
        animation.setShowPeriod(new Constant<>(0));
        animation.setTicksDuration(60);
    }

    @Override
    protected Cuboid initAnimation() {
        return new Cuboid();
    }

    @Override
    public Cuboid getAnimation() {
        checkNotNull(animation.getFromLocationToFirstCorner(), "fromLocationToFirstCorner must not be null.");
        checkNotNull(animation.getFromLocationToSecondCorner(), "fromLocationToSecondCorner must not be null.");
        checkNotNull(animation.getDistanceBetweenPoints(), "distanceBetweenPoints must not be null");
        return super.getAnimation();
    }

    /********* Cuboid specific setters ***********/

    /**
     * Defines the vector from the animation position to the first corner of the cuboid
     * @param fromLocationToFirstCorner a vector from the animation position to the first corner of the cuboid
     */
    public void setFromLocationToFirstCorner(IVariable<Vector> fromLocationToFirstCorner) {
        checkNotNull(fromLocationToFirstCorner, "fromLocationToFirstCorner must not be null.");
        animation.setFromLocationToFirstCorner(fromLocationToFirstCorner);
    }

    /**
     * Defines the vector from the animation position to the first corner of the cuboid
     * @param fromLocationToFirstCorner a vector from the animation position to the first corner of the cuboid
     */
    public void setFromLocationToFirstCorner(Vector fromLocationToFirstCorner) {
        setFromLocationToFirstCorner(new Constant<>(fromLocationToFirstCorner));
    }

    /**
     * Defines the vector from the animation position to the second corner of the cuboid
     * @param fromLocationToSecondCorner a vector from the animation position to the second corner of the cuboid
     */
    public void setFromLocationToSecondCorner(IVariable<Vector> fromLocationToSecondCorner) {
        checkNotNull(fromLocationToSecondCorner, "fromLocationToSecondCorner must not be null.");
        animation.setFromLocationToSecondCorner(fromLocationToSecondCorner);
    }

    /**
     * Defines the vector from the animation position to the second corner of the cuboid
     * @param fromLocationToSecondCorner a vector from the animation position to the second corner of the cuboid
     */
    public void setFromLocationToSecondCorner(Vector fromLocationToSecondCorner) {
        setFromLocationToSecondCorner(new Constant<>(fromLocationToSecondCorner));
    }

    /**
     * Defines the distance between two points when displaying the cuboid edges
     * @param distanceBetweenPoints the distance between two points of the cuboid
     */
    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        checkNotNull(distanceBetweenPoints, "distanceBetweenPoints must not be null");
        checkSuperior(distanceBetweenPoints, new Constant<>(0.1), "distanceBetweenPoints should be greater than 0.1", false);
        animation.setDistanceBetweenPoints(distanceBetweenPoints);
    }

    /**
     * Defines the distance between two points when displaying the cuboid edges
     * @param distanceBetweenPoints the distance between two points of the cuboid
     */
    public void setDistanceBetweenPoints(double distanceBetweenPoints) {
        setDistanceBetweenPoints(new Constant<>(distanceBetweenPoints));
    }

    /**
     * Defines the two corners of the cuboid and computes the animation position to be at the cuboid center
     * @param firstCorner the first corner of the cuboid
     * @param secondCorner the second corner of the cuboid
     */
    public void setCornersAndComputeCenter(Location firstCorner, Location secondCorner) {
        Vector fromFirstToSecond = secondCorner.toVector().subtract(firstCorner.toVector());
        Vector fromCenterToSecond = fromFirstToSecond.clone().multiply(0.5);
        Location center = firstCorner.clone().add(fromCenterToSecond);
        animation.setPosition(new LocatedAnimationPosition(new Constant<>(center)));
        animation.setFromLocationToFirstCorner(new Constant<>(fromCenterToSecond.clone().multiply(-1)));
        animation.setFromLocationToSecondCorner(new Constant<>(fromCenterToSecond.clone()));
    }
}
