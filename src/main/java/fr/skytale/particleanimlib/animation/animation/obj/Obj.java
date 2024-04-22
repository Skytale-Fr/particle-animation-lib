package fr.skytale.particleanimlib.animation.animation.obj;


import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

public class Obj extends AAnimation {
    /******** Attributes ********/

    private String objFileName;
    private double distanceBetweenParticles;
    private double scale;
    private double minAngleBetweenFaces;
    private double minFaceArea;

    /******** Constructor ********/

    public Obj() {
    }

    /******** public Methods ********/

    @Override
    public ObjTask show() {
        return new ObjTask(this);
    }

    @Override
    public Obj clone() {
        Obj obj = (Obj) super.clone();
        return obj;
    }

    /******** Getters & Setters ********/

    public String getObjFileName() {
        return objFileName;
    }

    public void setObjFileName(String objFileName) {
        this.objFileName = objFileName;
    }

    public double getDistanceBetweenParticles() {
        return distanceBetweenParticles;
    }

    public void setDistanceBetweenParticles(double distanceBetweenParticles) {
        this.distanceBetweenParticles = distanceBetweenParticles;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getMinAngleBetweenFaces() {
        return minAngleBetweenFaces;
    }

    public void setMinAngleBetweenFaces(double minAngleBetweenFaces) {
        this.minAngleBetweenFaces = minAngleBetweenFaces;
    }

    public double getMinFaceArea() {
        return minFaceArea;
    }

    public void setMinFaceArea(double minFaceArea) {
        this.minFaceArea = minFaceArea;
    }
}
