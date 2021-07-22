package fr.skytale.particleanimlib.animation.animation.obj;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;

import java.awt.*;

public class ObjBuilder extends ARotatingAnimationBuilder<Obj> {

    public ObjBuilder() {
        super();
        animation.setScale(0.05);
        animation.setDistanceBetweenParticles(0.5);
        animation.setMinAngleBetweenFaces(Math.toRadians(20));
        animation.setRotationAxis(null);
        animation.setRotationAngleAlpha(null);
        animation.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
    }

    @Override
    protected Obj initAnimation() {
        return new Obj();
    }

    /********* Obj specific setters ***********/

    public void setScale(double scale) {
        if (scale <= 0)
            throw new IllegalArgumentException("scale must be strictly positive.");
        animation.setScale(scale);
    }

    public void setDistanceBetweenParticles(double distanceBetweenParticles) {
        if (distanceBetweenParticles <= 0)
            throw new IllegalArgumentException("distanceBetweenParticles must be strictly positive.");
        animation.setDistanceBetweenParticles(distanceBetweenParticles);
    }

    public void setMinAngleBetweenFaces(double minAngleBetweenFaces) {
        if (minAngleBetweenFaces <= 0)
            throw new IllegalArgumentException("minAngleBetweenFaces must be strictly positive.");
        animation.setMinAngleBetweenFaces(minAngleBetweenFaces);
    }

    public void setObjFileName(String imageFileName) {
        animation.setObjFileName(imageFileName);
    }


    @Override
    public Obj getAnimation() {
        if (animation.getScale() <= 0)
            throw new IllegalArgumentException("scale must be strictly positive.");
        if (animation.getDistanceBetweenParticles() <= 0)
            throw new IllegalArgumentException("distanceBetweenParticles must be strictly positive.");
        if (animation.getMinAngleBetweenFaces() <= 0)
            throw new IllegalArgumentException("minAngleBetweenFaces must be strictly positive.");
        return super.getAnimation();
    }
}
