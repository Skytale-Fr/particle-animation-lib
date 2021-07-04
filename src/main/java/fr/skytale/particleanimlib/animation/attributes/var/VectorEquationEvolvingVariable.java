package fr.skytale.particleanimlib.animation.attributes.var;

import com.udojava.evalex.Expression;
import fr.skytale.particleanimlib.animation.attributes.var.parent.ACoordinatesEquationEvolvingVariable;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.math.BigDecimal;

public class VectorEquationEvolvingVariable extends ACoordinatesEquationEvolvingVariable<Vector> {

    /**
     * Create a new Evolving variable
     *
     * @param equationX the equation that describe how the X coordinate will evolve
     * @param equationY the equation that describe how the X coordinate will evolve
     * @param equationZ the equation that describe how the X coordinate will evolve
     */
    public VectorEquationEvolvingVariable(String equationX, String equationY, String equationZ) {
        super(equationX, equationY, equationZ);
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
                new Expression(equationX)
                        .with("x", iterationCountBigDecimal)
                        .setPrecision(0)
                        .eval()
                        .doubleValue(),
                new Expression(equationY)
                        .with("x", iterationCountBigDecimal)
                        .setPrecision(0)
                        .eval()
                        .doubleValue(),
                new Expression(equationZ)
                        .with("x", iterationCountBigDecimal)
                        .setPrecision(0)
                        .eval()
                        .doubleValue()
        );
    }
}
