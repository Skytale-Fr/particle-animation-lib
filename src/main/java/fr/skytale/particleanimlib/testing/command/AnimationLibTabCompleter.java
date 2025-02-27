package fr.skytale.particleanimlib.testing.command;

import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnimationLibTabCompleter implements TabCompleter {

    public static final List<String> FIRST_PARAMS = Arrays.asList("toggle", "showall", "type", "trail", "stopall");

    ParticleAnimLibTest particleAnimLibTest;

    public AnimationLibTabCompleter(ParticleAnimLibTest particleAnimLibTest) {
        this.particleAnimLibTest = particleAnimLibTest;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 0) {
            return null;
        }
        if (args.length == 1) {
            return FIRST_PARAMS.stream()
                    .filter(param -> param.startsWith(args[0]))
                    .collect(Collectors.toList());
        }
        if (args.length == 2 && args[0].equals("type")) {
            return this.particleAnimLibTest.getAnimationNames().stream()
                    .filter(typeStr -> typeStr.startsWith(args[1]))
                    .collect(Collectors.toList());
        }
        if (args.length == 2 && args[0].equals("trail")) {
            return this.particleAnimLibTest.getTrailNames().stream()
                    .filter(typeStr -> typeStr.startsWith(args[1]))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
