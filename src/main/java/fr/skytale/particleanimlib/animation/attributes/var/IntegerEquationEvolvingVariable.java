package fr.skytale.particleanimlib.animation.attributes.var;

import com.udojava.evalex.Expression;
import fr.skytale.particleanimlib.animation.attributes.var.parent.ASingleEquationEvolvingVariable;

import java.math.BigDecimal;

public class IntegerEquationEvolvingVariable extends ASingleEquationEvolvingVariable<Integer> {

    /**
     * Create a new Evolving variable
     *
     * @param equation the equation that describe how the value will evolve
     */
    public IntegerEquationEvolvingVariable(String equation) {
        super(equation);
    }

    /**
     * Retrieves the current value (constant or evolving according to the current iteration count)
     *
     * @param iterationCount the current iterationCount
     * @return the value for this iteration count
     */
    @Override
    public Integer getCurrentValue(int iterationCount) {
        return new Expression(equation)
                .with("x", BigDecimal.valueOf(iterationCount))
                .setPrecision(0)
                .eval()
                .intValue();
    }
}
