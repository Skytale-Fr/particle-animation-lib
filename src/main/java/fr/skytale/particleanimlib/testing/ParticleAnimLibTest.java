package fr.skytale.particleanimlib.testing;

import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.OnEntityAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.RelativeToEntityAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.testing.attribute.AnimationLibPlayerData;
import fr.skytale.particleanimlib.testing.command.AnimationLibCommand;
import fr.skytale.particleanimlib.testing.command.AnimationLibTabCompleter;
import fr.skytale.particleanimlib.testing.listener.RightClickAirEventListener;
import fr.skytale.particleanimlib.testing.manager.AnimationSampleManager;
import fr.skytale.particleanimlib.testing.manager.TrailSampleManager;
import fr.skytale.particleanimlib.trail.TrailTask;
import fr.skytale.particleanimlib.trail.attribute.TrailPreset;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ParticleAnimLibTest {

    private static ParticleAnimLibTest instance;

    private final JavaPlugin plugin;

    private final Map<UUID, AnimationLibPlayerData> playersData;

    private static List<AAnimationTask> animationTasks;

    private ParticleAnimLibTest(JavaPlugin plugin, PluginCommand command) {
        this.plugin = plugin;
        this.playersData = new HashMap<>();
        this.animationTasks = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(new RightClickAirEventListener(this), plugin);
        if (command != null) {
            command.setExecutor(new AnimationLibCommand(this));
            command.setTabCompleter(new AnimationLibTabCompleter(this));
        }
    }

    public static void enable(JavaPlugin plugin, PluginCommand command) {
        if (instance == null) {
            instance = new ParticleAnimLibTest(plugin, command);
        }
    }

    public static ParticleAnimLibTest getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Could not call ParticleAnimLibTest.getInstance() before the ParticleAnimLibTest.enable(JavaPlugin plugin, PluginCommand command) method has been called.");
        }
        return instance;
    }

    public void setShowAnimOnClick(Player player, Boolean showAnimationOnClick) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        playerData.setShowAnimationOnClick(showAnimationOnClick);
        playersData.put(player.getUniqueId(), playerData);
    }

    public Set<String> getAnimationNames() {
        return AnimationSampleManager.getInstance().getAnimationNames();
    }

    public void setAnimationType(Player player, String animationSampleName) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        playerData.setAnimationType(animationSampleName);
    }

    public void buildAndShowAnimation(Player player) {
        buildAndShowAnimation(
                player,
                new OnEntityAnimationPosition(new Constant<>(player))
        );
    }

    public void buildAndShowAnimation(Player player, AAnimationPosition position) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        AnimationSampleManager.getInstance().initBuilder(player, position, plugin, playerData.getAnimationType()).getAnimation().show();
    }

    public AnimationLibPlayerData getPlayerData(Player player) {
        AnimationLibPlayerData playerData = playersData.get(player.getUniqueId());
        if (playerData == null) {
            playerData = new AnimationLibPlayerData();
            playersData.put(player.getUniqueId(), playerData);
        }
        return playerData;
    }

    public void showAllAnimations(Player player) {
        AnimationSampleManager.getInstance().showChainedBuilders(player, plugin);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public Set<String> getTrailNames() {
        return TrailSampleManager.getInstance().getTrailNames();
    }

    public void toggleTrail(Player player) {
        final AnimationLibPlayerData playerData = getPlayerData(player);
        final String trailSampleName = playerData.getTrailSampleName();
        TrailTask trailTask = TrailSampleManager.getInstance().getTrailTask(trailSampleName, player);
        if (trailTask.containsPlayer(player.getUniqueId())) {
            trailTask.removePlayer(player.getUniqueId());
            player.sendMessage("Deactivating trail " + trailSampleName);
        } else {
            trailTask.addPlayer(player.getUniqueId());
            player.sendMessage("Activating trail " + trailSampleName);
        }
    }

    public void setTrailType(Player player, String trailAnimationSampleName) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        //disable previous trail
        final TrailTask previousTrailTask = TrailSampleManager.getInstance().getTrailTask(playerData.getTrailSampleName(), player);
        if (previousTrailTask.containsPlayer(player.getUniqueId())) {
            previousTrailTask.removePlayer(player.getUniqueId());
            player.sendMessage("Deactivating trail " + playerData.getTrailSampleName());
        }
        //activating new trail
        playerData.setTrailSampleName(trailAnimationSampleName);
        final TrailTask newTrailTask = TrailSampleManager.getInstance().getTrailTask(playerData.getTrailSampleName(), player);
        newTrailTask.addPlayer(player.getUniqueId());
        player.sendMessage("Activating trail " + playerData.getTrailSampleName());
    }

    public static void addAnimationTask(AAnimationTask animationTask) {
        if(animationTasks != null)
            animationTasks.add(animationTask);
    }

    public void clearAnimationTasks() {
        animationTasks.clear();
    }

    public List<AAnimationTask> getAnimationTasks() {
        return animationTasks;
    }
}
