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

/**
 * This class should be used to build a CollisionHandler to be registered in an AAnimationBuilder.
 * @param <T> The type of target you want to perform collisions on
 * @param <K> The type of animation task you want to plug the built collision handler to
 * @see CollisionHandler
 * @see AAnimationBuilder#addCollisionHandler(CollisionHandler) 
 */
public class CollisionBuilder<T, K extends AAnimationTask> {

    /**
     * A reference to the JavaPlugin, mainly used to register
     * synchronous task to be able to do API calls.
     */
    private JavaPlugin javaPlugin;
    /**
     * The collision period is ----- TODO Define with Ben ??? ----<br />
     * Default value set to a constant of 1.<br />
     *
     * <b>/!\ This value will always be capped (minimum) at 1 /!\</b>
     */
    private IVariable<Integer> collisionPeriod = new Constant<>(1);
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
    private IVariable<Integer> collectorPeriod = new Constant<>(1);
    /**
     * A set of filters that will be applied to the targets collected (after every calls of the collector).
     */
    private Set<BiPredicate<T, K>> filters = new HashSet<>();
    /**
     * All the registered processors stored by their CollisionTestType to ensure optimisation access.
     * @see CollisionProcessor
     */
    private Set<CollisionProcessor<T, K>> collisionProcessors = new HashSet<>();

    /**
     * Set a reference to the current JavaPlugin, mainly used to register
     * synchronous task to be able to do API calls.
     * @param javaPlugin The reference to the JavaPlugin
     */
    public void setJavaPlugin(JavaPlugin javaPlugin) { this.javaPlugin = javaPlugin; }

    /**
     * Set the value of the collision period. The collision period is ----- TODO Define with Ben ??? ----<br />
     * Default value set to a constant of 1.<br />
     *
     * <b>/!\ This value will always be capped (minimum) at 1 /!\</b>
     * @param collisionPeriod The value of the collision period
     */
    public void setCollisionPeriod(IVariable<Integer> collisionPeriod) { this.collisionPeriod = collisionPeriod; }

    /**
     * Set the collector function. This function will be called to collect the
     * potential targets around the animation that would be checked for potential collisions.
     * @param collector The collector function
     */
    public void setPotentialCollidingTargetsCollector(Function<K, Collection<T>> collector) { this.collector = collector; }

    /**
     * Set the collector function and the collector period. <br /><br />
     * - This function will be called to collect the
     * potential targets around the animation that would be checked for potential collisions.<br /><br />
     * - The collector period represents how often the collector will be called.<br />
     * By default, it will be set to 1, to be called every tick.<br />
     * <b>/!\ This value will always be capped (minimum) at 1 /!\</b>
     * @param collector The collector function
     * @param collectorPeriod The collector period value
     */
    public void setPotentialCollidingTargetsCollector(Function<K, Collection<T>> collector, IVariable<Integer> collectorPeriod) {
        this.collector = collector;
        this.collectorPeriod = collectorPeriod;
    }

    /**
     * Adds a filter that will be applied to the targets collected (after every calls of the collector).
     * @param filter A filter predicate
     */
    public void addPotentialCollidingTargetsFilter(BiPredicate<T, K> filter) { this.filters.add(filter); }

    /**
     * Adds a collision processor.
     * @see CollisionProcessor
     * @param collisionProcessor
     */
    public void addCollisionProcessor(CollisionProcessor<T, K> collisionProcessor) { this.collisionProcessors.add(collisionProcessor); }

    /**
     * Build a collision handler associated to the content you set / registered in this builder. <br />
     * The JavaPlugin should not be null. <br />
     * The collector function should not be null. <br />
     * The collision period should not be null. <br />
     * The collector period should not be null. <br />
     * @return A collision handler
     */
    public CollisionHandler<T, K> build() {
        if(this.javaPlugin == null)
            throw new IllegalStateException("Unable to build the CollisionHandler because you did not provided any java plugin to the builder.");
        if(this.collector == null)
            throw new IllegalStateException("Unable to build the CollisionHandler because you did not provided any collector function to the builder.");
        if(this.collisionPeriod == null)
            throw new IllegalArgumentException("Collision period should not be null.");
        if(this.collectorPeriod == null)
            throw new IllegalArgumentException("Collector period should not be null.");

        Map<CollisionTestType, Collection<CollisionProcessor<T, K>>> collisionProcessorsByType = new HashMap<>();
        this.collisionProcessors.forEach(collisionProcessor -> {
            collisionProcessorsByType.computeIfAbsent(collisionProcessor.getCollisionTestType(), k -> new HashSet<>()).add(collisionProcessor);
        });
        return new CollisionHandler<>(javaPlugin, collisionPeriod, collector, collectorPeriod, filters, collisionProcessorsByType);
    }

}
