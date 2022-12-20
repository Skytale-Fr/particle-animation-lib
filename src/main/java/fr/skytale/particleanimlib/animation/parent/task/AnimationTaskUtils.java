package fr.skytale.particleanimlib.animation.parent.task;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static List<AnimationPointData> getLinePoints(Vector point1, Vector point2, int nbPoints) {
        return getLinePoints(point1, point2, nbPoints, true);
    }

    public static List<AnimationPointData> getLinePoints(Vector point1, Vector point2, double step) {
        return getLinePoints(point1, point2, step, true);
    }

    public static List<AnimationPointData> getLinePoints(Vector point1, Vector point2, int nbPoints, boolean showLastPoint) {
        return getLinePoints(point1, point2, nbPoints, showLastPoint, null);
    }

    public static List<AnimationPointData> getLinePoints(Vector point1, Vector point2, double step, boolean showLastPoint) {
        return getLinePoints(point1, point2, step, showLastPoint, null);
    }

    public static List<AnimationPointData> getLinePoints(Vector point1, Vector point2, double step, boolean showLastPoint, Function<APointDefinition, APointDefinition> pointDefinitionModifier) {
        return getLineVectors(point1, point2, step, showLastPoint).stream()
                .map(vector -> new AnimationPointData(vector, pointDefinitionModifier))
                .collect(Collectors.toList());
    }

    public static List<AnimationPointData> getLinePoints(Vector point1, Vector point2, int nbPoints, boolean showLastPoint, Function<APointDefinition, APointDefinition> pointDefinitionModifier) {
        return getLineVectors(point1, point2, nbPoints, showLastPoint).stream()
                .map(vector -> new AnimationPointData(vector, pointDefinitionModifier))
                .collect(Collectors.toList());
    }

    public static List<Vector> getLineVectors(Vector point1, Vector point2, int nbPoints, boolean showLastPoint) {
        double distance = point1.distance(point2);
        double step = distance / (nbPoints - 1);
        return getLineVectors(point1, point2, step, showLastPoint);
    }

    public static List<Vector> getLineVectors(Vector point1, Vector point2, double step, boolean showLastPoint) {
        List<Vector> linePoints = new ArrayList<>();

        double distance = point1.distance(point2);
        Vector stepVector = point2.clone().subtract(point1).normalize().multiply(step);
        Vector currentLoc = point1.clone();

        for (double length = 0; length < distance; currentLoc.add(stepVector)) {
            linePoints.add(currentLoc.clone());
            length += step;
        }
        if (showLastPoint) {
            linePoints.add(point2.clone());
        }

        return linePoints;
    }
}
