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
        //Minute hand
        LineBuilder lineBuilder2 = new LineBuilder();
        lineBuilder2.setPoint1AtOrigin();
        lineBuilder2.setDirection(new Constant<>(new Vector(0,1,0)));
        lineBuilder2.setLength(new Constant<>(3.0d));
        lineBuilder2.setMainParticle(new ParticleTemplate("REDSTONE", new Color(184, 115, 51), null));
        lineBuilder2.setTicksDuration(60*20);
        lineBuilder2.setShowPeriod(new Constant<>(1));
        lineBuilder2.setNbPoints(new Constant<>(30));
        lineBuilder2.setPosition(lineBuilder1.getPosition());
        lineBuilder2.setJavaPlugin(plugin);
        lineBuilder2.setRotation(new Vector(0, 0, 1),Math.PI / 500);
        Line minute_hand = lineBuilder2.getAnimation();
        minute_hand.show();

        //Hour hand
        lineBuilder1.setPoint1AtOrigin();
        lineBuilder1.setDirection(new Constant<>(new Vector(1,0,0)));
        lineBuilder1.setLength(new Constant<>(2.0));
        lineBuilder1.setMainParticle(new ParticleTemplate("REDSTONE", new Color(184, 115, 51), null));
        lineBuilder1.setTicksDuration(60*20);
        lineBuilder1.setShowPeriod(new Constant<>(2));
        lineBuilder1.setNbPoints(new Constant<>(20));
        //lineBuilder1.setRotation(new Vector(0, 0, 1),Math.PI / 2000);


        lineBuilder1.setRotation(
                new Vector(0, 0, 1),
                new CallbackWithPreviousValueVariable<Double>(
                        0.5*Math.PI,
                        (iterationCount, previousValue) -> {
                            if(iterationCount%5==0){
                                //Move longest hand of the clock each half second
                                //Since the show period==2, iteration count+=10 each second
                                return previousValue + Math.PI/30;
                            }
                            else{
                                return previousValue;
                            }
                        }
                )
        );
    }
}
