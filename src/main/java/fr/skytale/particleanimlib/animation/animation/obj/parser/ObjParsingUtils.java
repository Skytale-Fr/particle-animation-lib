package fr.skytale.particleanimlib.animation.animation.obj.parser;

import com.mokiat.data.front.parser.*;
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

public class ObjParsingUtils {

    public static final String MODELS_FOLDER = "3dmodels";

    public static List<Vector> buildObjPoints(ObjPointsCacheCompositeKey cacheKey) {
        //Load obj
        List<Vector> objPixels = new ArrayList<>();

        try (InputStream in = new FileInputStream(getObjFile(cacheKey.getJavaPlugin(), cacheKey.getObjFileName()))) {
            final OBJModel model = parseObj(in, cacheKey.getObjFileName());

            // 1. Build each face data
            Set<Segment> segments = buildSegments(model, cacheKey.getMinFaceArea());

            // 2. removes segments that are used by 2 faces that are on the same plane
            // (This removal is done because if there is no edge we should not show the segment)
            removeLowAngleEdges(segments, cacheKey.getMinAngleBetweenFaces());

            // 3. Change the scale of the object
            scale(segments, cacheKey.getScale());

            // 4. Find the center of the shape(s)
            Vector3D center = getCenter(segments);

            // 5. Move each segment in a new referential with center as origin
            changeReferential(segments, center);

            // 6. get each points
            buildPoints(objPixels, segments, cacheKey.getDistanceBetweenParticles());

        } catch (IOException e) {
            throw new IllegalArgumentException("The obj could not be read", e);
        }
        return objPixels;
    }

    private static File getObjsDirectory(JavaPlugin plugin) {
        File pluginDir = plugin.getDataFolder();
        if (!pluginDir.exists()) {
            boolean result = pluginDir.mkdir();
            if (!result) {
                throw new IllegalStateException("The plugin directory could not be created. It is probably a permission issue.");
            }
        }
        File objsDir = new File(pluginDir, MODELS_FOLDER);
        if (!objsDir.exists()) {
            boolean result = objsDir.mkdir();
            if (!result) {
                throw new IllegalStateException(
                        "The " + MODELS_FOLDER + " directory could not be created. It is probably a permission issue.");
            }
        }
        return objsDir;
    }

    private static File getObjFile(JavaPlugin plugin, String objFileName) {
        File objsDir = getObjsDirectory(plugin);

        File objFile = new File(objsDir, objFileName);
        if (!objFile.exists()) {
            throw new IllegalArgumentException(String.format("The obj %s does not exist.", objFile.getAbsolutePath()));
        }
        if (!objFile.isFile()) {
            throw new IllegalArgumentException(String.format("%s should not be a directory.", objFile.getAbsolutePath()));
        }
        return objFile;
    }

    private static void scale(Set<Segment> segments, double scale) {
        segments.forEach(segment -> {
            segment.setA(segment.getA().scalarMultiply(scale));
            segment.setB(segment.getB().scalarMultiply(scale));
        });
    }

    private static void buildPoints(List<Vector> objPixels, Set<Segment> segments, double distanceBetweenParticles) {
        segments.forEach(segment -> objPixels.addAll(
                AnimationTaskUtils.getLineVectors(
                        new Vector(
                                segment.getA().getX(),
                                segment.getA().getY(),
                                segment.getA().getZ()
                        ),
                        new Vector(
                                segment.getB().getX(),
                                segment.getB().getY(),
                                segment.getB().getZ()
                        ),
                        distanceBetweenParticles,
                        true)
        ));
    }

    private static void changeReferential(Set<Segment> segments, Vector3D center) {
        segments.forEach(segment -> {
            segment.setA(segment.getA().subtract(center));
            segment.setB(segment.getB().subtract(center));
        });
    }

    private static Vector3D getCenter(Set<Segment> segments) {
        //4.1 Recompute the vertices
        Set<Vector3D> vertices = new HashSet<>();
        segments.forEach(segment -> {
            vertices.add(segment.getA());
            vertices.add(segment.getB());
        });
        //4.2 Compute Max and Min vectors
        Double xMin = Double.MAX_VALUE;
        Double yMin = Double.MAX_VALUE;
        Double zMin = Double.MAX_VALUE;
        Double xMax = Double.MIN_VALUE;
        Double yMax = Double.MIN_VALUE;
        Double zMax = Double.MIN_VALUE;
        for (Vector3D vertex : vertices) {
            xMin = Math.min(xMin, vertex.getX());
            yMin = Math.min(yMin, vertex.getY());
            zMin = Math.min(yMin, vertex.getZ());
            xMax = Math.max(xMax, vertex.getX());
            yMax = Math.max(yMax, vertex.getY());
            zMax = Math.max(yMax, vertex.getZ());
        }
        //4.3 compute center
        Vector3D center = new Vector3D((xMin + xMax) / 2, (yMin + yMax) / 2, (zMin + zMax) / 2);
        return center;
    }

    private static void removeLowAngleEdges(Set<Segment> segments, double minAngleBetweenFaces) {
        double minCrossProductResultBetweenFacesToShowEdge = Math.asin(minAngleBetweenFaces);
        segments.removeIf(segment -> {
            if (segment.getRelatedFaces().size() == 1) return false;
            Vector3D face1Normal = segment.getRelatedFaces().get(0).planeNormal;
            for (int i = 1; i < segment.getRelatedFaces().size(); i++) {
                Vector3D face2Normal = segment.getRelatedFaces().get(i).planeNormal;
                if (face1Normal.crossProduct(face2Normal).getNorm() > minCrossProductResultBetweenFacesToShowEdge)
                    return false;
            }
            return true;
        });
    }

    @NotNull
    private static Set<Segment> buildSegments(OBJModel model, double minFaceArea) {
        Set<Segment> segments = new HashSet<>();

        //Loop over each mesh;
        model.getObjects().forEach(object -> object.getMeshes().forEach(mesh -> mesh.getFaces().forEach(face -> {
            Segment.Face faceData = new Segment.Face();
            //1.1 find the face vertices
            List<Vector3D> faceVertexes = new ArrayList<>();
            for (OBJDataReference ref : face.getReferences()) {
                OBJVertex vertex = model.getVertex(ref);
                faceVertexes.add(new Vector3D(vertex.x, vertex.y, vertex.z));
            }
            faceData.vertices = faceVertexes;

            //1.2 Find out the face plane
            Plane plane = new Plane(faceVertexes.get(0), faceVertexes.get(1), faceVertexes.get(2), 1.0e-5);
            faceData.planeNormal = plane.getNormal().normalize();

            //1.3 Check if face area is not too small
            if (compute2DPolygonArea(faceData) >= minFaceArea) {
                //1.4 Find out the face segments
                //1.4.1 Add the segment between the first and the last vertex
                Segment firstSegment = new Segment(faceData.vertices.get(0), faceData.vertices.get(
                        faceData.vertices.size() - 1), faceData);
                addSegment(segments, firstSegment);

                //1.4.2 Add each other segments
                for (int i = 1; i < faceData.vertices.size(); i++) {
                    Segment currentSegment = new Segment(faceData.vertices.get(
                            i - 1), faceData.vertices.get(i), faceData);
                    addSegment(segments, currentSegment);
                }
            }
        })));
        return segments;
    }

    /**
     * @param face The faceData (without segments yet)
     * @return the area
     */
    private static double compute2DPolygonArea(Segment.Face face) {
        if (face.vertices.size() < 3) {
            throw new IllegalArgumentException("A Mesh face has less than 3 vertices");
        }

        Vector3D firstPoint = face.vertices.get(0);
        Vector3D secondPoint = face.vertices.get(1);
        Vector3D thirdPoint = face.vertices.get(2);


        Plane plane = new Plane(firstPoint, secondPoint, thirdPoint, 0.001);
        Vector2D[] vertices2D = face.vertices.stream()
                .map(plane::toSubSpace).toArray(Vector2D[]::new);
        ConvexHull2D convexHull2D = new ConvexHull2D(vertices2D, 0.001);
        return convexHull2D.createRegion().getSize();
    }

    private static OBJModel parseObj(InputStream in, String objFileName) throws IOException {
        // Create an OBJParser and parse the resource
        final IOBJParser parser = new OBJParser();
        final OBJModel model = parser.parse(in);

        // Use the model representation to get some basic info
        Bukkit.getLogger().log(
                Level.INFO,
                "OBJ model {4} has {0} vertices, {1} normals, {2} texture coordinates, and {3} objects.",
                new Object[]{
                        model.getVertices().size(),
                        model.getNormals().size(),
                        model.getTexCoords().size(),
                        model.getObjects().size(),
                        objFileName
                }
        );
        return model;
    }

    private static void addSegment(Set<Segment> segments, Segment segment) {
        Segment sameSegment = segments.stream().filter(thisSegment -> thisSegment.equals(segment)).findAny().orElse(null);
        if (sameSegment == null) {
            segments.add(segment);
        } else {
            //Avoid duplicates
            sameSegment.getRelatedFaces().add(segment.getRelatedFaces().get(0));
        }
    }

    private ObjParsingUtils() {};
}
