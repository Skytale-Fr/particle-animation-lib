package fr.skytale.particleanimlib.animation.animation.torussolenoid.preset;

import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoidBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class TorusSolenoid2PresetExecutor extends AAnimationPresetExecutor<TorusSolenoidBuilder> {

    public TorusSolenoid2PresetExecutor() {
        super(TorusSolenoidBuilder.class);
    }

    @Override
    protected void apply(TorusSolenoidBuilder torusSolenoidBuilder, JavaPlugin plugin) {

        double endRadius = 2d;
        int tickDuration = 20 * 20;

        torusSolenoidBuilder.setRotation(
                new Vector(0, 1, 0),
                new Vector(1, 0, 0),
                new Vector(1, 0, 0),
                Math.PI / 192);

        torusSolenoidBuilder.setNbPoints((int) (endRadius * 5));

        torusSolenoidBuilder.setTorusRadius(new CallbackVariable<>(iterationCount -> Math.cos(iterationCount / 10d) / 4 + endRadius));

        torusSolenoidBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.025f, new Vector(0, 0, 0), (ParticleData) null));
        torusSolenoidBuilder.setTicksDuration(tickDuration);

        torusSolenoidBuilder.setSolenoidRadius(endRadius / 4);
        torusSolenoidBuilder.setTorusSolenoidModifierNumerator(9);
    }
}
