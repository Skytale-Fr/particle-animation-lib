package fr.skytale.particleanimlib.sphere;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class SphereBuilder extends AAnimationBuilder {
    private Sphere sphere;

    public SphereBuilder(){
        sphere = new Sphere();
    }

    /*********SETTERS des éléments spécifiques a la sphere ***********/

    public void setNbCircles(int nbCircles){
        if(nbCircles<=0)
            nbCircles=10;
        sphere.setNbCircles(nbCircles);
    }

    public void setRadius(double radius) {
        sphere.setRadius(radius);
    }

    public void setStepAngle(double a){
        sphere.setStepAngle(a);
    }

    @Override
    public AAnimation getAnimation() {
        return sphere;
    }
}
