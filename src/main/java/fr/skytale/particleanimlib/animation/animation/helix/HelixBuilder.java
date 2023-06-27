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

    /**
     * Set the radius of the helix
     * @param radius the radius of the helix
     */
    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive", false);
        animation.setRadius(radius);
    }

    /**
     * Set the radius of the helix
     * @param radius the radius of the helix
     */
    public void setRadius(double radius) {
        setRadius(new Constant<>(radius));
    }

    /**
     * Set the number of helix
     * @param nbHelix the number of helix
     */
    public void setNbHelix(IVariable<Integer> nbHelix) {
        checkPositiveAndNotNull(nbHelix, "nbHelix should be positive", false);
        animation.setNbHelix(nbHelix);
    }

    /**
     * Set the number of helix
     * @param nbHelix the number of helix
     */
    public void setNbHelix(int nbHelix) {
        setNbHelix(new Constant<>(nbHelix));
    }

    /**
     * Set the angle of the helix
     * @param helixAngle the angle of the helix
     */
    public void setHelixAngle(IVariable<Double> helixAngle) {
        checkNotNull(animation.getHelixAngle(), "helixAngle should not be null");
        animation.setHelixAngle(helixAngle);
    }

    /**
     * Set the angle of the helix
     * @param helixAngle the angle of the helix
     */
    public void setHelixAngle(double helixAngle) {
        setHelixAngle(new Constant<>(helixAngle));
    }

    /**
     * Set the number of points of a single helix
     * @param nbTrailingHelixPoint the number of points of a single helix
     */
    public void setNbTrailingHelixPoint(IVariable<Integer> nbTrailingHelixPoint) {
        checkPositiveAndNotNull(nbTrailingHelixPoint, "nbTrailingHelixPoint should be positive or equal to zero", true);
        animation.setNbTrailingHelixPoint(nbTrailingHelixPoint);
    }

    /**
     * Set the number of points shown behind the first helix point
     * @param nbTrailingHelixPoint the number of points trailing behind the first helix point
     */
    public void setNbTrailingHelixPoint(int nbTrailingHelixPoint) {
        setNbTrailingHelixPoint(new Constant<>(nbTrailingHelixPoint));
    }

    /**
     * Set the number of points shown behind the central point
     * @param nbTrailingCentralPoint the number of points trailing behind the central point
     */
    public void setNbTrailingCentralPoint(IVariable<Integer> nbTrailingCentralPoint) {
        checkPositiveAndNotNull(nbTrailingCentralPoint, "nbTrailingCentralPoint should be positive or equal to zero", true);
        animation.setNbTrailingCentralPoint(nbTrailingCentralPoint);
    }

    /**
     * Set the number of points shown behind the central point
     * @param nbTrailingCentralPoint the number of points trailing behind the central point
     */
    public void setNbTrailingCentralPoint(int nbTrailingCentralPoint) {
        setNbTrailingCentralPoint(new Constant<>(nbTrailingCentralPoint));
    }

    /**
     * Set what will be displayed at the place of the central point and in its trail
     * @param centralParticle the definition of the central point
     */
    public void setCentralPointDefinition(APointDefinition centralParticle) {
        animation.setCentralPointDefinition(centralParticle);
    }

    /**
     * Set what will be displayed at the place of the central point and in its trail
     * @param particleTemplate the particle that will be displayed for the central point and its trailing points
     */
    public void setCentralPointDefinition(ParticleTemplate particleTemplate) {
        setCentralPointDefinition(new ParticlePointDefinition(particleTemplate));
    }
}
