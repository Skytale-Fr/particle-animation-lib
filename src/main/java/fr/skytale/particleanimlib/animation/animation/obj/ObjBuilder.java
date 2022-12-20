package fr.skytale.particleanimlib.animation.animation.obj;

import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class ObjBuilder extends AAnimationBuilder<Obj, ObjTask> {

    public ObjBuilder() {
        super();
        animation.setScale(0.05);
        animation.setDistanceBetweenParticles(0.5);
        animation.setMinAngleBetweenFaces(Math.toRadians(20));
        animation.setMinFaceArea(1);
    }

    @Override
    protected Obj initAnimation() {
        return new Obj();
    }

    @Override
    public Obj getAnimation() {
        if (animation.getScale() <= 0)
            throw new IllegalArgumentException("scale must be strictly positive.");
        if (animation.getDistanceBetweenParticles() <= 0)
            throw new IllegalArgumentException("distanceBetweenParticles must be strictly positive.");
        if (animation.getMinAngleBetweenFaces() <= 0)
            throw new IllegalArgumentException("minAngleBetweenFaces must be strictly positive.");
        if (animation.getMinFaceArea() < 0)
            throw new IllegalArgumentException("minFaceArea must be positive.");
        return super.getAnimation();
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

    public void setMinFaceArea(double minFaceArea) {
        if (minFaceArea < 0)
            throw new IllegalArgumentException("minFaceArea must be positive.");
        animation.setMinFaceArea(minFaceArea);
    }

    public void setObjFileName(String imageFileName) {
        animation.setObjFileName(imageFileName);
    }
}
