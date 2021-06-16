package fr.skytale.particleanimlib.pyramid;

import fr.skytale.particleanimlib.parent.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PyramidTask extends AAnimationTask<Pyramid> {

    private Location apex;
    private Location baseA;
    private Location baseB;
    private Location baseC;
    private double step;
    private int taskId;

    //Evolving variables
    int iterationCount;

    public PyramidTask(Pyramid pyramid) {
        super(pyramid);
        this.apex = pyramid.getApexPoint();
        this.baseA = pyramid.getBasePointA();
        this.baseB = pyramid.getBasePointB();
        this.baseC = pyramid.getBasePointC();
        this.step = pyramid.getStep();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    private void init() {
        iterationCount = 0;
    }

    private void drawLine(Location point1, Location point2) {
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(step);

        for (double length = 0; length < distance; p1.add(vector)) {

            Location particleLocation = new Location(location.getWorld(), p1.getX(), p1.getY(), p1.getZ());
            mainParticle.getParticleBuilder(particleLocation).display();
            length += step;
        }
    }

    @Override
    public void run() {
        if (iterationCount >= ticksDuration) {
            Bukkit.getScheduler().cancelTask(taskId);
            if (animation.getCallback() != null) {
                animation.getCallback().run(animation);
            }
            return;
        }

        //We only show at the specified frequency
        if (showFrequency != 0 && (iterationCount % showFrequency != 0)) {
            iterationCount++;
            return;
        }

        drawLine(apex, baseA);
        drawLine(apex, baseB);
        drawLine(apex, baseC);
        drawLine(baseA, baseB);
        drawLine(baseA, baseC);
        drawLine(baseB, baseC);

        iterationCount++;
    }
}
