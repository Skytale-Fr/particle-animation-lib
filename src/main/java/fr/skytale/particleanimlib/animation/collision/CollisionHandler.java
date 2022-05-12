package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * A collision handler is something that will be used by the library
 * to perform collisions (checks, callbacks) with a lot of customization.<br />
 * If you want to create a CollisionHandler, you may want to use
 * the CollisionBuilder.
 * @see CollisionBuilder
 * @param <T> The type of target you want to perform collisions on
 * @param <K> The type of animation task you want to plug this collision handler to
 */
public class CollisionHandler<T, K extends AAnimationTask> {

    /**
     * A reference to the JavaPlugin, mainly used to register
     * synchronous task to be able to do API calls.
     */
    private JavaPlugin javaPlugin;
    /**
     * The collision period is ----- TODO Define with Ben ??? ---- <br />
     *
     * <b>/!\ This value will always be capped (minimum) at 1 /!\</b>
     */
    private IVariable<Integer> collisionPeriod;
    /**
     * The collector function. This function will be called to collect the
     * potential targets around the animation that would be checked for potential collisions.
     */
    private Function<K, Collection<T>> collector;
    /**
     * The collector period represents how often the collector will be called.
     * By default, it will be set to 1, to be called every tick.<br />
     * <b>/!\ This value will always be capped (minimum) at 1 /!\</b>
     */
    private IVariable<Integer> collectorPeriod;
    /**
     * A set of filters that will be applied to the targets collected (after every calls of the collector).
     */
    private Set<BiPredicate<T, K>> filters;
    /**
     * All the registered processors stored by their CollisionTestType to ensure optimisation access.
     * @see CollisionProcessor
     */
    private Map<CollisionTestType, Collection<CollisionProcessor<T, K>>> collisionProcessorsByType;

    /**
     * A cache variable to store collected targets after the calls of the collector.
     */
    private Collection<T> targetsCollected;
    /**
     * A cache variable to remember the "no collision ticks" of a target.<br />
     * This value represents how many ticks a target can't be part of the collision process
     * since the last time it collides.
     */
    private Map<T, Integer> noCollisionTicksMap = new HashMap<>();
    /**
     * A cache variable to remember when all the maps have been modified.
     * This is helpful for PER_PARTICLE collisions tests. As those tests will be called
     * more than once per iteration count, the value stored in 'noCollisionTicksMap' will be more than once per iteration count.
     * (Where MAIN_PARTICLE collisions tests are only raised once per iteration count with 'currentIterationBaseLocation' of the animation)
     */
    private Map<T, Integer> updateTimestampsMap = new HashMap<>();
    private Map<T, Integer> lastValidateTestTimestampsMap = new HashMap<>();
    private AAnimationTask<?> runner = null;

    protected CollisionHandler(JavaPlugin javaPlugin, IVariable<Integer> collisionPeriod, Function<K, Collection<T>> collector, IVariable<Integer> collectorPeriod, Set<BiPredicate<T, K>> filters, Map<CollisionTestType, Collection<CollisionProcessor<T, K>>> collisionProcessorsByType) {
        this.javaPlugin = javaPlugin;
        this.collisionPeriod = collisionPeriod;
        this.collector = collector;
        this.collectorPeriod = collectorPeriod;
        this.filters = filters;
        if(this.filters == null) this.filters = new HashSet<>();
        this.collisionProcessorsByType = collisionProcessorsByType;
        if(this.collisionProcessorsByType == null) this.collisionProcessorsByType = new HashMap<>();
    }

    /**
     * Calls the collector and store the collected targets.<br />
     *
     * <b>/!\ Careful /!\</b><br />
     * This method calls an inner class method to provide access to Bukkit API calls
     * by calling it synchronously.
     * @param iterationCount The iteration count of the current animation task
     * @param animationTask The animation task calling the method
     */
    public void collect(int iterationCount, K animationTask) {
        Bukkit.getScheduler().runTask(javaPlugin, () -> innerCollect(iterationCount, animationTask));
    }

    /**
     * Calls the collector and store the collected targets.
     * @param iterationCount The iteration count of the current animation task
     * @param animationTask The animation task calling the method
     */
    private void innerCollect(int iterationCount, K animationTask) {
        int collectorPeriodValue = this.collectorPeriod.getCurrentValue(iterationCount);
        if(collectorPeriodValue <= 0)
            throw new IllegalStateException(String.format("Your are not able to provide a collector period that is lower or equals to 0 (got '%d')", collectorPeriodValue));
        if(iterationCount == 0 || iterationCount % collectorPeriodValue == 0) {
            this.targetsCollected = this.collector.apply(animationTask);
            noCollisionTicksMap.values().removeIf(noCollisionTicks -> noCollisionTicks <= 0);

            // If we have collected all the targets, we need to process all the registered filters.
            applyFilters(animationTask);
        }
    }

    public void clearTimestamps() {
        this.updateTimestampsMap.clear();
        this.lastValidateTestTimestampsMap.clear();
    }

    /**
     * Apply registered filters to the collected targets.
     * @param animationTask The animation task calling the method
     */
    private void applyFilters(K animationTask) {
        if(this.targetsCollected == null) return;
        this.filters.forEach(filter -> {
            this.targetsCollected.removeIf(t -> !filter.test(t, animationTask));
        });
    }

    /**
     * Process collisions checks on previously collected targets (for every registered collision processors).<br />
     *
     * <b>/!\ Careful /!\</b><br />
     * This method calls an inner class method to provide access to Bukkit API calls
     * by calling it synchronously.
     * @param iterationCount The iteration count of the current animation task
     * @param collisionTestType The type of the collision called (see location param)
     * @param location The location of the process (PER_PARTICLE: the location of the particle, MAIN_POSITION: the location of the animation)
     * @param animationTask The animation task calling the method
     */
    public void processCollision(int iterationCount, CollisionTestType collisionTestType, Location location, K animationTask) {
        if(runner == null || runner.hasDurationEnded())
        {
            clearTimestamps();
            runner = animationTask;
        }
        Bukkit.getScheduler().runTask(javaPlugin, () -> innerProcessCollision(iterationCount, collisionTestType, location, animationTask));
    }

    /**
     * Process collisions checks on previously collected targets (for every registered collision processors).
     *
     * @param iterationCount The iteration count of the current animation task
     * @param collisionTestType The type of the collision called (see location param)
     * @param location The location of the process (PER_PARTICLE: the location of the particle, MAIN_POSITION: the location of the animation)
     * @param animationTask The animation task calling the method
     */
    private void innerProcessCollision(int iterationCount, CollisionTestType collisionTestType, Location location, K animationTask) {
        int collisionPeriodValue = this.collisionPeriod.getCurrentValue(iterationCount);
        int animationShowPeriod = animationTask.getCurrentShowPeriod();
        if(collisionPeriodValue <= 0)
            throw new IllegalStateException(String.format("Your are not able to provide a collision period that is lower or equals to 0 (got '%d')", collisionPeriodValue));
        if(iterationCount == 0 || iterationCount % collisionPeriodValue == 0) {
            Collection<CollisionProcessor<T, K>> collisionProcessors = collisionProcessorsByType.get(collisionTestType);
            if(collisionProcessors == null) return;
            collisionProcessors.forEach(collisionProcessor -> {
                CollisionPredicate<T, K> collisionTest = collisionProcessor.getCollisionTest();
                CollisionActionCallback<T, K> actionCallback = collisionProcessor.getActionCallback();
                targetsCollected.forEach(target -> {
                    // How many ticks before the target can be part of the collision process:
                    int noCollisionTicks = noCollisionTicksMap.getOrDefault(target, 0);
                    // The last iteration count when the 'noCollisionTicks' has been decreased:
                    int lastUpdateIterationCount = updateTimestampsMap.getOrDefault(target, -1);
                    // The last iteration count when the collision test has been validate:
                    int lastValidateTestIterationCount = lastValidateTestTimestampsMap.getOrDefault(target, -1);
                    // The two previous values help us to check if (for PER_PARTICLE collision tests) there is not
                    // more the one update / action callback per iteration count.
                    if (lastValidateTestIterationCount >= runner.getIterationCount()) return;
                    System.out.println(noCollisionTicksMap);

                    if(noCollisionTicks <= 0 && collisionTest.test(location, animationTask, target)) {
                        noCollisionTicks = actionCallback.run(animationTask, target);
                        if(noCollisionTicks >= 0) {
                            lastValidateTestTimestampsMap.put(target, runner.getIterationCount());
                            noCollisionTicksMap.put(target, noCollisionTicks);
                        }
                    } else if (animationTask == runner && lastUpdateIterationCount < runner.getIterationCount()) {
                        // Else we need to update noCollisionTicksMap
                        // When updating this map, we need to subtract not 1, but the number of ticks between every animation show() call
                        noCollisionTicksMap.put(target, Math.max(noCollisionTicks - animationShowPeriod * collisionPeriodValue, 0));
                        updateTimestampsMap.put(target, runner.getIterationCount());
                    }
                });
            });
        }
    }

}
