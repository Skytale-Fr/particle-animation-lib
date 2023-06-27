package fr.skytale.particleanimlib.animation.animation.obj;

import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class ObjBuilder extends AAnimationBuilder<Obj, ObjTask> {

    public ObjBuilder() {
        super();
        animation.setScale(0.2);
        animation.setDistanceBetweenParticles(1);
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

    /**
     * Set the scale of the obj
     * @param scale the scale
     */
    public void setScale(double scale) {
        if (scale <= 0)
            throw new IllegalArgumentException("scale must be strictly positive.");
        animation.setScale(scale);
    }

    /**
     * Set the distance between particles in the edges of the obj
     * @param distanceBetweenParticles the distance between particles in the edges of the obj
     */
    public void setDistanceBetweenParticles(double distanceBetweenParticles) {
        if (distanceBetweenParticles <= 0)
            throw new IllegalArgumentException("distanceBetweenParticles must be strictly positive.");
        animation.setDistanceBetweenParticles(distanceBetweenParticles);
    }

    /**
     * Set the minimum angle between faces of the obj for the edge to be displayed
     * @param minAngleBetweenFaces the minimum angle between faces of the obj for the edge to be displayed
     */
    public void setMinAngleBetweenFaces(double minAngleBetweenFaces) {
        if (minAngleBetweenFaces <= 0)
            throw new IllegalArgumentException("minAngleBetweenFaces must be strictly positive.");
        animation.setMinAngleBetweenFaces(minAngleBetweenFaces);
    }

    /**
     * Set the minimum area of a face of the obj for the edge to be displayed
     * @param minFaceArea the minimum area of a face of the obj for the edge to be displayed
     */
    public void setMinFaceArea(double minFaceArea) {
        if (minFaceArea < 0)
            throw new IllegalArgumentException("minFaceArea must be positive.");
        animation.setMinFaceArea(minFaceArea);
    }

    /**
     * Set the obj file name
     * @param imageFileName the obj file name
     */
    public void setObjFileName(String imageFileName) {
        animation.setObjFileName(imageFileName);
    }
}
