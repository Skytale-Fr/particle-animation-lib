package fr.skytale.particleanimlib.wave;


import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Wave extends ARoundAnimation {
    private double maxRadius;
    private double step;
    private AAnimation circleAnim;

    public Wave() {
    }

    @Override
    public void show() {
        Location waveCenter;
        if (isFixedLocation())
            waveCenter = location.clone();
        else
            waveCenter = movingEntity.getLocation().clone().add(relativeLocation);

        new BukkitRunnable() {

            double currentRadius = radius;

            @Override
            public void run() {

                if (currentRadius >= maxRadius)
                    this.cancel();

                if (currentRadius % 2 != 0) {  //On affiche que les cercles de rayon impairs
                    ARoundAnimation circle = (ARoundAnimation) circleAnim;

                    //On change le rayon
                    circle.setRadius(currentRadius);

                    //On calcule et on change la hauteur
                    double y = waveCenter.getY() + (2 * Math.exp(-0.1* (39/( maxRadius - radius ) * currentRadius)) * Math.sin(currentRadius)) + 1;
                    circle.setLocation(new Location(waveCenter.getWorld(),circle.getLocation().getX(),y,circle.getLocation().getZ()));

                    circle.show();

                }

                currentRadius += step;

            }

        }.runTaskTimer(plugin, 0L, 1L);
    }

    /***********GETTERS & SETTERS***********/
    public double getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public AAnimation getCircleAnim() {
        return circleAnim;
    }

    public void setCircleAnim(AAnimation circleAnim) {
        this.circleAnim = circleAnim;
    }
}
