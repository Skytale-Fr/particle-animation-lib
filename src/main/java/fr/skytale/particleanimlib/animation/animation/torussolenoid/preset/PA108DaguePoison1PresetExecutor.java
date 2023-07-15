package fr.skytale.particleanimlib.animation.animation.torussolenoid.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoidBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedRelativeAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.*;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA108DaguePoison1PresetExecutor extends AAnimationPresetExecutor<TorusSolenoidBuilder> {

    public PA108DaguePoison1PresetExecutor() {
        super(TorusSolenoidBuilder.class);
    }

    @Override
    protected void apply(TorusSolenoidBuilder torusSolenoidBuilder, JavaPlugin plugin) {
        torusSolenoidBuilder.setRotation(
                new Vector(1, 0, 0),
                new Vector(0, 1, 0),
                new Vector(0,1,0),
                Math.PI/124
        );
        torusSolenoidBuilder.setNbPoints(100);
//        torusSolenoidBuilder.setTorusSolenoidModifierNumerator(new DoublePeriodicallyEvolvingVariable(1.0d, 2.0d, 40));
        torusSolenoidBuilder.setTorusSolenoidModifierNumerator(7d);
        torusSolenoidBuilder.setTorusSolenoidModifierDenominator(2);
        torusSolenoidBuilder.setSolenoidRadius(0.5d);
//        torusSolenoidBuilder.setTorusRadius(new DoublePeriodicallyEvolvingVariable(2d,0.01));
        torusSolenoidBuilder.setTorusRadius(new CallbackVariable<>(iterationCount -> Math.exp(iterationCount/40d)));

        torusSolenoidBuilder.setPosition(
                new LocatedRelativeAnimationPosition(
                        torusSolenoidBuilder.getOriginLocation(),
                        new VectorPeriodicallyEvolvingVariable(
                                new Vector(0,0,0),
                                new Vector(0,0.1,0)
                        )
                )
        );

        torusSolenoidBuilder.setTicksDuration(1);
        torusSolenoidBuilder.setShowPeriod(1);


        CircleBuilder explodingLinesCircleBuilder = new CircleBuilder();
        explodingLinesCircleBuilder.setJavaPlugin(plugin);
        explodingLinesCircleBuilder.setPosition(
                new LocatedAnimationPosition(
                        torusSolenoidBuilder.getOriginLocation()
                )
        );
        explodingLinesCircleBuilder.applyPreset(AnimationPreset.CIRCLE_EXPLODING_LINES, plugin);

        torusSolenoidBuilder.setAnimationEndedCallback(task -> explodingLinesCircleBuilder.getAnimation().show());
    }
}
