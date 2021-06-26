package fr.skytale.particleanimlib.testing.command;

import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import fr.skytale.particleanimlib.testing.attributes.AnimationType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class AnimationLibCommand implements CommandExecutor {

    private final ParticleAnimLibTest particleAnimLibTest;

    public AnimationLibCommand(ParticleAnimLibTest particleAnimLibTest) {
        this.particleAnimLibTest = particleAnimLibTest;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Usage from command block / console is not allowed");
            return false;
        }
        Player player = (Player) sender;
        try {
            if (args.length == 0) {
                this.particleAnimLibTest.buildAndShowAnimation(player);
                return true;
            }
            if (args.length == 1) {
                if (args[0].equals("event")) {
                    boolean isShowAnimationOnClick = this.particleAnimLibTest.getPlayerData(player).isShowAnimationOnClick();
                    this.particleAnimLibTest.setShowAnimOnClick(player, !isShowAnimationOnClick);
                    if (isShowAnimationOnClick) {
                        player.sendMessage("Event disabled");
                    } else {
                        player.sendMessage("Event enabled. Right click in air.");
                    }
                    return true;
                } else if (args[0].equals("showall")) {
                    this.particleAnimLibTest.showAllAnimations(player);
                    player.sendMessage("Showing all animation samples in a row.");
                }
            }
            if (args.length == 2 && args[0].equals("type")) {
                try {
                    AnimationType type = AnimationType.valueOf(args[1].toUpperCase(Locale.ROOT));
                    this.particleAnimLibTest.setAnimationType(player, type);
                    player.sendMessage("Testing particle animation \"" + type.name().toLowerCase(Locale.ROOT) + "\".");
                    return true;
                } catch (IllegalArgumentException e) {
                    player.sendMessage("This animation type does not exist. Available types : " +
                            Arrays.stream(AnimationType.values())
                                    .map(type -> type.name().toLowerCase(Locale.ROOT))
                                    .collect(Collectors.joining(", ")));
                }
            }
            player.sendMessage("Usage: /" + label + " [<type | toggleEvent> [type]]");
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
