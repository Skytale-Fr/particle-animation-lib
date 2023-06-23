package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoid;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoidBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class PA107PortailApparitionPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA107PortailApparitionPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {

        //TODO a supprimer, que pr le test
        Location location = ((AAnimationPosition) (circleBuilder.getPosition())).toIVariableLocation().getCurrentValue(0);
        World monde = location.getWorld();
        circleBuilder.setPosition(new LocatedAnimationPosition(new Location(monde, -96.5, -52, -17.5)));

        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.FOOTSTEP));
        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);

        /******Third parts****/
        TorusSolenoidBuilder torusSolenoidBuilder = new TorusSolenoidBuilder();
        torusSolenoidBuilder.setPosition(circleBuilder.getPosition());
        torusSolenoidBuilder.setJavaPlugin(plugin);

        torusSolenoidBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_2, plugin);
        TorusSolenoid p3 = torusSolenoidBuilder.getAnimation();

        /****Second parts****/
        //2.3
        torusSolenoidBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_2, plugin);
        torusSolenoidBuilder.setNbPoints(40);
        torusSolenoidBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(246, 255, 0)));
        torusSolenoidBuilder.setTorusRadius(new CallbackVariable<>(iterationCount -> Math.cos(iterationCount / 10d) / 2 + 4));
        torusSolenoidBuilder.setTicksDuration(20 * 18);
        TorusSolenoid p2_3 = torusSolenoidBuilder.getAnimation();

        //2.2
        torusSolenoidBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_2, plugin);
        torusSolenoidBuilder.setNbPoints(10);
        torusSolenoidBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.WHITE_ASH, 1f));
        TorusSolenoid p2_2 = torusSolenoidBuilder.getAnimation();

        // 2.2 + 2.3
        circleBuilder.setTicksDuration(20 * 2);
        circleBuilder.setAnimationEndedCallback(task -> {
            p2_2.show();
            p2_3.show();
            p3.show();
        });
        Circle startP22P23 = circleBuilder.getAnimation();

        //2.1 - main torus
        torusSolenoidBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_2, plugin);
        TorusSolenoid p2_1 = torusSolenoidBuilder.getAnimation();

        // startp2 launched main torus
        circleBuilder.setTicksDuration(20 * 7);
        circleBuilder.setAnimationEndedCallback(task -> {
            p2_1.show();
            startP22P23.show();
        });
        Circle startP2 = circleBuilder.getAnimation();

        /***Partie 1***/
        // p1
        SphereBuilder p1Builder = new SphereBuilder();
        p1Builder.setPosition(circleBuilder.getPosition());
        p1Builder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_1, plugin);
        Sphere p1 = p1Builder.getAnimation();

        //Combine
        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setTicksDuration(1);
        circleBuilder.setAnimationEndedCallback(task -> {
            p1.show();
            startP2.show();
        });
    }
}
