package fr.skytale.particleanimlib.animation.parent.animation.subanim;

import org.bukkit.util.Vector;

public interface IPlaneSubAnimation extends ISubAnimation {
    Vector getU();

    void setU(Vector directorU);

    Vector getV();

    void setV(Vector directorV);


}
