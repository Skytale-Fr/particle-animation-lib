package fr.skytale.particleanimlib.animation.animation.cuboid;


import fr.skytale.particleanimlib.animation.attribute.position.LocationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CuboidBuilder extends ARotatingAnimationBuilder<Cuboid, CuboidTask> {

    public CuboidBuilder() {
        super();
        animation.setDistanceBetweenPoints(new Constant<>(0.5));
        animation.setShowPeriod(new Constant<>(0));
        animation.setTicksDuration(60);
        animation.setRotationAxis(null);
        animation.setRotationAngleAlpha(null);
    }

    @Override
    protected Cuboid initAnimation() {
        return new Cuboid();
    }

    /********* Cuboid specific setters ***********/

    public void setFromLocationToFirstCorner(IVariable<Vector> fromLocationToFirstCorner) {
        checkNotNull(fromLocationToFirstCorner, "fromLocationToFirstCorner must not be null.");
        animation.setFromLocationToFirstCorner(fromLocationToFirstCorner);
    }

    public void setFromLocationToFirstCorner(Vector fromLocationToFirstCorner) {
        setFromLocationToFirstCorner(new Constant<>(fromLocationToFirstCorner));
    }

    public void setFromLocationToSecondCorner(IVariable<Vector> fromLocationToSecondCorner) {
        checkNotNull(fromLocationToSecondCorner, "fromLocationToSecondCorner must not be null.");
        animation.setFromLocationToSecondCorner(fromLocationToSecondCorner);
    }

    public void setFromLocationToSecondCorner(Vector fromLocationToSecondCorner) {
        setFromLocationToSecondCorner(new Constant<>(fromLocationToSecondCorner));
    }

    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        checkNotNull(distanceBetweenPoints, "distanceBetweenPoints must not be null");
        checkSuperior(distanceBetweenPoints, new Constant<>(0.1), "distanceBetweenPoints should be greater than 0.1", false);
        animation.setDistanceBetweenPoints(distanceBetweenPoints);
    }

    public void setDistanceBetweenPoints(double distanceBetweenPoints) {
        setDistanceBetweenPoints(new Constant<>(distanceBetweenPoints));
    }


    public void setCornersAndComputeCenter(Location firstCorner, Location secondCorner) {
        Vector fromFirstToSecond = secondCorner.toVector().subtract(firstCorner.toVector());
        Vector fromCenterToSecond = fromFirstToSecond.clone().multiply(0.5);
        Location center = firstCorner.clone().add(fromCenterToSecond);
        animation.setPosition(new LocationPosition(new Constant<>(center)));
        animation.setFromLocationToFirstCorner(new Constant<>(fromCenterToSecond.clone().multiply(-1)));
        animation.setFromLocationToSecondCorner(new Constant<>(fromCenterToSecond.clone()));
    }

    @Override
    public Cuboid getAnimation() {
        checkNotNull(animation.getFromLocationToFirstCorner(), "fromLocationToFirstCorner must not be null.");
        checkNotNull(animation.getFromLocationToSecondCorner(), "fromLocationToSecondCorner must not be null.");
        checkNotNull(animation.getDistanceBetweenPoints(), "distanceBetweenPoints must not be null");
        return super.getAnimation();
    }
}
