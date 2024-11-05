package fr.skytale.particleanimlib.testing.command;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AnimationLibCommand implements CommandExecutor {

    private final ParticleAnimLibTest particleAnimLibTest;

    public AnimationLibCommand(ParticleAnimLibTest particleAnimLibTest) {
        this.particleAnimLibTest = particleAnimLibTest;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Usage from command block / console is not allowed");
            return true;
        }
        Player player = (Player) sender;
        try {

            // ---- SHOW ANIMATION ----

            if (args.length == 0) {
                this.particleAnimLibTest.buildAndShowAnimation(player);
                return true;
            }

            // ---- EVENT TOGGLE ----

            else if (args.length == 1 && args[0].equals("toggle")) {
                boolean isShowAnimationOnClick = this.particleAnimLibTest.getPlayerData(player).isShowAnimationOnClick();
                this.particleAnimLibTest.setShowAnimOnClick(player, !isShowAnimationOnClick);
                if (isShowAnimationOnClick) {
                    player.sendMessage("Display animation on click disabled");
                } else {
                    player.sendMessage("Display animation on click enabled. Simply left click in the air or on a block.");
                }
                return true;
            }

            // ---- SHOW ALL ----

            else if (args.length == 1 && args[0].equals("showall")) {
                player.sendMessage("Showing all animation samples in a row.");
                this.particleAnimLibTest.showAllAnimations(player);
                return true;
            }

            // ---- ANIMATION TYPE ----

            else if (args.length == 2 && args[0].equals("type")) {
                String inputType = args[1].trim().toLowerCase(Locale.ROOT);
                Set<String> animationNames = this.particleAnimLibTest.getAnimationNames();
                if (animationNames.contains(inputType)) {
                    this.particleAnimLibTest.setAnimationType(player, inputType);
                    player.sendMessage("Testing particle animation \"" + inputType + "\".");
                    return true;
                } else {
                    player.sendMessage("This animation type does not exist. Available types : " +
                            String.join(", ", animationNames));
                    return false;
                }
            }

            // ---- TRAIL ----

            else if (args[0].equals("trail")) {

                // ---- TRAIL TOGGLE ----

                if (args.length == 1) {
                    this.particleAnimLibTest.toggleTrail(player);
                    return true;
                }

                // ---- TRAIL TYPE ----

                else if (args.length == 2) {
                    this.particleAnimLibTest.setTrailType(player, args[1]);
                    return true;
                }
            }

            // ---- STOP ALL ----
            else if (args.length == 1 && args[0].equals("stopall")) {

                List<AAnimationTask> animationTasks = particleAnimLibTest.getAnimationTasks();
                for (AAnimationTask animationTask : animationTasks) {
                    animationTask.stopAnimation(false);
                }
                particleAnimLibTest.clearAnimationTasks();

                return true;
            }

            player.sendMessage("Usage: /" + label + " [<type | showall | toggle | trail> [type/player]] | stopall");
            return true;
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
