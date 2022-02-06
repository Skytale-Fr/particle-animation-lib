package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class CollisionBuilder<T, K extends AAnimationTask> {

    private JavaPlugin javaPlugin;
    private IVariable<Integer> collisionPeriod = new Constant<>(1);
    private Function<K, Collection<T>> collector;
    private IVariable<Integer> collectorPeriod = new Constant<>(1);
    private Set<BiPredicate<T, K>> filters = new HashSet<>();
    private Set<CollisionProcessor<T, K>> collisionProcessors = new HashSet<>();

    public void setJavaPlugin(JavaPlugin javaPlugin) { this.javaPlugin = javaPlugin; }

    public void setCollisionPeriod(IVariable<Integer> collisionPeriod) { this.collisionPeriod = collisionPeriod; }

    public void setPotentialCollidingTargetsCollector(Function<K, Collection<T>> collector) { this.collector = collector; }
    public void setPotentialCollidingTargetsCollector(Function<K, Collection<T>> collector, IVariable<Integer> collectorPeriod) {
        this.collector = collector;
        this.collectorPeriod = collectorPeriod;
    }

    public void addPotentialCollidingTargetsFilter(BiPredicate<T, K> filter) { this.filters.add(filter); }

    public void addCollisionProcessor(CollisionProcessor<T, K> collisionProcessor) { this.collisionProcessors.add(collisionProcessor); }

    // TODO: build() : CollisionHandler<T, K extends AAnimationTask>
    public CollisionHandler<T, K> build() {
        if(this.javaPlugin == null)
            throw new IllegalStateException("Unable to build the CollisionHandler because you did not provided any java plugin to the builder.");

        if(this.collector == null)
            throw new IllegalStateException("Unable to build the CollisionHandler because you did not provided any collector function to the builder.");

        Map<CollisionTestType, Collection<CollisionProcessor<T, K>>> collisionProcessorsByType = new HashMap<>();
        this.collisionProcessors.forEach(collisionProcessor -> {
            collisionProcessorsByType.computeIfAbsent(collisionProcessor.getCollisionTestType(), k -> new HashSet<>()).add(collisionProcessor);
        });
        return new CollisionHandler<>(javaPlugin, collisionPeriod, collector, collectorPeriod, filters, collisionProcessorsByType);
    }

}
