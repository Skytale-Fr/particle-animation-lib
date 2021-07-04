package fr.skytale.particleanimlib.animation.attributes.var.parent;

public abstract class ACoordinatesEquationEvolvingVariable<T> implements IVariable<T> {

    /**
     * The equation string used to compute the X coordinate.
     * where x correspond to the iterationCount
     */
    protected String equationX;
    /**
     * The equation string used to compute the Y coordinate.
     * where x correspond to the iterationCount
     */
    protected String equationY;
    /**
     * The equation string used to compute the Z coordinate.
     * where x correspond to the iterationCount
     */
    protected String equationZ;


    /**
     * Create a new Evolving variable
     * @param equationX the equation that describe how the X coordinate will evolve
     * @param equationY the equation that describe how the X coordinate will evolve
     * @param equationZ the equation that describe how the X coordinate will evolve
     */
    public ACoordinatesEquationEvolvingVariable(String equationX, String equationY, String equationZ) {
        this.equationX = equationX;
        this.equationY = equationY;
        this.equationZ = equationZ;
    }

    /**
     * Retrieves the equation string used to compute the X coordinate.
     * Where x correspond to the iterationCount.
     * @return the equation that describe how the X coordinate will evolve
     */
    public final String getEquationX() {
        return equationX;
    }

    /**
     * Defines the equation string used to compute the X coordinate.
     * Where x correspond to the iterationCount.
     * @param equationX the equation that describe how the X coordinate will evolve
     */
    public final void setEquationX(String equationX) {
        this.equationX = equationX;
    }

    /**
     * Retrieves the equation string used to compute the Y coordinate.
     * Where x correspond to the iterationCount.
     * @return the equation that describe how the Y coordinate will evolve
     */
    public final String getEquationY() {
        return equationY;
    }

    /**
     * Defines the equation string used to compute the Y coordinate.
     * Where x correspond to the iterationCount.
     * @param equationY the equation that describe how the Y coordinate will evolve
     */
    public final void setEquationY(String equationY) {
        this.equationY = equationY;
    }

    /**
     * Retrieves the equation string used to compute the Z coordinate.
     * Where x correspond to the iterationCount.
     * @return the equation that describe how the Z coordinate will evolve
     */
    public final String getEquationZ() {
        return equationZ;
    }

    /**
     * Defines the equation string used to compute the Z coordinate.
     * Where x correspond to the iterationCount.
     * @param equationZ the equation that describe how the Z coordinate will evolve
     */
    public final void setEquationZ(String equationZ) {
        this.equationZ = equationZ;
    }
}
