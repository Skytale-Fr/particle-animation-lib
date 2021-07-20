package fr.skytale.particleanimlib.animation.animation.spiral.preset;

import fr.skytale.particleanimlib.animation.animation.polygon.Polygon;
import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.animation.spiral.Spiral;
import fr.skytale.particleanimlib.animation.animation.spiral.SpiralBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.LocationPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class SpiralADNPresetExecutor extends AAnimationPresetExecutor<SpiralBuilder> {

    public SpiralADNPresetExecutor() {
        super(SpiralBuilder.class);
    }

    @Override
    protected void apply(SpiralBuilder spiralBuilder, JavaPlugin plugin) {

        Location originLocation = spiralBuilder.getPosition().getType() == APosition.Type.ENTITY ? spiralBuilder.getPosition().getMovingEntity().getLocation() : spiralBuilder.getPosition().getLocation().getCurrentValue(0);
        PolygonBuilder polygonBuilder = new PolygonBuilder();
        polygonBuilder.setPosition(APosition.fromLocation(new LocationPeriodicallyEvolvingVariable(originLocation.clone(), new Location(originLocation.getWorld(), 0, 0.25, 0), 2)));
        polygonBuilder.setJavaPlugin(spiralBuilder.getJavaPlugin());
        polygonBuilder.setDistanceFromCenterToVertices(4.24);
        polygonBuilder.setNbVertices(6);
        polygonBuilder.setDistanceBetweenPoints(new Constant<>(0.5));
        polygonBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        polygonBuilder.setDirectorVectorsFromNormalVector(new Vector(0, 0.25, 0));
        polygonBuilder.setTicksDuration(200);
        polygonBuilder.setRotation(new Vector(0, 1, 0), Math.PI / 24);
        polygonBuilder.setShowPeriod(new Constant<>(2));
        Polygon polygon = polygonBuilder.getAnimation();


        spiralBuilder.setRadius(4);
        spiralBuilder.setNbSpiral(2);
        spiralBuilder.setAngleBetweenEachPoint(Math.PI / 24);
        spiralBuilder.setNbTrailingParticles(100);
        spiralBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 0, 0), null));
        spiralBuilder.setDirection(AnimationDirection.fromMoveVector(new Vector(0, 0.25, 0)));
        spiralBuilder.setTicksDuration(200);
        spiralBuilder.setShowPeriod(2);
        Spiral spiral = spiralBuilder.getAnimation();

        spiralBuilder.setTicksDuration(1);
        spiralBuilder.setCallback(result -> {
            spiral.show();
            polygon.show();
        });
    }
}
