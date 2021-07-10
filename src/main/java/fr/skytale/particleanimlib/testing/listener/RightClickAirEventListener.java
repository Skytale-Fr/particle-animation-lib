package fr.skytale.particleanimlib.testing.listener;

import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class RightClickAirEventListener implements Listener {

    private ParticleAnimLibTest particleAnimLibTest;

    private boolean antiSpam;

    public RightClickAirEventListener(ParticleAnimLibTest particleAnimLibTest) {
        this.particleAnimLibTest = particleAnimLibTest;
        this.antiSpam = false;
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        if (antiSpam) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            particleAnimLibTest.buildAndShowAnimation(event.getPlayer());
            event.setCancelled(true);
            activateAntiSpam();
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            particleAnimLibTest.buildAndShowAnimation(
                    event.getPlayer(),
                    APosition.fromLocation(
                            new Constant<>(Objects.requireNonNull(event.getClickedBlock()).getLocation())
                    ));
            event.setCancelled(true);
            activateAntiSpam();
        }
    }

    public void activateAntiSpam() {
        antiSpam = true;
        Bukkit.getScheduler().runTaskLater(ParticleAnimLibTest.getInstance().getPlugin(), () -> antiSpam = false, 5);
    }
}
