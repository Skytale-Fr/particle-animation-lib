package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.IPosition;
import fr.skytale.particleanimlib.animation.attribute.var.*;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class PA004RitaLongPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA004RitaLongPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {

        //TODO a supprimer, que pr le test
        Location location = ((AAnimationPosition)(sphereBuilder.getPosition())).toIVariableLocation().getCurrentValue(0);
        World monde = location.getWorld();
        sphereBuilder.setPosition(new LocatedAnimationPosition(new Location(monde,-66.5,-52,-17.5)));

        double startRadius = 15;
        double middleRadius = 4;

        //End of animation
        SphereBuilder sphereBuilderEnd = new SphereBuilder();
        sphereBuilderEnd.setJavaPlugin(plugin);
        sphereBuilderEnd.setPosition(sphereBuilder.getPosition());
        sphereBuilderEnd.setNbPoints(60);
        sphereBuilderEnd.setSphereType(Sphere.Type.FULL);
        sphereBuilderEnd.setTicksDuration(20*1);
        sphereBuilderEnd.setShowPeriod(0);
        sphereBuilderEnd.setRotation(new Vector(0,1,0), Math.PI/24);
        sphereBuilderEnd.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.5f, new Vector(0.3,0.3,0.3), (ParticleData) null));
        sphereBuilderEnd.setRadius(
                new CallbackVariable<>(iterationCount -> Math.exp(iterationCount-4)+4)
        );

        //Middle of animation
        SphereBuilder sphereBuilderMiddle = new SphereBuilder();
        sphereBuilderMiddle.setJavaPlugin(plugin);
        sphereBuilderMiddle.setPosition(sphereBuilder.getPosition());
        sphereBuilderMiddle.setNbPoints(20);
        sphereBuilderMiddle.setSphereType(Sphere.Type.FULL);
        sphereBuilderMiddle.setTicksDuration(20*5);
        sphereBuilderMiddle.setShowPeriod(0);
        sphereBuilderMiddle.setRadius(middleRadius);
        sphereBuilderMiddle.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.03f, new Vector(0.1,0.1,0.1), (ParticleData) null));
        sphereBuilderMiddle.setRotation(new Vector(0,1,0), Math.PI/24);
        sphereBuilderMiddle.setAnimationEndedCallback(animationEnding -> sphereBuilderEnd.getAnimation().show());

        //Start of animation
        SphereBuilder sphereBuilderStart = new SphereBuilder();
        sphereBuilderStart.setJavaPlugin(plugin);
        sphereBuilderStart.setPosition(sphereBuilder.getPosition());
        sphereBuilderStart.setNbPoints(20);
        sphereBuilderStart.setSphereType(Sphere.Type.FULL);
        sphereBuilderStart.setTicksDuration(20*8);
        sphereBuilderStart.setShowPeriod(0);
        sphereBuilderStart.setRadius(new DoublePeriodicallyEvolvingVariable(startRadius,  (middleRadius-startRadius)/(20*8)));
        sphereBuilderStart.setRotation(new VectorPeriodicallyEvolvingVariable(new Vector(-1,1,1), new Vector(9,-8,-1)), Math.PI/12);
        sphereBuilderStart.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.01f, new Vector(0.3,0.3,0.3), (ParticleData) null));
        sphereBuilderStart.setAnimationEndedCallback(animationEnding -> sphereBuilderMiddle.getAnimation().show());

        sphereBuilder.setTicksDuration(1);
        sphereBuilder.setNbPoints(1);
        sphereBuilder.setRadius(0.1);
        sphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 0f));
        sphereBuilder.setAnimationEndedCallback(animationEnding -> sphereBuilderStart.getAnimation().show());
    }
}
