package fr.skytale.particleanimlib.animation.parent.animation.subanim;

import org.bukkit.util.Vector;

public interface IPlaneSubAnimation extends ISubAnimation {
    Vector getU();

    Vector getV();

    void setU(Vector directorU);

    void setV(Vector directorV);


}
