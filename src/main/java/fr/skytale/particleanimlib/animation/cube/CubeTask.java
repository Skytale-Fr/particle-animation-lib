package fr.skytale.particleanimlib.animation.cube;

import fr.skytale.particleanimlib.parent.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CubeTask extends AAnimationTask<Cube> {

    private double sideLength;
    private double step;
    private int taskId;

    //Evolving variables
    double halfSide;
    Location upperFrontRight, upperFrontLeft, upperBackRight, upperBackLeft, lowerFrontRight, lowerFrontLeft, lowerBackRight, lowerBackLeft;

    public CubeTask(Cube cube) {
        super(cube);
        this.sideLength = animation.getSideLength();
        this.step = animation.getStep();
        halfSide = sideLength / 2;
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    private void initVertices(Location center) {
        upperFrontRight = center.clone().add(halfSide, halfSide, halfSide);
        upperFrontLeft = center.clone().add(-halfSide, halfSide, halfSide);
        upperBackLeft = center.clone().add(-halfSide, halfSide, -halfSide);
        upperBackRight = center.clone().add(halfSide, halfSide, -halfSide);
        lowerFrontRight = center.clone().add(halfSide, -halfSide, halfSide);
        lowerFrontLeft = center.clone().add(-halfSide, -halfSide, halfSide);
        lowerBackLeft = center.clone().add(-halfSide, -halfSide, -halfSide);
        lowerBackRight = center.clone().add(halfSide, -halfSide, -halfSide);
    }

    @Override
    public void show(Location iterationBaseLocation) {
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        initVertices(iterationBaseLocation);

        drawLine(upperFrontRight, upperFrontLeft, step);
        drawLine(upperFrontRight, upperBackRight, step);
        drawLine(upperFrontRight, lowerFrontRight, step);
        drawLine(lowerFrontRight, lowerFrontLeft, step);
        drawLine(lowerFrontRight, lowerBackRight, step);
        drawLine(lowerFrontLeft, upperFrontLeft, step);
        drawLine(lowerFrontLeft, lowerBackLeft, step);
        drawLine(upperBackLeft, upperFrontLeft, step);
        drawLine(upperBackLeft, lowerBackLeft, step);
        drawLine(upperBackLeft, upperBackRight, step);
        drawLine(lowerBackRight, lowerBackLeft, step);
        drawLine(lowerBackRight, upperBackRight, step);
    }

}
