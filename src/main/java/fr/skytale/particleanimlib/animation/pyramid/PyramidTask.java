package fr.skytale.particleanimlib.animation.pyramid;

import fr.skytale.particleanimlib.parent.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PyramidTask extends AAnimationTask<Pyramid> {

    private Location apex;
    private Location baseA;
    private Location baseB;
    private Location baseC;
    private double step;
    private int taskId;

    public PyramidTask(Pyramid pyramid) {
        super(pyramid);
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    public void init(){
        this.apex = animation.getApexPoint();
        this.baseA = animation.getBasePointA();
        this.baseB = animation.getBasePointB();
        this.baseC = animation.getBasePointC();
        this.step = animation.getStep();

        super.init();
    }

    @Override
    public void show(Location iterationBaseLocation) {

        if (hasDurationEnded()) {
            stopAnimation(taskId);
            return;
        }

        /*if(animation.getMovingEntity()!=null){
            animation.setApexPoint(animation.getApexPoint().add(animation.getRelativeLocation()));
            animation.setApexPoint(animation.getApexPoint().add(animation.getRelativeLocation()));
        }*/

        drawLine(apex, baseA, step);
        drawLine(apex, baseB, step);
        drawLine(apex, baseC, step);
        drawLine(baseA, baseB, step);
        drawLine(baseA, baseC, step);
        drawLine(baseB, baseC, step);
    }

    /*@Override
    public void run() {
        if (hasDurationEnded()) {
stopAnimation(taskId);
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
    }*/
}
