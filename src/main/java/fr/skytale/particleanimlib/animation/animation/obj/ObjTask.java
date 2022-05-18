package fr.skytale.particleanimlib.animation.animation.obj;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.task.ARotatingAnimationTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjTask extends ARotatingAnimationTask<Obj> {
    private final ParticleTemplate particleTemplate;
    public Set<Vector> currentImagePixels;

    public ObjTask(Obj image) {
        super(image);

        this.currentImagePixels = animation.getObjPixels().stream().map(Vector::clone).collect(Collectors.toSet());
        this.particleTemplate = this.animation.getMainParticle();
        startTask();
    }

    @Override
    public void showRotated(boolean changeRotation, Location iterationBaseLocation) {
        if (changeRotation) {
            currentImagePixels = animation.getObjPixels().stream()
                    .map(v -> this.rotation.rotateVector(v))
                    .collect(Collectors.toSet());
        }
        final Collection<? extends Player> viewers = animation.getViewers().getPlayers(iterationBaseLocation);

        currentImagePixels.forEach(vector -> {
            final Location loc = iterationBaseLocation.clone().add(vector);
            ParticleBuilder particleBuilder = particleTemplate.getParticleBuilder(loc);
            particleBuilder.display(viewers);
            particleBuilder.display();
        });
    }

    public Set<Vector> getCurrentImagePixels() {
        return currentImagePixels;
    }

}
