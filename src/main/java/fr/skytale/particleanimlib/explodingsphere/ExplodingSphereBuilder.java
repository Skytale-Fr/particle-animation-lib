package fr.skytale.particleanimlib.explodingsphere;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class ExplodingSphereBuilder extends AAnimationBuilder {
    private ExplodingSphere explodingSphere;

    public ExplodingSphereBuilder(){
        explodingSphere = new ExplodingSphere();
    }

    /*********SETTERS des éléments spécifiques a la sphere explosante ***********/

    public  void setGrowthSpeed(double growthSpeed){
        if(growthSpeed<=0){
            growthSpeed=0.5;
        }
        explodingSphere.setGrowthSpeed(growthSpeed);
    }

    public void setExplosionLimit(double explosionLimit){
        if(explosionLimit<=1)       //TODO oe nn peut etre pas, si < à 1 on peut rappetissir la sphere
            explosionLimit=6;
        explodingSphere.setExplosionLimit(explosionLimit);
    }

    public void setNbCircles(int nbCircles){
        explodingSphere.setNbCircles(nbCircles);
    }

    public void setRadius(double radius) {
        explodingSphere.setRadius(radius);
    }

    public void setStepAngle(double a){
        explodingSphere.setStepAngle(a);
    }

    @Override
    public AAnimation getAnimation() {
        return explodingSphere;
    }

}
