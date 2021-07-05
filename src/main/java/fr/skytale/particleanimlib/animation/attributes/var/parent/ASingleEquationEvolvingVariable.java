package fr.skytale.particleanimlib.animation.attributes.var.parent;

public abstract class ASingleEquationEvolvingVariable<T> extends AEquationEvolvingVariable<T> {

    /**
     * The equation string where "t" correspond to the iterationCount
     */
    protected String equation;


    /**
     * Create a new Evolving variable
     * @param equation the equation that describe how the value will evolve (where "t" correspond to the iterationCount)
     */
    public ASingleEquationEvolvingVariable(String equation) {
        this.equation = equation;
    }


    /**
     * Create a new Evolving variable by copy
     * @param singleEquationEvolvingVariable another singleEquationEvolvingVariable
     */
    public ASingleEquationEvolvingVariable(ASingleEquationEvolvingVariable<T> singleEquationEvolvingVariable) {
        this.equation = singleEquationEvolvingVariable.getEquation();
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

    /**
     * Returns true if this variable will change for this iteration count
     *
     * @param iterationCount the current iterationCount
     * @return true if this variable will change for this iteration count
     */
    @Override
    public boolean willChange(int iterationCount) {
        return true;
    }
}
