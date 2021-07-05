package fr.skytale.particleanimlib.animation.attributes.var;

import com.udojava.evalex.Expression;
import fr.skytale.particleanimlib.animation.attributes.var.parent.ACoordinatesEquationEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.bukkit.Location;
import org.bukkit.World;

import java.math.BigDecimal;

public class LocationEquationEvolvingVariable extends ACoordinatesEquationEvolvingVariable<Location> {

    /**
     * The world
     */
    private World world;

    /**
     * Create a new Evolving variable
     *
     * @param world the world
     * @param equationX the equation that describe how the X coordinate will evolve (where "t" correspond to the iterationCount)
     * @param equationY the equation that describe how the Y coordinate will evolve (where "t" correspond to the iterationCount)
     * @param equationZ the equation that describe how the Z coordinate will evolve (where "t" correspond to the iterationCount)
     */
    public LocationEquationEvolvingVariable(World world, String equationX, String equationY, String equationZ) {
        super(equationX, equationY, equationZ);
        this.world = world;
    }

    /**
     * Create a new Evolving variable by copy
     *
     * @param locationEquationEvolvingVariable another LocationEquationEvolvingVariable
     */
    public LocationEquationEvolvingVariable(LocationEquationEvolvingVariable locationEquationEvolvingVariable) {
        super(locationEquationEvolvingVariable);
        this.world = locationEquationEvolvingVariable.getWorld();
    }

    /**
     * Retrieves the current value (constant or evolving according to the current iteration count)
     *
     * @param iterationCount the current iterationCount
     * @return the value for this iteration count
     */
    @Override
    public Location getCurrentValue(int iterationCount) {
        BigDecimal iterationCountBigDecimal = BigDecimal.valueOf(iterationCount);
        return new Location(
                world,
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
    public IVariable<Location> copy() {
        return new LocationEquationEvolvingVariable(this);
    }

    /**
     * Defines the world
     * @return the world
     */
    public World getWorld() {
        return world;
    }


    /**
     * Retrieves the world
     * @param world the world
     */
    public void setWorld(World world) {
        this.world = world;
    }
}
