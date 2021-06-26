package fr.skytale.particleanimlib.animation.pyramid;

import fr.skytale.particleanimlib.animation.explodingsphere.ExplodingSphere;
import fr.skytale.particleanimlib.attributes.CustomVector;
import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.parent.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PyramidTask extends AAnimationTask<Pyramid> {

    private Vector fromCenterToApex;
    private List<Vector> fromCenterToBaseApexList;
    private double distanceToAnyBaseApex;
    private int nbBaseApex;
    private double distanceBetweenParticles;

    public PyramidTask(Pyramid pyramid) {
        super(pyramid);

        this.fromCenterToApex = animation.getFromCenterToApex().clone();
        this.distanceToAnyBaseApex = animation.getDistanceToAnyBaseApex();
        this.nbBaseApex = animation.getNbBaseApex();
        this.distanceBetweenParticles = animation.getDistanceBetweenParticles();

        /*
        Compute a vector to one of the base's apex:
         - its normal vector : fromCenterToApex
         - a point of the plane : iterationBaseLocation
         */
        Vector normalVector = fromCenterToApex.clone().normalize();

        Vector radiusVector = computeRadiusVector(normalVector,distanceToAnyBaseApex);

        /*
        Compute each point of the pyramid's base with :
        - the circle
        - the number of points : nbBaseApex
        */

        double theta = 2*Math.PI/nbBaseApex;

        fromCenterToBaseApexList = new ArrayList<>();
        fromCenterToBaseApexList.add(radiusVector);

        for(int i=1 ; i<nbBaseApex ; i++){
            CustomVector customVector = new CustomVector(radiusVector);
            customVector.rotateAroundAxis(normalVector,theta*i);
            fromCenterToBaseApexList.add(customVector);
        }

        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    @Override
    public void show(Location iterationBaseLocation) {

        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        // Compute the pyramid apex
        Location apex = iterationBaseLocation.clone().add(fromCenterToApex);

        // Compute each base apex
        List<Location> baseApexList = fromCenterToBaseApexList.stream()
                .map(fromCenterToBaseApex -> iterationBaseLocation.clone().add(fromCenterToBaseApex))
                .collect(Collectors.toList());

        List<Location> pointList = new ArrayList<>(baseApexList);
        pointList.add(apex);

        //Link each base apex inbetween

        drawLines(baseApexList, apex);
    }

    private void drawLines(List<Location> baseApexList, Location apex) {

        drawLine(baseApexList.get(0),baseApexList.get(baseApexList.size() -1), distanceBetweenParticles);

        for (int i = 0; i < baseApexList.size() - 1; i++) {
            drawLine(baseApexList.get(i), baseApexList.get(i+1), distanceBetweenParticles);
        }

        //Link each base apex with the main apex.
        baseApexList.forEach(baseApex -> drawLine(baseApex, apex, distanceBetweenParticles));
    }
}
