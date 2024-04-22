package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.line.Line;
import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class LineHandsOfClockPresetExecutor extends AAnimationPresetExecutor<LineBuilder> {

    public LineHandsOfClockPresetExecutor() {
        super(LineBuilder.class);
    }

    @Override
    protected void apply(LineBuilder lineBuilder1, JavaPlugin plugin) {

        //Hour hand
        lineBuilder1.setPoint1OnPosition();
        lineBuilder1.setFromPositionToPoint2(new Constant<>(new Vector(1, 0, 0)), new Constant<>(2d));
        lineBuilder1.setPointDefinition(new ParticleTemplate(Particle.REDSTONE, Color.fromRGB(184, 115, 51)));
        lineBuilder1.setTicksDuration(60 * 20);
        lineBuilder1.setShowPeriod(new Constant<>(2));
        lineBuilder1.setNbPoints(new Constant<>(20));

        lineBuilder1.setRotation(
                new Vector(0, 0, 1),
                new CallbackVariable<>(iterationCount -> {
                    if (iterationCount % 40 == 0) {
                        return Math.PI / 12;
                    } else {
                        return Math.PI * 2;
                    }
                }
                )
        );
        Line mainHand = lineBuilder1.getAnimation();


        //Minute hand
        lineBuilder1.setFromPositionToPoint2(new Constant<>(new Vector(0, 1, 0)), new Constant<>(3d));
        lineBuilder1.setNbPoints(new Constant<>(30));
        lineBuilder1.setRotation(
                new Vector(0, 0, 1),
                new CallbackVariable<>(iterationCount -> {
                    if (iterationCount % 10 == 0) {
                        return Math.PI / 48;
                    } else {
                        return Math.PI * 2;
                    }
                }
                )
        );
        Line minuteHand = lineBuilder1.getAnimation();
        lineBuilder1.setPoint2OnPosition();
        lineBuilder1.setNbPoints(1);
        lineBuilder1.setTicksDuration(1);
        lineBuilder1.setShowPeriod(0);
        lineBuilder1.addAnimationEndedCallback(animationEnding -> {
            mainHand.show();
            minuteHand.show();
        });

    }
}
