package fr.skytale.particleanimlib.sphere;

import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class SphereBuilder extends AAnimationBuilder<Sphere> {

    public SphereBuilder(){
        animation = new Sphere();
        animation.setNbCircles(10);
        animation.setRadius(1.0);
        animation.setStepAngle(Math.toRadians(30));
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

    @Override
    public Sphere getAnimation() {
        return super.getAnimation();
    }
}
