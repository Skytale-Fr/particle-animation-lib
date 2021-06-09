package fr.skytale.particleanimlib.circle;

import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.util.Vector;

public class CircleBuilder extends AAnimationBuilder<Circle> {

    public CircleBuilder() {
        super();
        animation = new Circle();
        animation.setU(new Vector(1, 0, 0));
        animation.setV(new Vector(0, 1, 0));
        animation.setRadius(1.0);
        animation.setNbPoints((int) animation.getRadius() * 20);
        animation.setStepAngle(2 * Math.PI / animation.getNbPoints());
    }

    /*********SETTERS des éléments spécifiques au cercle ***********/
    public void setDirectorVectors(Vector u, Vector v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException("Director vectors should not be null");
        }
        u.normalize();
        v.normalize();

        animation.setU(u);
        animation.setV(v);
    }

    public void setRadius(double r) {
        if (animation.getRadius() <= 0) {
            throw new IllegalArgumentException("Radius should be positive");
        }
        animation.setRadius(r);
    }

    public void setNbPoints(int nbPoints) {
        if (nbPoints <= 0) {
            throw new IllegalArgumentException("The number of point should be positive");
        }
        animation.setNbPoints(nbPoints);
        animation.setStepAngle(2 * Math.PI / nbPoints);
    }

    public void setAxis(Vector axis) {
        animation.setAxis(axis);
    }

    public void setStepAngleAlpha(double s){ animation.setStepAngleAlpha(s);}

    @Override
    public Circle getAnimation() {
        if (animation.getAxis() != null && animation.getStepAngleAlpha() == 0) {
            throw new IllegalArgumentException("The rotation animation should have a stepAngleAlpha");
        }
        return super.getAnimation();
    }
}
