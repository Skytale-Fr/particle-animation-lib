package fr.skytale.particleanimlib.animation.animation.obj.preset;

import fr.skytale.particleanimlib.animation.animation.obj.ObjBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
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
        objBuilder.setRotation(
                new CallbackVariable<>(iterationCount ->
                        iterationCount == 0 ? new Vector(1, 0, 0) : new Vector(0, 1, 0))
                ,
                new CallbackVariable<>(iterationCount -> iterationCount == 0 ? -Math.PI / 4 : Math.PI / 400));
        objBuilder.setObjFileName("shuttle.obj");
        objBuilder.setScale(3);
        objBuilder.setDistanceBetweenParticles(1);
        objBuilder.setMinAngleBetweenFaces(Math.toRadians(0.0000000001));
        objBuilder.setTicksDuration(400);
        objBuilder.setShowPeriod(new Constant<>(2));
    }
}
