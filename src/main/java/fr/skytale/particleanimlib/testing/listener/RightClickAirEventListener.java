package fr.skytale.particleanimlib.testing.listener;

import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class RightClickAirEventListener implements Listener {

    private final ParticleAnimLibTest particleAnimLibTest;

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
        if (!particleAnimLibTest.getPlayerData(event.getPlayer()).isShowAnimationOnClick()) {
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            particleAnimLibTest.buildAndShowAnimation(event.getPlayer());
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            final Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
            location.add(new Vector(0.5, 1, 0.5));

            particleAnimLibTest.buildAndShowAnimation(
                    event.getPlayer(),
                    new LocatedAnimationPosition(
                            new Constant<>(location)
                    ));
        }
        event.setCancelled(true);
        activateAntiSpam();
    }

    public void activateAntiSpam() {
        antiSpam = true;
        Bukkit.getScheduler().runTaskLater(ParticleAnimLibTest.getInstance().getPlugin(), () -> antiSpam = false, 5);
    }
}
