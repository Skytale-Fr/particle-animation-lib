package fr.skytale.particleanimlib.animation.animation.circuitpulse3d;


import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.area.IArea;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is used to animate a circuit pulse 3D animation.
 * It represents a moving point (that has its own trail)
 *
 * @see CircuitPulse3D
 */
public class CircuitPulseMovingPoint {

    protected static final int NB_POSSIBLE_DIRECTION = 8;
    protected static final double BRACE_ANGLE = Math.PI * 2 / NB_POSSIBLE_DIRECTION;
    protected static final Random RANDOM = new Random();

    private final double speed;
    private final APointDefinition pointDefinition;
    private Vector3D location;
    private final double horizontalOriginDirectionAngle;
    private final double verticalOriginDirectionAngle;
    private double horizontalDirectionAngle;
    private double verticalDirectionAngle;
    private LinkedList<Vector3D> trailLocations = new LinkedList<>();
    private boolean isInside = true;

    public CircuitPulseMovingPoint(Vector3D location, double horizontalDirectionAngle, double verticalDirectionAngle, double speed, APointDefinition pointDefinition) {
        this.horizontalOriginDirectionAngle = horizontalDirectionAngle;
        this.verticalOriginDirectionAngle = verticalDirectionAngle;
        this.location = location;
        this.horizontalDirectionAngle = horizontalDirectionAngle;
        this.verticalDirectionAngle = verticalDirectionAngle;
        this.speed = speed;
        this.pointDefinition = pointDefinition;
    }

    /**
     * Build a collection of CircuitPulseMovingPoint from the spawner
     *
     * @param particlesToSpawn The spawner
     * @param speed            The speed of the particles
     * @param pointDefinition  The point definition
     * @return The collection of CircuitPulseMovingPoint
     */
    public static Collection<CircuitPulseMovingPoint> build(ParticlesToSpawn particlesToSpawn, Double speed, APointDefinition pointDefinition) {
        return particlesToSpawn.stream()
                .flatMap(particleToSpawn -> {
                    final Stream.Builder<CircuitPulseMovingPoint> builder = Stream.builder();
                    for (int i = 0; i < particleToSpawn.getNbParticleToSpawn(); ++i) {
                        int horizontalAngleFactor = RANDOM.nextInt(NB_POSSIBLE_DIRECTION);
                        int verticalAngleFactor = RANDOM.nextInt(NB_POSSIBLE_DIRECTION);
                        builder.add(new CircuitPulseMovingPoint(
                                new Vector3D(particleToSpawn.getSpawnLocation().getX(), particleToSpawn.getSpawnLocation().getY(), particleToSpawn.getSpawnLocation().getZ()),
                                horizontalAngleFactor * BRACE_ANGLE,
                                verticalAngleFactor * BRACE_ANGLE,
                                speed,
                                pointDefinition
                        ));
                    }
                    return builder.build();
                })
                .collect(Collectors.toList());
    }


    public void update(IArea boundaryArea, int trailSize, Double directionChangeProbability, Double maxAngleBetweenDirectionAndOriginDirection) {
        updateDirection(directionChangeProbability, maxAngleBetweenDirectionAndOriginDirection);
        move(boundaryArea, trailSize);
    }

    public boolean isDead() {
        //Even if the point is outside, we keep it alive until its trail is empty
        return (!isInside) && trailLocations.isEmpty();
    }

    public void updateDirection(Double directionChangeProbability, Double maxAngleBetweenDirectionAndOriginDirection) {
        if (isInside) {
            double directionChangeRandom = RANDOM.nextDouble();
            if (directionChangeRandom <= directionChangeProbability) {
                double newHorizontalAngle;
                double newVerticalAngle;
                if (directionChangeRandom <= directionChangeProbability / 2) {
                    newHorizontalAngle = normalizeAngle(horizontalDirectionAngle + BRACE_ANGLE);
                    newVerticalAngle = normalizeAngle(verticalDirectionAngle + BRACE_ANGLE);
                } else {
                    newHorizontalAngle = normalizeAngle(horizontalDirectionAngle - BRACE_ANGLE);
                    newVerticalAngle = normalizeAngle(verticalDirectionAngle - BRACE_ANGLE);
                }
                if (Math.abs(this.horizontalOriginDirectionAngle - this.horizontalDirectionAngle) <= maxAngleBetweenDirectionAndOriginDirection) {
                    horizontalDirectionAngle = newHorizontalAngle;
                }
                if (Math.abs(this.verticalOriginDirectionAngle - this.verticalDirectionAngle) <= maxAngleBetweenDirectionAndOriginDirection) {
                    verticalDirectionAngle = newVerticalAngle;
                }
            }
        }
    }

    private double normalizeAngle(double angle) {
        return (angle + (Math.PI * 2)) % (Math.PI * 2);
    }

    public void move(IArea boundaryArea, int trailSize) {
        if (isInside) {
            trailLocations.add(0, location);
            if (trailLocations.size() > trailSize) {
                trailLocations = new LinkedList<>(trailLocations.subList(0, trailSize));
            }
            location = getAfterMoveLocation();
            checkParticleIsInside(boundaryArea);
        } else {
            trailLocations.removeLast();
        }
    }

    public Vector3D getAfterMoveLocation() {
        return new Vector3D(
                location.getX() + (Math.sin(verticalDirectionAngle) * Math.cos(horizontalDirectionAngle) * speed),
                location.getY() + (Math.sin(verticalDirectionAngle) * Math.sin(horizontalDirectionAngle) * speed),
                location.getZ() + (Math.cos(verticalDirectionAngle) * speed)
        );
    }

    private void checkParticleIsInside(IArea boundaryArea) {
        isInside = boundaryArea.isInside(new Vector(location.getX(), location.getY(), location.getZ()));
    }

    public List<AnimationPointData> getDisplay(boolean fadeColorInTrail) {
        List<AnimationPointData> pointsData = new ArrayList<>();
        if (isInside) {
            pointsData.add(new AnimationPointData(
                    new Vector(location.getX(), location.getY(), location.getZ()),
                    givenPointDefinition -> pointDefinition
            ));
        }
        boolean fadeOutColor = false;
        if (fadeColorInTrail && pointDefinition instanceof ParticlePointDefinition) {
            ParticleTemplate particleTemplate = ((ParticlePointDefinition) pointDefinition).getParticleTemplate();
            if (particleTemplate.getAdditionalData() instanceof RegularColor) {
                //compute fade out color
                RegularColor particleColor = (RegularColor) particleTemplate.getAdditionalData();
                final Color color = new Color(particleColor.getRed(), particleColor.getGreen(), particleColor.getBlue());
                float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
                float initialBrightness = hsbColor[2];

                for (int i = 0; i < trailLocations.size(); ++i) {
                    //reduce color brightness when going away from the point inside its trail
                    float brightness = initialBrightness * (1 - ((float) i / trailLocations.size()));
                    Color currentTrailParticleColor = Color.getHSBColor(hsbColor[0], hsbColor[1], brightness);

                    ParticleTemplate fadingColorParticleTemplate = new ParticleTemplate(particleTemplate);
                    fadingColorParticleTemplate.setAdditionalData(new RegularColor(currentTrailParticleColor.getRed(), currentTrailParticleColor.getGreen(), currentTrailParticleColor.getBlue()));

                    pointsData.add(new AnimationPointData(
                            new Vector(trailLocations.get(i).getX(), trailLocations.get(i).getY(), trailLocations.get(i).getZ()),
                            givenPointDefinition -> new ParticlePointDefinition(fadingColorParticleTemplate)
                    ));
                }
                fadeOutColor = true;
            }
        }
        if (!fadeOutColor) {
            pointsData.addAll(trailLocations.stream()
                    .map(trailLocation ->
                            new AnimationPointData(
                                    new Vector(trailLocation.getX(), trailLocation.getY(), trailLocation.getZ()),
                                    givenPointDefinition -> pointDefinition
                            )
                    )
                    .collect(Collectors.toList()));
        }
        return pointsData;
    }
}