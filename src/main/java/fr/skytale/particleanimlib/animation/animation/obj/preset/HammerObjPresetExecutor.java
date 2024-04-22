package fr.skytale.particleanimlib.animation.animation.obj.preset;

import fr.skytale.particleanimlib.animation.animation.obj.ObjBuilder;
import fr.skytale.particleanimlib.animation.animation.obj.ObjTask;
import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class HammerObjPresetExecutor extends AAnimationPresetExecutor<ObjBuilder> {

    private static final PARotation VERTICAL_HAMMER_ROTATION = new PARotation(
            ObjTask.U.clone().multiply(-1), ObjTask.W,
            ObjTask.V, ObjTask.V);

    public HammerObjPresetExecutor() {
        super(ObjBuilder.class);
    }

    @Override
    protected void apply(ObjBuilder objBuilder, JavaPlugin plugin) {
        objBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(Math.PI / 20));
        objBuilder.setObjFileName("hammer.obj");
        objBuilder.setScale(0.7);
        objBuilder.setDistanceBetweenParticles(1);
        objBuilder.setRotation(new CallbackVariable<>(iterationCount -> {
            PARotation rotation = new PARotation(VERTICAL_HAMMER_ROTATION);
            rotation.rotate(ObjTask.U, 1.55*Math.cos(iterationCount/20d));
            return rotation;
        }));

        objBuilder.setPointDefinition(new ParticleTemplate(Particle.REDSTONE, Color.fromRGB(255, 170, 0)));
        objBuilder.setTicksDuration(200);
        objBuilder.setShowPeriod(new Constant<>(2));
    }
}
