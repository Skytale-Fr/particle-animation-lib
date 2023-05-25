package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class PA204AttaqueCorpsSol1PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA204AttaqueCorpsSol1PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.EXPLOSION_NORMAL, 1, 0f, new Vector(0, 0, 0), (ParticleData) null));
        circleBuilder.setNbPoints(10, true);
        circleBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(0.1, 0.2));
        circleBuilder.setTicksDuration(5);
        circleBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(0.5, 0.2));
        circleBuilder.setTicksDuration(5);
    }
}
