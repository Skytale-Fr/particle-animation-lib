package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleCirclePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SimpleCirclePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setNbPoints(20, true);
        circleBuilder.setRadius(4);
        circleBuilder.setTicksDuration(100);
        circleBuilder.setShowPeriod(new Constant<>(1));
    }
}
