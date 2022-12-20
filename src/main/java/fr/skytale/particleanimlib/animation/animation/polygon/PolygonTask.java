package fr.skytale.particleanimlib.animation.animation.polygon;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PolygonTask extends AAnimationTask<Polygon> {

    @IVariableCurrentValue
    private int nbVertices;

    @IVariableCurrentValue(animationIVariableFieldName = "distanceFromCenterToVertices")
    private double radius;

    @IVariableCurrentValue
    private double distanceBetweenPoints;

    public PolygonTask(Polygon polygon) {
        super(polygon);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        List<AnimationPointData> animationPointsData = new ArrayList<>();
        double stepAngle = 2 * Math.PI / nbVertices;
        List<Vector> vertices = new ArrayList<>();

        for (int pointIndex = 0; pointIndex < nbVertices; pointIndex++) {
            double theta = pointIndex * stepAngle;

            double x = (currentU.getX() * radius * Math.cos(theta)) + (currentV.getX() * radius * Math.sin(theta));
            double y = (currentU.getY() * radius * Math.cos(theta)) + (currentV.getY() * radius * Math.sin(theta));
            double z = (currentU.getZ() * radius * Math.cos(theta)) + (currentV.getZ() * radius * Math.sin(theta));

            vertices.add(new Vector(x, y, z));
        }

        //Link each vertices
        animationPointsData.addAll(getLinePoints(vertices.get(0), vertices.get(vertices.size() - 1), distanceBetweenPoints));

        for (int i = 0; i < vertices.size() - 1; i++) {
            animationPointsData.addAll(getLinePoints(vertices.get(i), vertices.get(i + 1), distanceBetweenPoints));
        }
        return animationPointsData;
    }

}
