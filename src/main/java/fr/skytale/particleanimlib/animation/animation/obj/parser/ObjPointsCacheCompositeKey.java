package fr.skytale.particleanimlib.animation.animation.obj.parser;

import fr.skytale.particleanimlib.animation.animation.obj.Obj;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ObjPointsCacheCompositeKey {
    // composite key attributes
    private final String objFileName;
    private final double distanceBetweenParticles;
    private final double scale;
    private final double minAngleBetweenFaces;
    private final double minFaceArea;

    // not part of the key
    private final JavaPlugin javaPlugin;

    public ObjPointsCacheCompositeKey(Obj obj) {
        this.objFileName = obj.getObjFileName();
        this.distanceBetweenParticles = obj.getDistanceBetweenParticles();
        this.scale = obj.getScale();
        this.minAngleBetweenFaces = obj.getMinAngleBetweenFaces();
        this.minFaceArea = obj.getMinFaceArea();
        this.javaPlugin = obj.getPlugin();
    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    public String getObjFileName() {
        return objFileName;
    }

    public double getDistanceBetweenParticles() {
        return distanceBetweenParticles;
    }

    public double getScale() {
        return scale;
    }

    public double getMinAngleBetweenFaces() {
        return minAngleBetweenFaces;
    }

    public double getMinFaceArea() {
        return minFaceArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjPointsCacheCompositeKey that = (ObjPointsCacheCompositeKey) o;
        return Double.compare(that.distanceBetweenParticles, distanceBetweenParticles) == 0 &&
               Double.compare(that.scale, scale) == 0 &&
               Double.compare(that.minAngleBetweenFaces, minAngleBetweenFaces) == 0 &&
               Double.compare(that.minFaceArea, minFaceArea) == 0 &&
               Objects.equals(objFileName, that.objFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objFileName, distanceBetweenParticles, scale, minAngleBetweenFaces, minFaceArea);
    }
}
