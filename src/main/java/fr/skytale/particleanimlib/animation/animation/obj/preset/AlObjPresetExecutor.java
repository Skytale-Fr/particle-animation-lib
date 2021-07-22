package fr.skytale.particleanimlib.animation.animation.obj.preset;

import fr.skytale.particleanimlib.animation.animation.obj.ObjBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class AlObjPresetExecutor extends AAnimationPresetExecutor<ObjBuilder> {

    public AlObjPresetExecutor() {
        super(ObjBuilder.class);
    }

    @Override
    protected void apply(ObjBuilder objBuilder, JavaPlugin plugin) {
        objBuilder.setObjFileName("al.obj");
        objBuilder.setScale(4);
        objBuilder.setDistanceBetweenParticles(1);

        objBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        objBuilder.setTicksDuration(400);
        objBuilder.setShowPeriod(new Constant<>(2));
    }
}
