package fr.skytale.particleanimlib.animation.attributes.var;

import com.udojava.evalex.Expression;
import fr.skytale.particleanimlib.animation.attributes.var.parent.ASingleEquationEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;

import java.math.BigDecimal;

public class DoubleEquationEvolvingVariable extends ASingleEquationEvolvingVariable<Double> {
    /**
     * Create a new Evolving variable
     *
     * @param equation the equation that describe how the value will evolve (where "t" correspond to the iterationCount)
     */
    public DoubleEquationEvolvingVariable(String equation) {
        super(equation);
    }

    /**
     * Create a new Evolving variable by copy
     *
     * @param doubleEquationEvolvingVariable another DoubleEquationEvolvingVariable
     */
    protected DoubleEquationEvolvingVariable(DoubleEquationEvolvingVariable doubleEquationEvolvingVariable) {
        super(doubleEquationEvolvingVariable);
    }

    /**
     * Retrieves the current value (constant or evolving according to the current iteration count)
     *
     * @param iterationCount the current iterationCount
     * @return the value for this iteration count
     */
    @Override
    public Double getCurrentValue(int iterationCount) {
        return addIterationCount(new Expression(equation), BigDecimal.valueOf(iterationCount))
                .setPrecision(5)
                .eval()
                .doubleValue();
    }

    /**
     * Clone a IVariable
     * @return the cloned IVariable
     */
    @Override
    public IVariable<Double> copy() {
        return new DoubleEquationEvolvingVariable(this);
    }
}
