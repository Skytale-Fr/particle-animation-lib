package fr.skytale.particleanimlib.animation.animation.obj.preset;

import fr.skytale.particleanimlib.animation.animation.obj.ObjBuilder;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class ShuttleObjPresetExecutor extends AAnimationPresetExecutor<ObjBuilder> {

    public ShuttleObjPresetExecutor() {
        super(ObjBuilder.class);
    }

    @Override
    protected void apply(ObjBuilder objBuilder, JavaPlugin plugin) {
        objBuilder.setRotation(Orientation.EAST, new Vector(0,1,0), Math.PI / 250);
        objBuilder.setObjFileName("shuttle.obj");
        objBuilder.setScale(3);
        objBuilder.setDistanceBetweenParticles(1);
        objBuilder.setMinAngleBetweenFaces(Math.PI / 100);
        objBuilder.setMinFaceArea(0.001);
        objBuilder.setTicksDuration(400);
        objBuilder.setShowPeriod(new Constant<>(2));
    }
}
