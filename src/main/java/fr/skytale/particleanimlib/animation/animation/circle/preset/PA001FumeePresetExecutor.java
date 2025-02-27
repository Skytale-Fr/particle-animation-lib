package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA001FumeePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA001FumeePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        CircleBuilder subAnim1 = new CircleBuilder();
        subAnim1.setPosition(circleBuilder.getPosition());
        subAnim1.setJavaPlugin(circleBuilder.getJavaPlugin());
        subAnim1.setRadius(0.1);
        subAnim1.setNbPoints(1);
        subAnim1.setShowPeriod(20);
        subAnim1.setTicksDuration(20);
        subAnim1.setPointDefinition(new ParticleTemplate(Particle.EXPLOSION_NORMAL, 200, 0.01f, new Vector(2, 2, 2)));

        CircleBuilder subAnim2 = new CircleBuilder();
        subAnim2.setPosition(circleBuilder.getPosition());
        subAnim2.setJavaPlugin(circleBuilder.getJavaPlugin());
        subAnim2.setRadius(1);
        subAnim2.setNbPoints(2);
        subAnim2.setShowPeriod(20);
        subAnim2.setTicksDuration(20);
        subAnim2.setPointDefinition(new ParticleTemplate(Particle.EXPLOSION_LARGE, 10, 0f, new Vector(2, 2, 2)));

        circleBuilder.setNbPoints(1, true);
        circleBuilder.setRadius(1);
        circleBuilder.setTicksDuration(1);
        circleBuilder.setShowPeriod(0);
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.EXPLOSION_NORMAL));
        circleBuilder.setAnimationEndedCallback(animationEnding -> {
            subAnim1.getAnimation().show();
            subAnim2.getAnimation().show();
        });
        /*
        CAMPFIRE_COSY_SMOKE
        CAMPFIRE_SIGNAL_SMOKE
        CLOUD / EXPLOSION_NORMAL
        SMOKE_LARGE
        SMOKE_NORMAL
        EXPLOSION_LARGE / EXPLOSION_HUGE
         */
    }
}