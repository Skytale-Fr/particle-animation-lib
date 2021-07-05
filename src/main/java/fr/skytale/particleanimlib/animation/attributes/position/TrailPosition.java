package fr.skytale.particleanimlib.animation.attributes.position;

import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.bukkit.util.Vector;

public class TrailPosition extends APosition {

    public TrailPosition(IVariable<Vector> relativeLocation) {
        this.type = Type.TRAIL;
        this.relativeLocation = relativeLocation;
    }
}
