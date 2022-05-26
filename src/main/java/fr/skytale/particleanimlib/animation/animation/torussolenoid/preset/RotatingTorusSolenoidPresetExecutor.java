package fr.skytale.particleanimlib.animation.animation.torussolenoid.preset;

import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoidBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RotatingTorusSolenoidPresetExecutor extends AAnimationPresetExecutor<TorusSolenoidBuilder> {

    public RotatingTorusSolenoidPresetExecutor() {
        super(TorusSolenoidBuilder.class);
    }

    @Override
    protected void apply(TorusSolenoidBuilder torusSolenoidBuilder, JavaPlugin plugin) {
        torusSolenoidBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        torusSolenoidBuilder.setNbPoints(200);
        torusSolenoidBuilder.setTorusRadius(6);
        torusSolenoidBuilder.setSolenoidRadius(1.5d);
        torusSolenoidBuilder.setTicksDuration(4860);

        torusSolenoidBuilder.setTorusSolenoidModifierNumerator(9);
        torusSolenoidBuilder.setTorusSolenoidModifierDenominator(4);

        torusSolenoidBuilder.setRotation(
                new CallbackWithPreviousValueVariable<>(
                        new Vector(0, 1, 0),
                        (iterationCount, previousValue) -> previousValue.add(new Vector((Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4)).normalize()
                ),
                Math.PI / 80
        );

        torusSolenoidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        torusSolenoidBuilder.setShowPeriod(new Constant<>(1));
    }
}
