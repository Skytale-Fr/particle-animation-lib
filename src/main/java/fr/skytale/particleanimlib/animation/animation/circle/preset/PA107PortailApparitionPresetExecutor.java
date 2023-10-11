package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoid;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoidBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.AnimationStopCondition;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class PA107PortailApparitionPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA107PortailApparitionPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.FOOTSTEP));
        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);

        TorusSolenoidBuilder torusSolenoidBuilder = new TorusSolenoidBuilder();
        torusSolenoidBuilder.setPosition(circleBuilder.getPosition());
        torusSolenoidBuilder.setTicksDuration(Integer.MAX_VALUE);
        torusSolenoidBuilder.setJavaPlugin(plugin);

        //Secondary torus redstone
        torusSolenoidBuilder.applyPreset(AnimationPreset.TORUS_SOLENOID_2, plugin);
        torusSolenoidBuilder.setNbPoints(20);
        torusSolenoidBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(246, 255, 0)));
        torusSolenoidBuilder.setTorusRadius(new CallbackVariable<>(iterationCount -> Math.cos(iterationCount / 10d) / 2 + 2));
        TorusSolenoid secondaryTorusRedstone = torusSolenoidBuilder.getAnimation();

        //Secondary torus ash
        torusSolenoidBuilder.applyPreset(AnimationPreset.TORUS_SOLENOID_2, plugin);
        torusSolenoidBuilder.setNbPoints(10);
        torusSolenoidBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.WHITE_ASH, 1f));
        TorusSolenoid secondaryTorusAsh = torusSolenoidBuilder.getAnimation();

        // Secondary toruses
        circleBuilder.setTicksDuration(20 * 2);
        circleBuilder.setAnimationEndedCallback(task -> {
            secondaryTorusAsh.setRotation(circleBuilder.getAnimation().getRotation());
            secondaryTorusAsh.setStopCondition(circleBuilder.getAnimation().getStopCondition());
            secondaryTorusAsh.show();

            secondaryTorusRedstone.setRotation(circleBuilder.getAnimation().getRotation());
            secondaryTorusRedstone.setStopCondition(circleBuilder.getAnimation().getStopCondition());
            secondaryTorusRedstone.show();
        });
        Circle startSecondaryToruses = circleBuilder.getAnimation();

        //Main torus
        torusSolenoidBuilder.applyPreset(AnimationPreset.TORUS_SOLENOID_2, plugin);
        TorusSolenoid firstTorus = torusSolenoidBuilder.getAnimation();

        //Combine
        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setTicksDuration(1);
        circleBuilder.setAnimationEndedCallback(task -> {
            AnimationStopCondition stopCondition = circleBuilder.getAnimation().getStopCondition();

            firstTorus.setRotation(circleBuilder.getAnimation().getRotation());
            firstTorus.setStopCondition(stopCondition);
            firstTorus.show();

            secondaryTorusAsh.setRotation(circleBuilder.getAnimation().getRotation());
            startSecondaryToruses.setStopCondition(stopCondition);
            startSecondaryToruses.show();
        });
    }
}
