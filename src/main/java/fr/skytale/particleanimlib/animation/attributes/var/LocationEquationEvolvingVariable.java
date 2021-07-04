package fr.skytale.particleanimlib.animation.attributes.var;

import com.udojava.evalex.Expression;
import fr.skytale.particleanimlib.animation.attributes.var.parent.ACoordinatesEquationEvolvingVariable;
import org.bukkit.Location;
import org.bukkit.World;

import java.math.BigDecimal;

public class LocationEquationEvolvingVariable extends ACoordinatesEquationEvolvingVariable<Location> {

    private World world;

    /**
     * Create a new Evolving variable
     *
     * @param world the world
     * @param equationX the equation that describe how the X coordinate will evolve
     * @param equationY the equation that describe how the X coordinate will evolve
     * @param equationZ the equation that describe how the X coordinate will evolve
     */
    public LocationEquationEvolvingVariable(World world, String equationX, String equationY, String equationZ) {
        super(equationX, equationY, equationZ);
        this.world = world;
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
