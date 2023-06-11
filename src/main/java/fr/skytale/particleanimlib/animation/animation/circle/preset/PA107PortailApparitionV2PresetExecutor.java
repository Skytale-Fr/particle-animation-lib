package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoid;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoidBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class PA107PortailApparitionV2PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA107PortailApparitionV2PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {

        //TODO a supprimer, que pr le test
        Location location = ((AAnimationPosition)(circleBuilder.getPosition())).toIVariableLocation().getCurrentValue(0);
        World monde = location.getWorld();
        circleBuilder.setPosition(new LocatedAnimationPosition(new Location(monde,-66.5,-52,-17.5)));

        //Second parts
//        circleBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_2_1, plugin);
////        circleBuilder.setAnimationEndedCallback(task -> {
////            p3.show();
//////            p3_1.show();
////        });
//        Circle p2_1 = circleBuilder.getAnimation();
        TorusSolenoidBuilder torusSolenoidBuilder = new TorusSolenoidBuilder();
        torusSolenoidBuilder.setPosition(circleBuilder.getPosition());
        torusSolenoidBuilder.setJavaPlugin(plugin);
        torusSolenoidBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_2_V2,plugin);
        TorusSolenoid p2_1 = torusSolenoidBuilder.getAnimation();

        /****First parts****/
        //  1.2
        circleBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_1_V2, plugin);
        Circle p1_2 = circleBuilder.getAnimation();

        //  1.1
        SphereBuilder sphereBuilder = new SphereBuilder();
        sphereBuilder.setJavaPlugin(plugin);
        sphereBuilder.setPosition(circleBuilder.getPosition());
        sphereBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_1_V5,plugin);
        Sphere p1_1 = sphereBuilder.getAnimation();

        //  1.0 to lauch second part of anim
        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setTicksDuration(20*8);
        circleBuilder.setAnimationEndedCallback(task -> {
            p2_1.show();
        });
        Circle p1_0 = circleBuilder.getAnimation();

        //Combine
        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setTicksDuration(1);
        circleBuilder.setAnimationEndedCallback(task -> {
            p1_1.show();  //10 sec
            p1_2.show();  //10 sec
            p1_0.show();  //8 sec
        });
    }
}
