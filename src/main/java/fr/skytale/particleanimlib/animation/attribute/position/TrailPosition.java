package fr.skytale.particleanimlib.animation.attribute.position;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.util.Vector;

public class TrailPosition extends APosition {

    public TrailPosition(IVariable<Vector> relativeLocation) {
        this.type = Type.TRAIL;
        this.relativeLocation = relativeLocation;
    }
}
