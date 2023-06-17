package fr.skytale.particleanimlib.animation.animation.circuitpulse2d;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.ForceUpdatePointsConfiguration;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ForceUpdatePointsConfiguration(alwaysUpdate = true)
public class CircuitPulse2DTask extends AAnimationTask<CircuitPulse2D> {
    protected static final int NB_ANGLES = 8;
    protected static final double BRACE_ANGLE = Math.PI * 2 / NB_ANGLES;
    protected static final Random RANDOM = new Random();

    @IVariableCurrentValue
    Double speed;

    @IVariableCurrentValue
    Integer trailSize;

    @IVariableCurrentValue
    Float maxSize;

    @IVariableCurrentValue
    ParticlesToSpawn spawner;

    List<Particle> particles = new ArrayList<>();

    public CircuitPulse2DTask(CircuitPulse2D circuitPulse2D) {
        super(circuitPulse2D);
        startTask();
    }

    public int getNbParticleAlive() {
        return particles.size();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        updateParticles();
        removeDeadParticles();
        spawnNewParticles();
        return getParticlesToDisplay();
    }

    private void updateParticles() {
        particles.forEach(particle -> particle.update(maxSize, trailSize));
    }

    private void removeDeadParticles() {
        particles.removeIf(Particle::isDead);
    }

    protected void spawnNewParticles() {
        particles.addAll(
                Particle.build(spawner, speed, animation.getPointDefinition())
        );
    }

    private List<AnimationPointData> getParticlesToDisplay() {
        return particles.stream()
                .flatMap(particle -> particle.getDisplay().stream())
                .collect(Collectors.toList());
    }

    protected static class Particle {
        private final double speed;
        private final APointDefinition pointDefinition;
        private Vector2D location;
        private double angle;
        private LinkedList<Vector2D> trailLocations = new LinkedList<>();
        private boolean isInside = true;

        public Particle(Vector2D location, double angle, double speed, APointDefinition pointDefinition) {
            this.location = location;
            this.angle = angle;
            this.speed = speed;
            this.pointDefinition = pointDefinition;
        }

        public static Collection<Particle> build(ParticlesToSpawn particlesToSpawn, Double speed, APointDefinition pointDefinition) {
            return particlesToSpawn.stream()
                    .flatMap(particleToSpawn -> {
                        final Stream.Builder<Particle> builder = Stream.builder();
                        for (int i = 0; i < particleToSpawn.getNbParticleToSpawn(); ++i) {
                            int angleFactor = RANDOM.nextInt(NB_ANGLES);
                            builder.add(new Particle(
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

        public void update(double maxSize, int trailSize) {
            updateDirection();
            move(maxSize, trailSize);
        }

        public boolean isDead() {
            return (!isInside) && trailLocations.isEmpty();
        }

        public void updateDirection() {
            if (isInside) {
                double directionChangeRandom = RANDOM.nextDouble();
                if (directionChangeRandom > 0.95) {
                    if (directionChangeRandom > 0.975) {
                        angle += BRACE_ANGLE;
                    } else {
                        angle -= BRACE_ANGLE;
                    }
                }
            }
        }

        public void move(double maxSize, int trailSize) {
            if (isInside) {
                trailLocations.add(0, location);
                if (trailLocations.size() > trailSize) {
                    trailLocations = new LinkedList<>(trailLocations.subList(0, trailSize));
                }
                location = getAfterMoveLocation();
                checkParticleIsInside(maxSize);
            } else {
                trailLocations.removeLast();
            }
        }

        public Vector2D getAfterMoveLocation() {
            return new Vector2D(
                    location.getX() + (Math.cos(angle) * speed),
                    location.getY() + (Math.sin(angle) * speed)
            );
        }

        private void checkParticleIsInside(double maxSize) {
            isInside = Math.abs(location.getX()) < maxSize &&
                       Math.abs(location.getY()) < maxSize;
        }

        public List<AnimationPointData> getDisplay() {
            List<AnimationPointData> pointsData = new ArrayList<>();
            if (isInside) {
                pointsData.add(new AnimationPointData(
                        new Vector(location.getX(), 0, location.getY()),
                        givenPointDefinition -> pointDefinition
                ));
            }
            pointsData.addAll(trailLocations.stream()
                    .map(trailLocation ->
                            new AnimationPointData(
                                    new Vector(trailLocation.getX(), 0, trailLocation.getY()),
                                    givenPointDefinition -> pointDefinition
                            )
                    )
                    .collect(Collectors.toList()));
            return pointsData;
        }
    }

}
