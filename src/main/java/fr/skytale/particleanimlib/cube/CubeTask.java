package fr.skytale.particleanimlib.cube;

import fr.skytale.particleanimlib.parent.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CubeTask extends AAnimationTask<Cube> {

    private double sideLength;
    private double step;
    private int taskId;

    //Evolving variables
    int iterationCount;
    double halfSide;
    Location upperFrontRight, upperFrontLeft, upperBackRight, upperBackLeft, lowerFrontRight, lowerFrontLeft, lowerBackRight, lowerBackLeft;

    public CubeTask(Cube cube) {
        super(cube);
        this.sideLength = cube.getSideLength();
        this.step = cube.getStep();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    private void init() {
        iterationCount = 0;
        halfSide = sideLength / 2;

        Location center = animation.getBaseLocation();

        upperFrontRight = center.clone().add(halfSide, halfSide, halfSide);
        upperFrontLeft = center.clone().add(-halfSide, halfSide, halfSide);
        upperBackLeft = center.clone().add(-halfSide, halfSide, -halfSide);
        upperBackRight = center.clone().add(halfSide, halfSide, -halfSide);
        lowerFrontRight = center.clone().add(halfSide, -halfSide, halfSide);
        lowerFrontLeft = center.clone().add(-halfSide, -halfSide, halfSide);
        lowerBackLeft = center.clone().add(-halfSide, -halfSide, -halfSide);
        lowerBackRight = center.clone().add(halfSide, -halfSide, -halfSide);
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

        drawLine(upperFrontRight, upperFrontLeft);
        drawLine(upperFrontRight, upperBackRight);
        drawLine(upperFrontRight, lowerFrontRight);
        drawLine(lowerFrontRight, lowerFrontLeft);
        drawLine(lowerFrontRight, lowerBackRight);
        drawLine(lowerFrontLeft, upperFrontLeft);
        drawLine(lowerFrontLeft, lowerBackLeft);
        drawLine(upperBackLeft, upperFrontLeft);
        drawLine(upperBackLeft, lowerBackLeft);
        drawLine(upperBackLeft, upperBackRight);
        drawLine(lowerBackRight, lowerBackLeft);
        drawLine(lowerBackRight, upperBackRight);

        iterationCount++;
    }
}
