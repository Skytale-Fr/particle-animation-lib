package fr.skytale.particleanimlib.animation.animation.helix;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class HelixBuilder extends AAnimationBuilder<Helix, HelixTask> {

    public HelixBuilder() {
        super();
        animation.setRadius(new Constant<>(2.0));
        animation.setNbHelix(new Constant<>(2));
        animation.setHelixAngle(new Constant<>(Math.toRadians(20)));
        animation.setNbTrailingHelixPoint(new Constant<>(5));
        animation.setNbTrailingCentralPoint(new Constant<>(2));
    }

    @Override
    protected Helix initAnimation() {
        return new Helix();
    }

    @Override
    public Helix getAnimation() {
        checkPositiveAndNotNull(animation.getRadius(), "radius should be positive.", false);
        checkPositiveAndNotNull(animation.getNbHelix(), "nbHelix should be positive", false);
        checkNotNull(animation.getHelixAngle(), "helixAngle should not be null");
        checkPositiveAndNotNull(animation.getNbTrailingHelixPoint(), "nbTrailingHelixPoint should be positive or equal to zero", true);
        checkPositiveAndNotNull(animation.getNbTrailingCentralPoint(), "nbTrailingCentralPoint should be positive or equal to zero", true);
        return super.getAnimation();
    }

    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive", false);
        animation.setRadius(radius);
    }

    public void setRadius(double radius) {
        setRadius(new Constant<>(radius));
    }

    public void setNbHelix(IVariable<Integer> nbHelix) {
        checkPositiveAndNotNull(nbHelix, "nbHelix should be positive", false);
        animation.setNbHelix(nbHelix);
    }

    public void setNbHelix(int nbHelix) {
        setNbHelix(new Constant<>(nbHelix));
    }

    public void setHelixAngle(IVariable<Double> helixAngle) {
        checkNotNull(animation.getHelixAngle(), "helixAngle should not be null");
        animation.setHelixAngle(helixAngle);
    }

    public void setHelixAngle(double helixAngle) {
        setHelixAngle(new Constant<>(helixAngle));
    }

    public void setNbTrailingHelixPoint(IVariable<Integer> nbTrailingHelixPoint) {
        checkPositiveAndNotNull(nbTrailingHelixPoint, "nbTrailingHelixPoint should be positive or equal to zero", true);
        animation.setNbTrailingHelixPoint(nbTrailingHelixPoint);
    }

    public void setNbTrailingHelixPoint(int nbTrailingParticles) {
        setNbTrailingHelixPoint(new Constant<>(nbTrailingParticles));
    }

    public void setNbTrailingCentralPoint(IVariable<Integer> nbTrailingCentralPoint) {
        checkPositiveAndNotNull(nbTrailingCentralPoint, "nbTrailingCentralPoint should be positive or equal to zero", true);
        animation.setNbTrailingCentralPoint(nbTrailingCentralPoint);
    }

    public void setNbTrailingCentralPoint(int nbTrailingCentralPoint) {
        setNbTrailingCentralPoint(new Constant<>(nbTrailingCentralPoint));
    }

    public void setCentralPointDefinition(APointDefinition centralParticle) {
        animation.setCentralPointDefinition(centralParticle);
    }

    public void setCentralPointDefinition(ParticleTemplate particleTemplate) {
        setCentralPointDefinition(new ParticlePointDefinition(particleTemplate));
    }
}
