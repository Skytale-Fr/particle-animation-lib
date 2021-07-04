package fr.skytale.particleanimlib.animation.attributes.var.parent;

public abstract class ASingleEquationEvolvingVariable<T> implements IVariable<T> {

    /**
     * The equation string where x correspond to the iterationCount
     */
    protected String equation;


    /**
     * Create a new Evolving variable
     * @param equation the equation that describe how the value will evolve
     */
    public ASingleEquationEvolvingVariable(String equation) {
        this.equation = equation;
    }

    /**
     * Retrieves the equation string
     * @return the equation string
     */
    public final String getEquation() {
        return equation;
    }

    /**
     * Defines the equation string
     * @param equation the equation string
     */
    public final void setEquation(String equation) {
        this.equation = equation;
    }
}
