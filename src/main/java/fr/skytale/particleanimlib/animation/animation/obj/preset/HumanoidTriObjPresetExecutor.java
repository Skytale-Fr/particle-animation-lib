package fr.skytale.particleanimlib.animation.animation.obj.preset;

import fr.skytale.particleanimlib.animation.animation.obj.ObjBuilder;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class HumanoidTriObjPresetExecutor extends AAnimationPresetExecutor<ObjBuilder> {

    public HumanoidTriObjPresetExecutor() {
        super(ObjBuilder.class);
    }

    @Override
    protected void apply(ObjBuilder objBuilder, JavaPlugin plugin) {
        objBuilder.setObjFileName("humanoid_tri.obj");
        objBuilder.setRotation(Orientation.NORTH, new Vector(0,1,0), Math.PI / 250);
        objBuilder.setScale(1);
        objBuilder.setDistanceBetweenParticles(1);
        objBuilder.setTicksDuration(400);
        objBuilder.setShowPeriod(new Constant<>(2));
    }
}
