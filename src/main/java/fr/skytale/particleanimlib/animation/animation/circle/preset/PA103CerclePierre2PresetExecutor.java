package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.polygon.Polygon;
import fr.skytale.particleanimlib.animation.animation.wave.Wave;
import fr.skytale.particleanimlib.animation.animation.wave.WaveBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedRelativeAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class PA103CerclePierre2PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA103CerclePierre2PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        double radius = 10d;
        circleBuilder.setTicksDuration(20*20);


        //Inner circle
        //CircleBuilder innerCircler = new CircleBuilder();
        //circleBuilder.setJavaPlugin(plugin);
        //circleBuilder.setPosition(circleBuilder.getPosition());
        circleBuilder.setRadius(radius/2);
        circleBuilder.setNbPoints((int) radius, true);
        circleBuilder.setPointDefinition(
                new ParticleTemplate(
                        ParticleEffect.DRAGON_BREATH,1,0.01f,new Vector(radius/4,0,radius/4), (ParticleData) null
                )
        );
        Circle innerCircle = circleBuilder.getAnimation();


        //Outer circle
        circleBuilder.setRadius(radius);
        circleBuilder.setNbPoints(80, true);
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.DRAGON_BREATH,0.005f));
        circleBuilder.setRotation(
                new Vector(0,1,0),
                Math.PI/100
        );
        AAnimationPosition position = (AAnimationPosition) circleBuilder.getPosition();
        circleBuilder.setPosition(
                new LocatedRelativeAnimationPosition(
                        position.toIVariableLocation(),
                        new CallbackVariable<>(iterationCount -> new Vector(0, Math.sin(iterationCount/2d) +1, 0))
                )
        );
        circleBuilder.setShowPeriod(2);
        Circle outerCircle = circleBuilder.getAnimation();

        circleBuilder.setTicksDuration(1);
        circleBuilder.setAnimationEndedCallback(animationEnding -> {
            outerCircle.show();
            innerCircle.show();
        });
    }
}
