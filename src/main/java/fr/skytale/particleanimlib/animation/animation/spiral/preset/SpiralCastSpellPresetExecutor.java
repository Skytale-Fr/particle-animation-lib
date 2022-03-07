package fr.skytale.particleanimlib.animation.animation.spiral.preset;

import fr.skytale.particleanimlib.animation.animation.spiral.SpiralBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class SpiralCastSpellPresetExecutor extends AAnimationPresetExecutor<SpiralBuilder> {

    public SpiralCastSpellPresetExecutor() {
        super(SpiralBuilder.class);
    }

    @Override
    protected void apply(SpiralBuilder spiralBuilder, JavaPlugin plugin) {

        APosition position = spiralBuilder.getPosition();
        APosition.Type type = position.getType();
        AnimationDirection direction = null;
        switch (type) {
            case ENTITY: {
                // Follow the entity's looking direction
                Entity entity = position.getMovingEntity();
                IVariable<Vector> player_direction = new CallbackVariable<>(iterationCount -> {
                    return entity.getLocation().clone().getDirection();
                });
                Vector evolution = player_direction.copy().getCurrentValue(0);
                evolution = new Vector(evolution.getX()/10,evolution.getY()/10,evolution.getZ()/10);

                direction = AnimationDirection.fromMoveVector(new VectorPeriodicallyEvolvingVariable(player_direction.getCurrentValue(0), evolution, 0));
                break;
            }
            default: {
                direction = AnimationDirection.fromMoveVector(new VectorPeriodicallyEvolvingVariable(new Vector(0, 0.1, 0), new Vector(0, 0.01, 0), 0));
                break;
            }
        }

        spiralBuilder.setRadius(2);
        spiralBuilder.setNbSpiral(3);
        spiralBuilder.setAngleBetweenEachPoint(Math.PI / 24);
        spiralBuilder.setNbTrailingParticles(3);
        spiralBuilder.setCentralPointDefinition(new ParticleTemplate("REDSTONE", new Color(0, 0, 255), null));
        spiralBuilder.setMainParticle(new ParticleTemplate("FIREWORKS_SPARK", null, null));
        spiralBuilder.setDirection(direction);
        spiralBuilder.setTicksDuration(200);
        spiralBuilder.setShowPeriod(2);
    }
}
