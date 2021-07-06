package fr.skytale.particleanimlib.testing;

import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.attributes.var.Constant;
import fr.skytale.particleanimlib.testing.attributes.AnimationLibPlayerData;
import fr.skytale.particleanimlib.testing.command.AnimationLibCommand;
import fr.skytale.particleanimlib.testing.command.AnimationLibTabCompleter;
import fr.skytale.particleanimlib.testing.listener.RightClickAirEventListener;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ParticleAnimLibTest {

    public static String DEFAULT_ANIMATION_TYPE = "cuboid";
    public static String DEFAULT_TRAIL_TYPE = "circle";
    public static boolean DEFAULT_SHOW_ON_CLICK = true;

    private static ParticleAnimLibTest instance;

    private final JavaPlugin plugin;

    private final Map<UUID, AnimationLibPlayerData> playersData;

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

    private ParticleAnimLibTest(JavaPlugin plugin, PluginCommand command) {
        this.plugin = plugin;
        this.playersData = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(new RightClickAirEventListener(this), plugin);
        if (command != null) {
            command.setExecutor(new AnimationLibCommand(this));
            command.setTabCompleter(new AnimationLibTabCompleter(this));
        }
    }

    public void setShowAnimOnClick(Player player, Boolean showAnimationOnClick) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        playerData.setShowAnimationOnClick(showAnimationOnClick);
        playersData.put(player.getUniqueId(), playerData);
    }

    public Set<String> getAnimationNames() {
        return AnimationManager.getInstance().getAnimationNames();
    }

    public void setAnimationType(Player player, String animationSampleName) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        playerData.setAnimationType(animationSampleName);
        playersData.put(player.getUniqueId(), playerData);
    }

    public void buildAndShowAnimation(Player player) {
        buildAndShowAnimation(player, APosition.fromEntity(player, new Constant<>(player.getEyeLocation().toVector().subtract(player.getLocation().toVector()))));
    }

    public void buildAndShowAnimation(Player player, APosition position) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        if (!playerData.isShowAnimationOnClick()) return;
        AnimationManager.getInstance().initBuilder(player, position, plugin, playerData.getAnimationType()).getAnimation().show();
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
        AnimationManager.getInstance().getChainedBuilders(player, plugin).getAnimation().show();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }


    public Set<String> getTrailNames() {
        return TrailManager.getInstance().getTrailNames();
    }

    public boolean toggleTrail(Player player) {
        return TrailManager.getInstance().toggleTrail(player, getPlayerData(player).getTrailSampleName());
    }

    public void setTrailType(Player player, String trailAnimationSampleName) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        TrailManager.getInstance().disableTrail(player, playerData.getTrailSampleName());
        playerData.setTrailSampleName(trailAnimationSampleName);
        playersData.put(player.getUniqueId(), playerData);
        TrailManager.getInstance().enableTrail(player, trailAnimationSampleName);
    }
}
