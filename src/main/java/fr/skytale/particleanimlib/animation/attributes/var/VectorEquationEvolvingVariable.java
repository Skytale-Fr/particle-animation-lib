package fr.skytale.particleanimlib.animation.attributes.var;

import com.udojava.evalex.Expression;
import fr.skytale.particleanimlib.animation.attributes.var.parent.ACoordinatesEquationEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.math.BigDecimal;

public class VectorEquationEvolvingVariable extends ACoordinatesEquationEvolvingVariable<Vector> {

    /**
     * Create a new Evolving variable
     *
     * @param equationX the equation that describe how the X coordinate will evolve (where "t" correspond to the iterationCount)
     * @param equationY the equation that describe how the Y coordinate will evolve (where "t" correspond to the iterationCount)
     * @param equationZ the equation that describe how the Z coordinate will evolve (where "t" correspond to the iterationCount)
     */
    public VectorEquationEvolvingVariable(String equationX, String equationY, String equationZ) {
        super(equationX, equationY, equationZ);
    }

    /**
     * Create a new Evolving variable by copy
     *
     * @param vectorEquationEvolvingVariable another VectorEquationEvolvingVariable
     */
    public VectorEquationEvolvingVariable(VectorEquationEvolvingVariable vectorEquationEvolvingVariable) {
        super(vectorEquationEvolvingVariable);
    }

    /**
     * Retrieves the current value (constant or evolving according to the current iteration count)
     *
     * @param iterationCount the current iterationCount
     * @return the value for this iteration count
     */
    @Override
    public Vector getCurrentValue(int iterationCount) {
        BigDecimal iterationCountBigDecimal = BigDecimal.valueOf(iterationCount);
        return new Vector(
                addIterationCount(new Expression(equationX), iterationCountBigDecimal)
                        .setPrecision(0)
                        .eval()
                        .doubleValue(),
                addIterationCount(new Expression(equationY), iterationCountBigDecimal)
                        .setPrecision(0)
                        .eval()
                        .doubleValue(),
                addIterationCount(new Expression(equationZ), iterationCountBigDecimal)
                        .setPrecision(0)
                        .eval()
                        .doubleValue()
        );
    }

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    @Override
    public IVariable<Vector> copy() {
        return new VectorEquationEvolvingVariable(this);
    }
}
