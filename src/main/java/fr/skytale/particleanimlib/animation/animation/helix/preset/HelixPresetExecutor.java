package fr.skytale.particleanimlib.animation.animation.helix.preset;

import fr.skytale.particleanimlib.animation.animation.helix.HelixBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.awt.*;

public class HelixPresetExecutor extends AAnimationPresetExecutor<HelixBuilder> {

    public HelixPresetExecutor() {
        super(HelixBuilder.class);
    }

    @Override
    protected void apply(HelixBuilder helixBuilder, JavaPlugin plugin) {
        helixBuilder.setPosition(new DirectedLocationAnimationPosition(helixBuilder.getOriginLocation(), new Vector(0, 1, 0), 0.3d));
        helixBuilder.setRadius(2);
        helixBuilder.setNbHelix(new IntegerPeriodicallyEvolvingVariable(1, 1, 30));
        helixBuilder.setHelixAngle(Math.PI / 24);
        helixBuilder.setNbTrailingCentralPoint(3);
        helixBuilder.setNbTrailingHelixPoint(8);
        helixBuilder.setCentralPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, 1, 1, new Vector(0, 0, 0), new RegularColor(new Color(255, 0, 0))));
        helixBuilder.setTicksDuration(200);
        helixBuilder.setShowPeriod(2);
    }
}
