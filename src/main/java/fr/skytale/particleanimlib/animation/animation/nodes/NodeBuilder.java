package fr.skytale.particleanimlib.animation.animation.nodes;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;
import org.bukkit.util.Vector;

public class NodeBuilder extends ARotatingAnimationBuilder<Node> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    public static final String FULL_NODE_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE = "To do a fullNode, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute nbPoints value, angleBetweenEachPoint have to be a constant.\n" +
            "Else you will have to compute the nbPoints evolving value yourself.\n" +
            "Reminder (for a full node) : nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    public static final String FULL_NODE_NB_POINTS_ERROR_MESSAGE = "To do a fullNode, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute angleBetweenEachPoint value, nbPoints have to be a constant.\n" +
            "Else you will have to compute the angleBetweenEachPoint evolving value yourself.\n" +
            "Reminder (for a full node) :\n" +
            "angleBetweenEachPoint = 2 * Math.PI / nbPoints" +
            "nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    private static final String NODE_MODIFIER_MUST_NOT_BE_NULL_OR_EQUAL_TO_0 = "nodeModifier must not be NULL or equal to 0.";

    public NodeBuilder() {
        super();
        animation.setU(new Vector(1, 0, 0));
        animation.setV(new Vector(0, 1, 0));
        animation.setRadius(new Constant<>(3.0));
        animation.setNodeModifierNumerator(new Constant<>(3d));
        animation.setNodeModifierDenominator(new Constant<>(2));
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
        setNbPoints(new Constant<>(20));
    }

    @Override
    protected Node initAnimation() {
        return new Node();
    }

    /********* Node specific setters ***********/
    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive.", false);
        animation.setRadius(radius);
    }

    public void setRadius(double radius) {
        setRadius(new Constant<>(radius));
    }

    public void setMaxRadius(IVariable<Double> maxRadius) {
        checkPositive(maxRadius, "maxRadius should be positive.", false);
        animation.setMaxRadius(maxRadius);
    }

    public void setMaxRadius(double maxRadius) {
        setMaxRadius(new Constant<>(maxRadius));
    }

    public void setDirectorVectors(Vector u, Vector v) {
        checkNotNull(u, DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(v, DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        animation.setU(u);
        animation.setV(v);
    }

    public void setDirectorVectorsFromOrientation(Orientation direction, double length) {
        setDirectorVectors(direction.getU(length), direction.getV(length));
    }

    public void setDirectorVectorsFromNormalVector(Vector normal) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        animation.setU(plane.u);
        animation.setV(plane.v);
    }


    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
    }

    public void setNodeModifierNumerator(IVariable<Double> nodeModifierNumerator){
        animation.setNodeModifierNumerator(nodeModifierNumerator);
    }

    public void setNodeModifierNumerator(double nodeModifierNumerator){
        setNodeModifierNumerator(new Constant<>(nodeModifierNumerator));
    }

    public void setNodeModifierDenominator(IVariable<Integer> nodeModifierDenominator){
        animation.setNodeModifierDenominator(nodeModifierDenominator);
    }

    public void setNodeModifierDenominator(int nodeModifierDenominator){
        setNodeModifierDenominator(new Constant<>(nodeModifierDenominator));
    }

    @Override
    public Node getAnimation() {
        checkPositiveAndNotNull(animation.getRadius(), "radius should be positive.", false);
        checkNotNull(animation.getU(), DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getV(), DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        checkNotNullOrZero(animation.getNodeModifierDenominator(), NODE_MODIFIER_MUST_NOT_BE_NULL_OR_EQUAL_TO_0);
        checkNotNull(animation.getNodeModifierNumerator(),"NODE_MODIFIER_NUMERATOR_MUST_NOT_BE_NULL");
        checkPositiveAndNotNull(animation.getNbPoints(), "nbPoints should be positive", false);
        return super.getAnimation();
    }
}
