package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface IPAnimSample {

    AAnimationBuilder<?> getInitializedBuilder(APosition position, JavaPlugin plugin);

    String getName();
}
