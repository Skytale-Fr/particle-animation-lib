package fr.skytale.particleanimlib.animation.sphere;

import fr.skytale.particleanimlib.attributes.PropagationType;
import fr.skytale.particleanimlib.attributes.SphereType;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class SphereBuilder extends AAnimationBuilder<Sphere> {

    public SphereBuilder(){
        animation.setNbCircles(10);
        animation.setRadius(1.0);
        animation.setStepAngle(Math.toRadians(30));
        animation.setSphereType(SphereType.FULL);
        animation.setShowFrequency(0);
        animation.setPropagationType(null);
        animation.setTicksDuration(60);
    }

    @Override
    protected Sphere initAnimation() {
        return new Sphere();
    }

    /*********SETTERS des éléments spécifiques a la sphere ***********/

    public void setNbCircles(int nbCircles){
        if(nbCircles<=0)
            throw new IllegalArgumentException("Number of circles must be positive.");
        animation.setNbCircles(nbCircles);
    }

    public void setRadius(double radius) {
        if (animation.getRadius() <= 0) {
            throw new IllegalArgumentException("Radius should be positive.");
        }
        animation.setRadius(radius);
    }

    public void setStepAngle(double a){
        if(a==0)
            throw new IllegalArgumentException("Step angle should not be equal to zero.");
        animation.setStepAngle(a);
    }

    public void setPropagationType(PropagationType propagationType) {
        animation.setPropagationType(propagationType);
    }

    public void setSimultaneousCircles(int propagationSimultaneousCircles) {
        animation.setSimultaneousCircles(propagationSimultaneousCircles);
    }

    public void setSphereType(SphereType sphereType) {
        if (sphereType == null) {
            throw new IllegalArgumentException("sphereType should not be null");
        }
        animation.setSphereType(sphereType);
    }

    @Override
    public Sphere getAnimation() {
        if (animation.getPropagationType() != null) {
            if (animation.getSimultaneousCircles() <= 0)
                throw new IllegalArgumentException("Propagation Simultaneous Circles should be positive");
            else if (animation.getSimultaneousCircles() > animation.getNbCircles()) {
                throw new IllegalArgumentException("Propagation Simultaneous Circles should be inferior to the total number of circles for this sphere");
            }
        }

        return super.getAnimation();
    }
}
