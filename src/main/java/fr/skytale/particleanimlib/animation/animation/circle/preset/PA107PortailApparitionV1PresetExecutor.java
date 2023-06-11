package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class PA107PortailApparitionV1PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA107PortailApparitionV1PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {

        double startRadius = 20d;
        double endRadius = 4d;

        //Parties 3
        circleBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_3,plugin);
        Circle p3 = circleBuilder.getAnimation();
//        circleBuilder.setRotation(
//                new Vector(0, 1, 0),
//                new Vector(0, 0, 1),
//                new Vector(1,0,0),
//                Math.PI/12);
//        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.DRAGON_BREATH,0f));//, java.awt.Color.getHSBColor(0, 100, 46)));
//        circleBuilder.setShowPeriod(5);
//        Circle p3_1 = circleBuilder.getAnimation();
//        circleBuilder.setShowPeriod(0);

////        //Partie 2.2
////        circleBuilder.setNbPoints(3,true);
////        circleBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(endRadius+2,   -2d/(20*2)));
////
////        circleBuilder.setRotation(
////                new Vector(0, 1, 0),
////                new Vector(0, 0, 1),
////                new Vector(1,0,0),
////                Math.PI/144);
////
////        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.WAX_ON, 1f));
////        circleBuilder.setTicksDuration(20*2);
////        Circle p2_2 = circleBuilder.getAnimation();
//
        //Parties 2
        circleBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_2_1, plugin);
        circleBuilder.setAnimationEndedCallback(task -> {
            p3.show();
//            p3_1.show();
        });
        Circle p2_1 = circleBuilder.getAnimation();
//        circleBuilder.setRotation(
//                new Vector(0, 1, 0),
//                new Vector(0, 0, 1),
//                new Vector(1,0,0),
//                Math.PI/144);
//        circleBuilder.setNbPoints(3,true);
//        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.WAX_ON, 1, 1f, new Vector(0.1,0.1,0.1), (ParticleData) null));
//        Circle p2_2= circleBuilder.getAnimation();

        //Partie 1
        circleBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_1_V2, plugin);
        circleBuilder.setAnimationEndedCallback(task -> {
            p2_1.show();
//            p2_2.show();
        });
        Circle p1 = circleBuilder.getAnimation();


        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setTicksDuration(1);
        circleBuilder.setAnimationEndedCallback(task -> {
            p1.show();
        });
    }
}
