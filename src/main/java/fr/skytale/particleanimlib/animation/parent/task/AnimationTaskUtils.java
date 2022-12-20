package fr.skytale.particleanimlib.animation.parent.task;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class AnimationTaskUtils {

    public static Vector computeRadiusVector(Vector normalVector, double radius) {
        /*Let directorVector=(a,b,c).
        Then the equation of the plane containing the point (0,0,0) with directorVector as normal vector is ax + by + cz = 0.
        We want to find the vector radiusVector belonging to the plane*/
        double a = normalVector.getX();
        double b = normalVector.getY();
        double c = normalVector.getZ();

        Vector radiusVector;

        if (a == 0) {
            if (b == 0)
                radiusVector = new Vector(1, 1, 0);
            else if (c == 0)
                radiusVector = new Vector(1, 0, 1);
            else
                radiusVector = new Vector(1, 1, -b / c);
        } else if (b == 0) {
            if (c == 0)
                radiusVector = new Vector(0, 1, 1);
            else
                radiusVector = new Vector(1, 1, -a / c);
        } else if (c == 0)
            radiusVector = new Vector(1, -b / a, 1);
        else
            radiusVector = new Vector(1, 1, (-a - b) / c);

        return radiusVector.normalize().multiply(radius);
    }

    protected static final List<AnimationPointData> getLinePoints(Vector point1, Vector point2, double step) {
        return getLinePoints(point1, point2, step, null);
    }

    protected static final List<AnimationPointData> getLinePoints(Vector point1, Vector point2, double step, Function<APointDefinition, APointDefinition> pointDefinitionModifier) {
        double distance = point1.distance(point2);
        Vector stepVector = point2.subtract(point1).normalize().multiply(step);
        Vector currentLoc = point1.clone();

        List<AnimationPointData> linePoints = new ArrayList<>();
        for (double length = 0; length < distance; currentLoc.add(stepVector)) {
            linePoints.add(new AnimationPointData(currentLoc.clone(), pointDefinitionModifier));
            length += step;
        }
        linePoints.add(new AnimationPointData(point2.clone(), pointDefinitionModifier));
        return linePoints;
    }
}
