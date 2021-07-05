package fr.skytale.particleanimlib.animation.attributes.var;

import com.udojava.evalex.Expression;
import fr.skytale.particleanimlib.animation.attributes.var.parent.ASingleEquationEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;

import java.math.BigDecimal;

public class IntegerEquationEvolvingVariable extends ASingleEquationEvolvingVariable<Integer> {

    /**
     * Create a new Evolving variable
     *
     * @param equation the equation that describe how the value will evolve (where "t" correspond to the iterationCount)
     */
    public IntegerEquationEvolvingVariable(String equation) {
        super(equation);
    }

    /**
     * Create a new Evolving variable by copy
     *
     * @param integerEquationEvolvingVariable another IntegerEquationEvolvingVariable
     */
    public IntegerEquationEvolvingVariable(IntegerEquationEvolvingVariable integerEquationEvolvingVariable) {
        super(integerEquationEvolvingVariable);
    }

    /**
     * Retrieves the current value (constant or evolving according to the current iteration count)
     *
     * @param iterationCount the current iterationCount
     * @return the value for this iteration count
     */
    @Override
    public Integer getCurrentValue(int iterationCount) {
        return addIterationCount(new Expression(equation), BigDecimal.valueOf(iterationCount))
                .setPrecision(0)
                .eval()
                .intValue();
    }

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    @Override
    public IVariable<Integer> copy() {
        return new IntegerEquationEvolvingVariable(this);
    }
}
