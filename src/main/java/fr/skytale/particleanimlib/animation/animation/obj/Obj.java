package fr.skytale.particleanimlib.animation.animation.obj;


import com.mokiat.data.front.parser.*;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AnimationTaskUtils;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.geometry.euclidean.twod.hull.ConvexHull2D;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class Obj extends AAnimation {
    /******** Attributes ********/

    private String objFileName;
    private double distanceBetweenParticles;
    private double scale;
    private double minAngleBetweenFaces;
    private double minFaceArea;

    /******** Constructor ********/

    public Obj() {
    }

    /******** public Methods ********/

    @Override
    public ObjTask show() {
        return new ObjTask(this);
    }

    @Override
    public Obj clone() {
        Obj obj = (Obj) super.clone();
        return obj;
    }

    /******** Getters & Setters ********/

    public String getObjFileName() {
        return objFileName;
    }

    public void setObjFileName(String objFileName) {
        this.objFileName = objFileName;
    }

    public double getDistanceBetweenParticles() {
        return distanceBetweenParticles;
    }

    public void setDistanceBetweenParticles(double distanceBetweenParticles) {
        this.distanceBetweenParticles = distanceBetweenParticles;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getMinAngleBetweenFaces() {
        return minAngleBetweenFaces;
    }

    public void setMinAngleBetweenFaces(double minAngleBetweenFaces) {
        this.minAngleBetweenFaces = minAngleBetweenFaces;
    }

    public double getMinFaceArea() {
        return minFaceArea;
    }

    public void setMinFaceArea(double minFaceArea) {
        this.minFaceArea = minFaceArea;
    }
}
