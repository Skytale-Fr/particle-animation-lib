package fr.skytale.particleanimlib.animation.animation.obj.preset;

import fr.skytale.particleanimlib.animation.animation.obj.ObjBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class CubeObjPresetExecutor extends AAnimationPresetExecutor<ObjBuilder> {

    public CubeObjPresetExecutor() {
        super(ObjBuilder.class);
    }

    @Override
    protected void apply(ObjBuilder objBuilder, JavaPlugin plugin) {
        objBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(Math.PI / 20));
        objBuilder.setObjFileName("cube.obj");
        objBuilder.setScale(2);
        objBuilder.setDistanceBetweenParticles(0.2);

        objBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        objBuilder.setTicksDuration(400);
        objBuilder.setShowPeriod(new Constant<>(2));
    }
}
