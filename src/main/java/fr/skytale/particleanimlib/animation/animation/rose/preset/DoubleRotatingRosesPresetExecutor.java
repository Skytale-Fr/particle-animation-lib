package fr.skytale.particleanimlib.animation.animation.rose.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.animation.rose.Rose;
import fr.skytale.particleanimlib.animation.animation.rose.RoseBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;


public class DoubleRotatingRosesPresetExecutor extends AAnimationPresetExecutor<RoseBuilder> {

    public DoubleRotatingRosesPresetExecutor() {
        super(RoseBuilder.class);
    }

    @Override
    protected void apply(RoseBuilder roseBuilder, JavaPlugin plugin) {

        //First rose
        RoseBuilder roseBuilder1 = new RoseBuilder();
        roseBuilder1.setDirectorVectors(new Vector(0, 0, 1), new Vector(1, 0, 0));
        roseBuilder1.setNbPoints(300);
        roseBuilder1.setRadius(5);
        roseBuilder1.setRoseModifierNumerator(3d);
        roseBuilder1.setRoseModifierDenominator(2);
        roseBuilder1.setTicksDuration(20*10);
        roseBuilder1.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255,0,0), null));
        roseBuilder1.setShowPeriod(new Constant<>(3));
        roseBuilder1.setJavaPlugin(plugin);
        roseBuilder1.setPosition(roseBuilder.getPosition());
        roseBuilder1.setRotation(new Vector(0,1,0), new Constant<>(Math.PI/210));

        Rose firstRose = roseBuilder1.getAnimation();

        //Second rose
        RoseBuilder roseBuilder2 = new RoseBuilder();
        roseBuilder2.setDirectorVectors(new Vector(0, 0, 1), new Vector(1, 0, 0));
        roseBuilder2.setNbPoints(300);
        roseBuilder2.setRadius(5);
        roseBuilder2.setRoseModifierNumerator(3d);
        roseBuilder2.setRoseModifierDenominator(2);
        roseBuilder2.setTicksDuration(20*10);
        roseBuilder2.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255,0,0), null));
        roseBuilder2.setShowPeriod(new Constant<>(3));
        roseBuilder2.setJavaPlugin(plugin);
        roseBuilder2.setPosition(roseBuilder.getPosition());
        roseBuilder2.setRotation(new Vector(0,1,0), new Constant<>(-Math.PI/200));

        Rose secondRose = roseBuilder2.getAnimation();

        //Builder to merge the two roses
        roseBuilder.setNbPoints(1);
        roseBuilder.setRadius(0.1d);
        roseBuilder.setTicksDuration(1);
        roseBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255,0,0), null));
        roseBuilder.addAnimationEndedCallback( result ->{
            firstRose.show();
            secondRose.show();
        });

//        roseBuilder.setDirectorVectors(new Vector(0, 0, 1), new Vector(1, 0, 0));
//        roseBuilder.setNbPoints(200);
//        roseBuilder.setRadius(5);
//        roseBuilder.setRoseModifierNumerator(3d);
//        roseBuilder.setRoseModifierDenominator(2);
//        roseBuilder.setTicksDuration(20*10);
//        roseBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255,0,0), null));
//        roseBuilder.setShowPeriod(new Constant<>(3));
//
//        //First rose
//        roseBuilder.setRotation(new Vector(0,1,0), new Constant<>(Math.PI/210));
//        Rose firstRose = roseBuilder.getAnimation();
//
//        //Second rose
//        roseBuilder.setRotation(new Vector(0,1,0), new Constant<>(-Math.PI/200));
//        Rose secondRose = roseBuilder.getAnimation();
//
//        //Builder to merge the two roses
//        roseBuilder.setNbPoints(1);
//        roseBuilder.setRadius(0.1d);
//        roseBuilder.setTicksDuration(1);
//        roseBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255,0,0), null));
//        roseBuilder.addAnimationEndedCallback( result ->{
//            firstRose.show();
//            secondRose.show();
//        });
    }
}
