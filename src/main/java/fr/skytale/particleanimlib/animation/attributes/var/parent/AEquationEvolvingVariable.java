package fr.skytale.particleanimlib.animation.attributes.var.parent;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

public abstract class AEquationEvolvingVariable<T> implements IVariable<T> {

    protected static Expression addIterationCount(Expression expression, BigDecimal iterationCount) {
        return expression.with("t", iterationCount);
    }

    /**
     * Returns true if this variable will change over time
     *
     * @return true if this variable will change over time
     */
    @Override
    public boolean isConstant() {
        return false;
    }
}
