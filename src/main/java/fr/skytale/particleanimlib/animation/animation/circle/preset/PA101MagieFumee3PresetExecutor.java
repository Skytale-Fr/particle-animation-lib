package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA101MagieFumee3PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA101MagieFumee3PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        //Particules de fumées
        CircleBuilder fumeeSubAnimExplosion = new CircleBuilder();
        fumeeSubAnimExplosion.setPosition(circleBuilder.getPosition());
        fumeeSubAnimExplosion.setJavaPlugin(circleBuilder.getJavaPlugin());
        fumeeSubAnimExplosion.setRadius(0.1);
        fumeeSubAnimExplosion.setNbPoints(1);
        fumeeSubAnimExplosion.setShowPeriod(20);
        fumeeSubAnimExplosion.setTicksDuration(20);
        fumeeSubAnimExplosion.setPointDefinition(new ParticleTemplate(Particle.EXPLOSION_NORMAL, 200, 0.01f, new Vector(2, 2, 2)));

        CircleBuilder fumeeSubAnimDragonBreath = new CircleBuilder();
        fumeeSubAnimDragonBreath.setPosition(circleBuilder.getPosition());
        fumeeSubAnimDragonBreath.setJavaPlugin(circleBuilder.getJavaPlugin());
        fumeeSubAnimDragonBreath.setRadius(1);
        fumeeSubAnimDragonBreath.setNbPoints(3);
        fumeeSubAnimDragonBreath.setShowPeriod(20);
        fumeeSubAnimDragonBreath.setTicksDuration(20);
        fumeeSubAnimDragonBreath.setPointDefinition(new ParticleTemplate(Particle.DRAGON_BREATH, 100, 0.2f, new Vector(2, 2, 2)));

        CircleBuilder fumee = new CircleBuilder();
        fumee.setPosition(circleBuilder.getPosition());
        fumee.setJavaPlugin(plugin);
        fumee.setNbPoints(1, true);
        fumee.setRadius(1);
        fumee.setTicksDuration(1);
        fumee.setShowPeriod(0);
        fumee.setPointDefinition(new ParticleTemplate(Particle.EXPLOSION_NORMAL));
        fumee.setAnimationEndedCallback(animationEnding -> {
            fumeeSubAnimExplosion.getAnimation().show();
            fumeeSubAnimDragonBreath.getAnimation().show();
        });

        //Particules de magie
        CircleBuilder magie = new CircleBuilder();
        magie.setPosition(circleBuilder.getPosition());
        magie.setJavaPlugin(circleBuilder.getJavaPlugin());
        magie.setRadius(0.1);
        magie.setNbPoints(3);
        magie.setShowPeriod(10);
        magie.setTicksDuration(20+10);
        magie.setPointDefinition(new ParticleTemplate(Particle.SPELL_WITCH, 100, 0.01f, new Vector(2, 2, 2)));
        /*
        COMPOSTER
        CRIT_MAGIC
        END_ROD
        FIREWORKS_SPARK
        GLOW
        SPELL_WITCH -
        WAX_ON
         */
        circleBuilder.setNbPoints(1, true);
        circleBuilder.setRadius(1);
        circleBuilder.setTicksDuration(1);
        circleBuilder.setShowPeriod(0);
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.EXPLOSION_NORMAL));
        circleBuilder.setAnimationEndedCallback(animationEnding -> {
            fumee.getAnimation().show();
            magie.getAnimation().show();
        });
    }
}