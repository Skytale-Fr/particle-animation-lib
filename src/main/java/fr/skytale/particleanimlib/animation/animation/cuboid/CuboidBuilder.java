package fr.skytale.particleanimlib.animation.animation.cuboid;


import fr.skytale.particleanimlib.animation.attribute.position.LocationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CuboidBuilder extends ARotatingAnimationBuilder<Cuboid> {

    public CuboidBuilder() {
        super();
        animation.setDistanceBetweenPoints(new Constant<>(0.5));
        animation.setShowFrequency(new Constant<>(0));
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
        animation.setFromLocationToFirstCorner(fromLocationToFirstCorner);
    }

    public void setFromLocationToSecondCorner(IVariable<Vector> fromLocationToSecondCorner) {
        animation.setFromLocationToSecondCorner(fromLocationToSecondCorner);
    }

    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        animation.setDistanceBetweenPoints(distanceBetweenPoints);
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
        if (animation.getFromLocationToFirstCorner() == null) {
            throw new IllegalArgumentException("fromLocationToFirstCorner must not be null.");
        }
        if (animation.getFromLocationToSecondCorner() == null) {
            throw new IllegalArgumentException("fromLocationToSecondCorner must not be null.");
        }
        return super.getAnimation();
    }
}
