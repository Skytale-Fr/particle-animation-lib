package fr.skytale.particleanimlib.animation.animation.image;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;
import org.bukkit.util.Vector;

import java.awt.*;

public class ImageBuilder extends ARotatingAnimationBuilder<Image, ImageTask> {

    public ImageBuilder() {
        super();
        animation.setU(new Vector(1, 0, 0));
        animation.setV(new Vector(0, 1, 0));
        animation.setRotationAxis(null);
        animation.setRotationAngleAlpha(null);
        animation.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
    }

    @Override
    protected Image initAnimation() {
        return new Image();
    }

    /********* Image specific setters ***********/
    public void setDirectorVectors(Vector u, Vector v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException("Director vectors should not be null");
        }

        animation.setU(u);
        animation.setV(v.clone().multiply(-1));
    }

    public void setDirectorVectorsFromOrientation(Orientation direction, double length) {
        setDirectorVectors(direction.getU(length), direction.getV(length));
    }

    public void setDirectorVectorsFromNormalVector(Vector normal) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        animation.setU(plane.u);
        animation.setV(plane.v);
    }

    public void setImageFileName(String imageFileName) {
        animation.setImageFileName(imageFileName);
    }


    @Override
    public Image getAnimation() {
        return super.getAnimation();
    }
}
