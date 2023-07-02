package fr.skytale.particleanimlib.animation.animation.mandala.preset;

import fr.skytale.particleanimlib.animation.animation.mandala.Mandala2DBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mandala2DPlayerMousePresetExecutor extends AAnimationPresetExecutor<Mandala2DBuilder> {

    public Mandala2DPlayerMousePresetExecutor() {
        super(Mandala2DBuilder.class);
    }

    @Override
    protected void apply(Mandala2DBuilder mandala2DBuilder, JavaPlugin plugin) {
        Player player = findClosestPlayer(mandala2DBuilder);

        List<Vector2D> previousPoints = new ArrayList<>();

        mandala2DBuilder.setTicksDuration(400);

        mandala2DBuilder.setPoints(new CallbackVariable<>(iterationCount -> {
            Vector2D fromOriginToPlayerTargetPoint = findMousePositionInAnimPlane(mandala2DBuilder, player);
            if (Objects.nonNull(fromOriginToPlayerTargetPoint)) {
                previousPoints.add(fromOriginToPlayerTargetPoint);
                if (previousPoints.size() > 30) {
                    previousPoints.remove(0);
                }
            }
            return new ArrayList<>(previousPoints);
        }));

        mandala2DBuilder.setNbCircleSection(16);
    }

    private Vector2D findMousePositionInAnimPlane(Mandala2DBuilder mandala2DBuilder, Player player) {
        final Location originLocation = mandala2DBuilder.getOriginLocation();
        final Vector3D originVector = new Vector3D(
                originLocation.getX(),
                originLocation.getY(),
                originLocation.getZ()
        );

        //TODO undertake rotation of the plane according to animation rotation
        Plane plane = new Plane(
                originVector,
                new Vector3D(0, 1, 0),
                0.1
        );

        Vector3D lineFirstPoint = new Vector3D(
                player.getEyeLocation().getX(),
                player.getEyeLocation().getY(),
                player.getEyeLocation().getZ());
        Vector3D lineSecondPoint = new Vector3D(
                lineFirstPoint.getX(),
                lineFirstPoint.getY(),
                lineFirstPoint.getZ())
                .add(new Vector3D(
                        player.getLocation().getDirection().getX(),
                        player.getLocation().getDirection().getY(),
                        player.getLocation().getDirection().getZ()));

        Line line = new Line(
                lineFirstPoint,
                lineSecondPoint,
                0.1
        );

        final Vector3D intersection = plane.intersection(line);
        Vector2D fromOriginToMouseIntersection = null;
        if (intersection != null) {
            fromOriginToMouseIntersection = new Vector2D(
                    intersection.getX() - originVector.getX(),
                    intersection.getZ() - originVector.getZ()
            );
        }
        return fromOriginToMouseIntersection;
    }

    private Player findClosestPlayer(Mandala2DBuilder mandala2DBuilder) {
        Player closestPlayer = null;
        // Nothing different from builder
        for (Player player : Objects.requireNonNull(mandala2DBuilder.getOriginLocation().getWorld()).getPlayers()) {
            if (closestPlayer == null) {
                closestPlayer = player;
            } else {
                if (
                        player.getLocation().distance(mandala2DBuilder.getOriginLocation()) <
                        closestPlayer.getLocation().distance(mandala2DBuilder.getOriginLocation())
                ) {
                    closestPlayer = player;
                }
            }
        }
        if (closestPlayer == null) {
            throw new IllegalArgumentException("No player found in world");
        }
        return closestPlayer;
    }
}
