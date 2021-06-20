package fr.skytale.particleanimlib.parent;

public abstract class ARoundAnimationTask<T extends ARoundAnimation> extends AAnimationTask<T> {
    protected double radius;
    protected int nbPoints;
    protected double stepAngle;

    public ARoundAnimationTask(T animation) {
        super(animation);
        this.radius = animation.getRadius();
        this.nbPoints = animation.getNbPoints();
        this.stepAngle = animation.getStepAngle();
    }

    protected void init(){
    }
}
