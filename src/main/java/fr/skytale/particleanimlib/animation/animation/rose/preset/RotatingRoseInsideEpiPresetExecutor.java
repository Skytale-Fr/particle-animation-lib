package fr.skytale.particleanimlib.animation.animation.rose.preset;

import fr.skytale.particleanimlib.animation.animation.epi.Epi;
import fr.skytale.particleanimlib.animation.animation.epi.EpiBuilder;
import fr.skytale.particleanimlib.animation.animation.rose.Rose;
import fr.skytale.particleanimlib.animation.animation.rose.RoseBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;


public class RotatingRoseInsideEpiPresetExecutor extends AAnimationPresetExecutor<RoseBuilder> {

    public RotatingRoseInsideEpiPresetExecutor() {
        super(RoseBuilder.class);
    }

    @Override
    protected void apply(RoseBuilder roseBuilder, JavaPlugin plugin) {

        //Rose
        RoseBuilder roseBuilder1 = new RoseBuilder();       //TODO y'a un pb donc obligé de créer un autre builder au lieu d'utiliser roseBuilder - comme si pb de clonage
        roseBuilder1.setDirectorVectors(new Vector(0, 0, 1), new Vector(1, 0, 0));
        roseBuilder1.setNbPoints(200);
        roseBuilder1.setRadius(3);
        roseBuilder1.setRoseModifierNumerator(3d);
        roseBuilder1.setRoseModifierDenominator(4);
        roseBuilder1.setTicksDuration(20*10);
        roseBuilder1.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255,0,0), null));
        roseBuilder1.setShowPeriod(new Constant<>(3));
        roseBuilder1.setJavaPlugin(plugin);
        roseBuilder1.setPosition(roseBuilder.getPosition());
        roseBuilder1.setRotation(new Vector(0,1,0), new Constant<>(Math.PI/210));

        Rose rose = roseBuilder1.getAnimation();

        //Epi
        EpiBuilder epiBuilder = new EpiBuilder();
        epiBuilder.setDirectorVectors(new Vector(0, 0, 1), new Vector(1, 0, 0));
        epiBuilder.setNbPoints(200);
        epiBuilder.setRadius(3);
        epiBuilder.setEpiModifierNumerator(2d);
        epiBuilder.setEpiModifierDenominator(5);
        epiBuilder.setTicksDuration(20*10);
        epiBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255,0,0), null));
        epiBuilder.setShowPeriod(new Constant<>(3));
        epiBuilder.setJavaPlugin(plugin);
        epiBuilder.setPosition(roseBuilder.getPosition());
        epiBuilder.setRotation(new Vector(0,1,0), new Constant<>(-Math.PI/200));

        Epi epi = epiBuilder.getAnimation();

        //Builder to merge the two animations
        roseBuilder.setNbPoints(1);
        roseBuilder.setRadius(0.1d);
        roseBuilder.setTicksDuration(1);
        roseBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255,0,0), null));
        roseBuilder.addAnimationEndedCallback( result ->{
            rose.show();
            epi.show();
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
