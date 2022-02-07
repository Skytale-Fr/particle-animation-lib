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

public class CollisionHandler<T, K extends AAnimationTask> {

    private JavaPlugin javaPlugin;
    private IVariable<Integer> collisionPeriod;
    private long lastCollisionTimestamp = 0;
    private Function<K, Collection<T>> collector;
    private IVariable<Integer> collectorPeriod;
    private long lastCollectorTimestamp = 0;
    private Set<BiPredicate<T, K>> filters;
    private Map<CollisionTestType, Collection<CollisionProcessor<T, K>>> collisionProcessorsByType;

    private Collection<T> targetsCollected;
    private Map<T, Integer> noCollisionTicksMap = new HashMap<>();

    public CollisionHandler(JavaPlugin javaPlugin, IVariable<Integer> collisionPeriod, Function<K, Collection<T>> collector, IVariable<Integer> collectorPeriod, Set<BiPredicate<T, K>> filters, Map<CollisionTestType, Collection<CollisionProcessor<T, K>>> collisionProcessorsByType) {
        this.javaPlugin = javaPlugin;
        this.collisionPeriod = collisionPeriod;
        this.collector = collector;
        this.collectorPeriod = collectorPeriod;
        this.filters = filters;
        if(this.filters == null) this.filters = new HashSet<>();
        this.collisionProcessorsByType = collisionProcessorsByType;
        if(this.collisionProcessorsByType == null) this.collisionProcessorsByType = new HashMap<>();
    }

    public void collect(int iterationCount, K animationTask) {
        Bukkit.getScheduler().runTask(javaPlugin, () -> innerCollect(iterationCount, animationTask));
    }

    private void innerCollect(int iterationCount, K animationTask) {
        int collectorPeriodValue = this.collectorPeriod.getCurrentValue(iterationCount);
        if(collectorPeriodValue <= 0)
            throw new IllegalStateException(String.format("Your are not able to provide a collector period that is lower or equals to 0 (got '%d')", collectorPeriodValue));
        if(iterationCount == 0 || iterationCount % collectorPeriodValue == 0) {
            this.targetsCollected = this.collector.apply(animationTask);
            this.noCollisionTicksMap.clear();

            // If we have collected all the targets, we need to process all the registered filters.
            applyFilters(animationTask);
        }
    }

    private void applyFilters(K animationTask) {
        if(this.targetsCollected == null) return;
        this.filters.forEach(filter -> {
            this.targetsCollected.removeIf(t -> !filter.test(t, animationTask));
        });
    }

    public void processCollision(int iterationCount, CollisionTestType collisionTestType, Location location, K animationTask) {
        Bukkit.getScheduler().runTask(javaPlugin, () -> innerProcessCollision(iterationCount, collisionTestType, location, animationTask));
    }

    private void innerProcessCollision(int iterationCount, CollisionTestType collisionTestType, Location location, K animationTask) {
        int collisionPeriodValue = this.collisionPeriod.getCurrentValue(iterationCount);
        if(collisionPeriodValue <= 0)
            throw new IllegalStateException(String.format("Your are not able to provide a collision period that is lower or equals to 0 (got '%d')", collisionPeriodValue));
        if(iterationCount == 0 || iterationCount % collisionPeriodValue == 0) {
            Collection<CollisionProcessor<T, K>> collisionProcessors = collisionProcessorsByType.get(collisionTestType);
            if(collisionProcessors == null) return;
            collisionProcessors.forEach(collisionProcessor -> {
                CollisionPredicate<T, K> collisionTest = collisionProcessor.getCollisionTest();
                CollisionActionCallback<T, K> actionCallback = collisionProcessor.getActionCallback();
                targetsCollected.forEach(target -> {
                    int noCollisionTicks = noCollisionTicksMap.getOrDefault(target, 0);
                    if(noCollisionTicks == 0 && collisionTest.test(location, animationTask, target)) {
                        noCollisionTicks = actionCallback.run(animationTask, target);
                        if(noCollisionTicks >= 0) noCollisionTicksMap.put(target, noCollisionTicks);
                    } else noCollisionTicksMap.put(target, noCollisionTicks - 1); // Else we need to update noCollisionTicksMap
                });
            });
        }
    }

}
