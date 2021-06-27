package fr.skytale.particleanimlib.testing.listener;

import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightClickAirEventListener implements Listener {

    private ParticleAnimLibTest particleAnimLibTest;

    public RightClickAirEventListener(ParticleAnimLibTest particleAnimLibTest) {
        this.particleAnimLibTest = particleAnimLibTest;
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            particleAnimLibTest.buildAndShowAnimation(event.getPlayer());
        }
    }
}
