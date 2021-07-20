package fr.skytale.particleanimlib.animation.animation.parabola.preset;

import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class ParabolaPlayerAimPresetExecutor extends AAnimationPresetExecutor<ParabolaBuilder> {

    public ParabolaPlayerAimPresetExecutor() {
        super(ParabolaBuilder.class);
    }

    @Override
    protected void apply(ParabolaBuilder parabolaBuilder, JavaPlugin plugin) {
        if (parabolaBuilder.getPosition() != null && parabolaBuilder.getPosition().getType() == APosition.Type.ENTITY) {
            Vector entityDirection = parabolaBuilder.getPosition().getMovingEntity().getLocation().getDirection();
            parabolaBuilder.setDirection(AnimationDirection.fromMoveVector(entityDirection));
        }
        parabolaBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 0, 0), null));
        parabolaBuilder.setTicksDuration(100);
        parabolaBuilder.setShowPeriod(new Constant<>(3));
    }
}
