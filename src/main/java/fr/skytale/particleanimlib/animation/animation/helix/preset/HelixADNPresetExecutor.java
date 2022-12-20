package fr.skytale.particleanimlib.animation.animation.helix.preset;

import fr.skytale.particleanimlib.animation.animation.helix.HelixBuilder;
import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.LocationPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class HelixADNPresetExecutor extends AAnimationPresetExecutor<HelixBuilder> {

    public HelixADNPresetExecutor() {
        super(HelixBuilder.class);
    }

    @Override
    protected void apply(HelixBuilder helixBuilder, JavaPlugin plugin) {

        Location originLocation = helixBuilder.getOriginLocation();
        PolygonBuilder polygonBuilder = new PolygonBuilder();
        polygonBuilder.setPosition(new LocatedAnimationPosition(new LocationPeriodicallyEvolvingVariable(originLocation.clone(), new Location(originLocation.getWorld(), 0, 0.25, 0), 2)));
        polygonBuilder.setJavaPlugin(helixBuilder.getJavaPlugin());
        polygonBuilder.setDistanceFromCenterToVertices(4.24);
        polygonBuilder.setNbVertices(6);
        polygonBuilder.setDistanceBetweenPoints(new Constant<>(0.5));
        polygonBuilder.setPlaneFromNormalVector(new Vector(0, 0.25, 0));
        polygonBuilder.setTicksDuration(200);
        polygonBuilder.setRotation(new Vector(0, 1, 0), Math.PI / 24);
        polygonBuilder.setShowPeriod(new Constant<>(2));


        helixBuilder.setRadius(4);
        helixBuilder.setNbSpiral(2);
        helixBuilder.setHelixAngle(Math.PI / 24);
        helixBuilder.setNbTrailingHelixPoint(30);
        helixBuilder.setNbTrailingCentralPoint(3);
        helixBuilder.setTicksDuration(200);
        helixBuilder.setShowPeriod(2);
        helixBuilder.setPointDefinition(new SubAnimPointDefinition(polygonBuilder.getAnimation()));
    }
}
