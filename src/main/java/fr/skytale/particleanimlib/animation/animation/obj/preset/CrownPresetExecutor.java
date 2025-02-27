package fr.skytale.particleanimlib.animation.animation.obj.preset;

import fr.skytale.particleanimlib.animation.animation.obj.ObjBuilder;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class CrownPresetExecutor extends AAnimationPresetExecutor<ObjBuilder> {

    public CrownPresetExecutor() {
        super(ObjBuilder.class);
    }

    @Override
    protected void apply(ObjBuilder objBuilder, JavaPlugin plugin) {
        objBuilder.setObjFileName("crown.obj");
        objBuilder.setScale(0.25);
        objBuilder.setDistanceBetweenParticles(0.5);

        objBuilder.setPointDefinition(new ParticleTemplate(Particle.REDSTONE, 1, 1, new Vector(0, 0, 0), Color.fromRGB(255, 215, 0)));
        objBuilder.setTicksDuration(100);
        objBuilder.setRotation(Orientation.DOWN, new Vector(0,1,0), Math.PI / 250);
        objBuilder.setShowPeriod(new Constant<>(3));
    }
}
