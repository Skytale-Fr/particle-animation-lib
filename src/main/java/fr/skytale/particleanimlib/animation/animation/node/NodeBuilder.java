package fr.skytale.particleanimlib.animation.animation.node;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class NodeBuilder extends AAnimationBuilder<Node, NodeTask> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    public static final String FULL_NODE_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE =
            "To do a fullNode, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute nbPoints value, angleBetweenEachPoint have to be a constant.\n" +
            "Else you will have to compute the nbPoints evolving value yourself.\n" +
            "Reminder (for a full node) : nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    public static final String FULL_NODE_NB_POINTS_ERROR_MESSAGE =
            "To do a fullNode, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute angleBetweenEachPoint value, nbPoints have to be a constant.\n" +
            "Else you will have to compute the angleBetweenEachPoint evolving value yourself.\n" +
            "Reminder (for a full node) :\n" +
            "angleBetweenEachPoint = 2 * Math.PI / nbPoints" +
            "nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    private static final String NODE_MODIFIER_MUST_NOT_BE_NULL_OR_EQUAL_TO_0 = "nodeModifier must not be NULL or equal to 0.";

    public NodeBuilder() {
        super();
        animation.setRadius(3.0);
        animation.setNodeModifierNumerator(3d);
        animation.setNodeModifierDenominator(2);
        animation.setShowPeriod(1);
        animation.setTicksDuration(60);
        animation.setNbPoints(20);
    }

    @Override
    protected Node initAnimation() {
        return new Node();
    }

    @Override
    public Node getAnimation() {
        checkPositiveAndNotNull(animation.getRadius(), "radius should be positive.", false);
        checkNotNullOrZero(animation.getNodeModifierDenominator(), NODE_MODIFIER_MUST_NOT_BE_NULL_OR_EQUAL_TO_0);
        checkNotNull(animation.getNodeModifierNumerator(), "NODE_MODIFIER_NUMERATOR_MUST_NOT_BE_NULL");
        checkPositiveAndNotNull(animation.getNbPoints(), "nbPoints should be positive", false);
        return super.getAnimation();
    }

    /********* Node specific setters ***********/

    /**
     * Set the radius of the node
     * @param radius the radius of the node
     */
    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive.", false);
        animation.setRadius(radius);
    }

    /**
     * Set the radius of the node
     * @param radius the radius of the node
     */
    public void setRadius(double radius) {
        setRadius(new Constant<>(radius));
    }

    /**
     * Set the maximum radius of the points to display
     * @param maxRadius the maximum radius of the points to display
     */
    public void setMaxRadius(IVariable<Double> maxRadius) {
        checkPositive(maxRadius, "maxRadius should be positive.", false);
        animation.setMaxRadius(maxRadius);
    }

    /**
     * Set the maximum radius of the points to display
     * @param maxRadius the maximum radius of the points to display
     */
    public void setMaxRadius(double maxRadius) {
        setMaxRadius(new Constant<>(maxRadius));
    }

    /**
     * Set the number of points of the node
     * @param nbPoints the number of points of the node
     */
    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    /**
     * Set the number of points of the node
     * @param nbPoints the number of points of the node
     */
    public void setNbPoints(IVariable<Integer> nbPoints) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
    }

    /**
     * Set the numerator of the node modifier
     * @see <a href="https://mathcurve.com/courbes2d/noeud/noeud.shtml">Node of mathcurve.com</a>
     * @param nodeModifierNumerator the numerator of the node modifier
     */
    public void setNodeModifierNumerator(IVariable<Double> nodeModifierNumerator) {
        animation.setNodeModifierNumerator(nodeModifierNumerator);
    }

    /**
     * Set the numerator of the node modifier
     * @see <a href="https://mathcurve.com/courbes2d/noeud/noeud.shtml">Node of mathcurve.com</a>
     * @param nodeModifierNumerator the numerator of the node modifier
     */
    public void setNodeModifierNumerator(double nodeModifierNumerator) {
        setNodeModifierNumerator(new Constant<>(nodeModifierNumerator));
    }

    /**
     * Set the denominator of the node modifier
     * @see <a href="https://mathcurve.com/courbes2d/noeud/noeud.shtml">Node of mathcurve.com</a>
     * @param nodeModifierDenominator the denominator of the node modifier
     */
    public void setNodeModifierDenominator(IVariable<Integer> nodeModifierDenominator) {
        animation.setNodeModifierDenominator(nodeModifierDenominator);
    }

    /**
     * Set the denominator of the node modifier
     * @see <a href="https://mathcurve.com/courbes2d/noeud/noeud.shtml">Node of mathcurve.com</a>
     * @param nodeModifierDenominator the denominator of the node modifier
     */
    public void setNodeModifierDenominator(int nodeModifierDenominator) {
        setNodeModifierDenominator(new Constant<>(nodeModifierDenominator));
    }
}
