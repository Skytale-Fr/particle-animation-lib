package fr.skytale.particleanimlib.animation.animation.helix;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

public class Helix extends AAnimation {
    private IVariable<Double> radius;
    private IVariable<Integer> nbHelix;
    private IVariable<Double> helixAngle;
    private IVariable<Integer> nbTrailingHelixPoint;
    private IVariable<Integer> nbTrailingCentralPoint;
    private APointDefinition centralPointDefinition;

    public Helix() {
    }

    @Override
    public HelixTask show() {
        return new HelixTask(this);
    }

    @Override
    public Helix clone() {
        Helix obj = (Helix) super.clone();
        obj.radius = radius.copy();
        obj.nbHelix = nbHelix.copy();
        obj.helixAngle = helixAngle.copy();
        obj.nbTrailingHelixPoint = nbTrailingHelixPoint.copy();
        obj.nbTrailingCentralPoint = nbTrailingCentralPoint.copy();
        obj.centralPointDefinition = centralPointDefinition == null ? null : centralPointDefinition.copy();
        return obj;
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Double> getRadius() {
        return radius;
    }

    public void setRadius(IVariable<Double> radius) {
        this.radius = radius;
    }

    public IVariable<Integer> getNbHelix() {
        return nbHelix;
    }

    public void setNbHelix(IVariable<Integer> nbHelix) {
        this.nbHelix = nbHelix;
    }

    public IVariable<Double> getHelixAngle() {
        return helixAngle;
    }

    public void setHelixAngle(IVariable<Double> helixAngle) {
        this.helixAngle = helixAngle;
    }

    public IVariable<Integer> getNbTrailingHelixPoint() {
        return nbTrailingHelixPoint;
    }

    public void setNbTrailingHelixPoint(IVariable<Integer> nbTrailingHelixPoint) {
        this.nbTrailingHelixPoint = nbTrailingHelixPoint;
    }

    public IVariable<Integer> getNbTrailingCentralPoint() {
        return nbTrailingCentralPoint;
    }

    public void setNbTrailingCentralPoint(IVariable<Integer> nbTrailingCentralPoint) {
        this.nbTrailingCentralPoint = nbTrailingCentralPoint;
    }

    public APointDefinition getCentralPointDefinition() {
        return centralPointDefinition;
    }

    public void setCentralPointDefinition(APointDefinition centralPointDefinition) {
        this.centralPointDefinition = centralPointDefinition;
    }
}
