package fr.skytale.particleanimlib.testing.command;

import fr.skytale.particleanimlib.animation.animation.spiral.SpiralBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

            else if (args.length == 1 && args[0].equals("event")) {
                boolean isShowAnimationOnClick = this.particleAnimLibTest.getPlayerData(player).isShowAnimationOnClick();
                this.particleAnimLibTest.setShowAnimOnClick(player, !isShowAnimationOnClick);
                if (isShowAnimationOnClick) {
                    player.sendMessage("Event disabled");
                } else {
                    player.sendMessage("Event enabled. Right click in air.");
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
                    boolean activated = this.particleAnimLibTest.toggleTrail(player);
                    player.sendMessage((activated ? "Testing" : "Stopping the test of") + " the trail \"" + this.particleAnimLibTest.getPlayerData(player).getTrailSampleName() + "\".");
                    return true;
                }

                // ---- TRAIL TYPE ----

                else if (args.length == 2) {
                    String inputType = args[1].trim().toLowerCase(Locale.ROOT);
                    Set<String> trailNames = this.particleAnimLibTest.getTrailNames();
                    if (trailNames.contains(inputType)) {
                        this.particleAnimLibTest.setTrailType(player, inputType);
                        player.sendMessage("Testing the trail \"" + inputType + "\".");
                    } else {
                        player.sendMessage("This trail type does not exist. Available types : " +
                                String.join(", ", trailNames));
                    }
                    return true;
                }
            }

            // ---- TARGET ----

            else if(args.length == 2 && args[0].equals("target")){
                Player targetedPlayer = Bukkit.getPlayer(args[1]);
                if( targetedPlayer == null ){
                    player.sendMessage("The player "+args[1]+" does not exist.");
                    return true;
                }
                else if(targetedPlayer.equals(player)){
                    player.sendMessage("One cannot target itself.");
                    return true;
                }
                else if(!targetedPlayer.getWorld().equals(player.getWorld())){
                    player.sendMessage("Targeted player must be in the same world.");
                    return true;
                }

                target(player, targetedPlayer);
                return true;
            }

            player.sendMessage("Usage: /" + label + " [<type | showall | event | trail> [type/player]]");
            return true;
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private void target(Player player, Player targetedPlayer) {
        SpiralBuilder  spiralBuilder = new SpiralBuilder();
        spiralBuilder.setPosition(APosition.fromLocation(player.getLocation().clone()));
        spiralBuilder.setDirection(AnimationDirection.fromTargetEntity(targetedPlayer,0.5));
        spiralBuilder.applyPreset(AnimationPreset.SPIRAL_CASTING_SPELL,particleAnimLibTest.getPlugin());
        spiralBuilder.getAnimation().show();
    }
}
