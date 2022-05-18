package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.line.Line;
import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class LineHandsOfClockPresetExecutor extends AAnimationPresetExecutor<LineBuilder> {

    public LineHandsOfClockPresetExecutor() {
        super(LineBuilder.class);
    }

    @Override
    protected void apply(LineBuilder lineBuilder1, JavaPlugin plugin) {
        
        //Hour hand
        lineBuilder1.setDirection(new Constant<>(new Vector(1,0,0)));
        lineBuilder1.setLength(new Constant<>(2.0));
        lineBuilder1.setMainParticle(new ParticleTemplate("REDSTONE", new Color(184, 115, 51), null));
        lineBuilder1.setTicksDuration(60*20);
        lineBuilder1.setShowPeriod(new Constant<>(2));
        lineBuilder1.setNbPoints(new Constant<>(20));

        lineBuilder1.setRotation(
                new Vector(0, 0, 1),
                new CallbackVariable<>( iterationCount -> {
                    if(iterationCount%40==0){
                        return Math.PI/12;
                    }
                    else{
                        return Math.PI*2;
                    }
                }
                )
        );
        Line mainHand = lineBuilder1.getAnimation();
        

        //Minute hand
        lineBuilder1.setDirection(new Constant<>(new Vector(0,1,0)));
        lineBuilder1.setLength(new Constant<>(3.0d));
        lineBuilder1.setNbPoints(new Constant<>(30));
        lineBuilder1.setRotation(
                new Vector(0, 0, 1),
                new CallbackVariable<>( iterationCount -> {
                    if(iterationCount%10==0){
                        return Math.PI/48;
                    }
                    else{
                        return Math.PI*2;
                    }
                }
                )
        );
        Line minuteHand = lineBuilder1.getAnimation();
        
        lineBuilder1.setLength(0.01);
        lineBuilder1.setNbPoints(1);
        lineBuilder1.setTicksDuration(1);
        lineBuilder1.setShowPeriod(0);
        lineBuilder1.addAnimationEndedCallback(animationEnding -> {
            mainHand.show();
            minuteHand.show();
        });

    }
}
