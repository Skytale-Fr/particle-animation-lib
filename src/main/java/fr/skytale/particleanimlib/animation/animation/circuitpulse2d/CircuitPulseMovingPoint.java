package fr.skytale.particleanimlib.animation.animation.circuitpulse2d;


import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.area.IArea;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is used to animate a circuit pulse 2D animation.
 * It represents a moving point (that has its own trail)
 *
 * @see CircuitPulse2D
 */
public class CircuitPulseMovingPoint {

    protected static final int NB_POSSIBLE_DIRECTION = 8;
    protected static final double BRACE_ANGLE = Math.PI * 2 / NB_POSSIBLE_DIRECTION;
    protected static final Random RANDOM = new Random();

    private final double speed;
    private final APointDefinition pointDefinition;
    private Vector2D location;
    private double originDirectionAngle;
    private double directionAngle;
    private LinkedList<Vector2D> trailLocations = new LinkedList<>();
    private boolean isInside = true;

    public CircuitPulseMovingPoint(Vector2D location, double directionAngle, double speed, APointDefinition pointDefinition) {
        this.originDirectionAngle = directionAngle;
        this.location = location;
        this.directionAngle = directionAngle;
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
                        int angleFactor = RANDOM.nextInt(NB_POSSIBLE_DIRECTION);
                        builder.add(new CircuitPulseMovingPoint(
                                new Vector2D(particleToSpawn.getSpawnLocation().getX(), particleToSpawn.getSpawnLocation().getY()),
                                angleFactor * BRACE_ANGLE,
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
                double newAngle;
                if (directionChangeRandom <= directionChangeProbability / 2) {
                    newAngle = normalizeAngle(directionAngle + BRACE_ANGLE);
                } else {
                    newAngle = normalizeAngle(directionAngle - BRACE_ANGLE);
                }
                if (Math.abs(this.originDirectionAngle - this.directionAngle) <= maxAngleBetweenDirectionAndOriginDirection) {
                    directionAngle = newAngle;
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

    public Vector2D getAfterMoveLocation() {
        return new Vector2D(
                location.getX() + (Math.cos(directionAngle) * speed),
                location.getY() + (Math.sin(directionAngle) * speed)
        );
    }

    private void checkParticleIsInside(IArea boundaryArea) {
        isInside = boundaryArea.isInside(new Vector(location.getX(), 0, location.getY()));
    }

    public List<AnimationPointData> getDisplay(boolean fadeColorInTrail) {
        List<AnimationPointData> pointsData = new ArrayList<>();
        if (isInside) {
            pointsData.add(new AnimationPointData(
                    new Vector(location.getX(), 0, location.getY()),
                    givenPointDefinition -> pointDefinition
            ));
        }
        boolean fadeOutColor = false;
        // fade out color in trail if possible
        if (fadeColorInTrail && pointDefinition instanceof ParticlePointDefinition) {
            ParticleTemplate particleTemplate = ((ParticlePointDefinition) pointDefinition).getParticleTemplate();
            if (particleTemplate.data() != null) {
                if (particleTemplate.data() instanceof Particle.DustOptions dustOptions) {
                    //compute fade out color
                    final Color color = dustOptions.getColor();
                    float[] hsbColor = java.awt.Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
                    float initialBrightness = hsbColor[2];

                    for (int i = 0; i < trailLocations.size(); ++i) {
                        //reduce color brightness when going away from the point inside its trail
                        float brightness = initialBrightness * (1 - ((float) i / trailLocations.size()));
                        java.awt.Color currentTrailParticleColor = java.awt.Color.getHSBColor(hsbColor[0], hsbColor[1], brightness);

                        ParticleTemplate fadingColorParticleTemplate = new ParticleTemplate(particleTemplate);
                        fadingColorParticleTemplate.data(new Particle.DustOptions(
                                Color.fromRGB(currentTrailParticleColor.getRed(), currentTrailParticleColor.getGreen(), currentTrailParticleColor.getBlue()),
                                dustOptions.getSize()
                        ));

                        pointsData.add(new AnimationPointData(
                                new Vector(trailLocations.get(i).getX(), 0, trailLocations.get(i).getY()),
                                givenPointDefinition -> new ParticlePointDefinition(fadingColorParticleTemplate)
                        ));
                    }
                    fadeOutColor = true;
                }
            }
        }
        if (!fadeOutColor) {
            pointsData.addAll(trailLocations.stream()
                    .map(trailLocation ->
                            new AnimationPointData(
                                    new Vector(trailLocation.getX(), 0, trailLocation.getY()),
                                    givenPointDefinition -> pointDefinition
                            )
                    )
                    .toList());
        }
        return pointsData;
    }
}