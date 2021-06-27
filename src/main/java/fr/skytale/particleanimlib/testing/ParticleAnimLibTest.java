package fr.skytale.particleanimlib.testing;

import fr.skytale.particleanimlib.testing.attributes.AnimationLibPlayerData;
import fr.skytale.particleanimlib.testing.attributes.AnimationType;
import fr.skytale.particleanimlib.testing.command.AnimationLibCommand;
import fr.skytale.particleanimlib.testing.command.AnimationLibTabCompleter;
import fr.skytale.particleanimlib.testing.listener.RightClickAirEventListener;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleAnimLibTest {

    public static AnimationType DEFAULT_ANIMATION_TYPE = AnimationType.CUBOID;

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

    public void setAnimationType(Player player, AnimationType animationType) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        playerData.setAnimationType(animationType);
        playersData.put(player.getUniqueId(), playerData);
    }

    public void buildAndShowAnimation(Player player) {
        AnimationLibPlayerData playerData = getPlayerData(player);
        if (!playerData.isShowAnimationOnClick()) return;
        BuildersInitializer.getInstance().initBuilder(player, plugin, playerData.getAnimationType()).getAnimation().show();
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
        BuildersInitializer.getInstance().getChainedBuilders(player, plugin).getAnimation().show();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
