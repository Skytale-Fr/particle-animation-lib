package fr.skytale.particleanimlib.animation.animation.text;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.animation.parent.task.ARotatingAnimationTask;
import fr.skytale.ttfparser.TTFAlphabet;
import fr.skytale.ttfparser.TTFCharacter;
import fr.skytale.ttfparser.TTFString;
import fr.skytale.ttfparser.tables.TTFPoint;
import fr.skytale.ttfparser.tables.glyf.Glyf;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.awt.geom.Point2D;
import java.util.List;

public class TextTask extends ARotatingAnimationTask<Text> {

    private static final double FILL_LINE_PADDING = 0.2;

    TTFAlphabet ttfAlphabet;
    Vector startU, startV;
    Vector currentU, currentV;

    public TextTask(Text text) {
        super(text);
        ttfAlphabet = text.getTTFAlphabet();
        startU = animation.getU().clone();
        startV = animation.getV().clone();
        this.startTask();
    }

    @Override
    protected void showRotated(boolean rotationChanged, Location iterationBaseLocation) {
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        currentU = startU;
        currentV = startV;

        if(hasRotation && rotationChanged) {
            currentU = rotation.rotateVector(startU);
            currentV = rotation.rotateVector(startV);
        }

        String baseString = animation.getBaseString().getCurrentValue(iterationCount);
        // The scale factor of 1.3 his to fit a Minecraft block size.
        // So a font size of 10.0 will create an upper case character of 10.0 blocks height.
        double fontSize = animation.getFontSize().getCurrentValue(iterationCount) * 1.3d;
        TTFString ttfString = ttfAlphabet.getString(baseString, fontSize);

        for (int i = 0; i < ttfString.length(); i++) {
            TTFCharacter ttfCharacter = ttfString.getCharacter(i);
            Point2D.Double position = ttfString.getPosition(i);
            Vector vecU = currentU.clone().multiply(position.getX());
            Vector vecV = currentV.clone().multiply(position.getY());
            Vector charPadding = vecU.add(vecV);
            showCharacter(ttfCharacter, charPadding, iterationBaseLocation);
        }

    }

    private void showCharacter(TTFCharacter character, Vector charPadding, Location iterationBaseLocation) {
        Glyf glyf = character.getGlyf();
        List<List<TTFPoint>> contours = glyf.getContours();
        for (int contourIndex = 0; contourIndex < contours.size(); contourIndex++) {
            List<TTFPoint> points = contours.get(contourIndex);
            for (int i = 0; i < points.size(); i++) {
                TTFPoint point1 = points.get(i);
                TTFPoint point2;
                if (point1.isLastPointOfContour()) {
                    point2 = points.get(0);
                } else {
                    point2 = points.get(i + 1);
                }

                fillLines(point1, point2, charPadding, FILL_LINE_PADDING, iterationBaseLocation);
            }
        }
    }

    private void fillLines(TTFPoint point1, TTFPoint point2, Vector charPadding, double padding, Location iterationBaseLocation) {
        Vector vec1 = getVectorFromPoint(point1);
        Vector vec2 = getVectorFromPoint(point2);
        Vector vec12 = vec2.clone().subtract(vec1).normalize().multiply(padding);

        Location particuleLocation = iterationBaseLocation.clone().add(vec1).add(charPadding);
        Location endLocation = iterationBaseLocation.clone().add(vec2).add(charPadding);
        while (particuleLocation.distance(endLocation) > padding) {
            showPoint(animation.getPointDefinition(), particuleLocation, iterationBaseLocation);
            particuleLocation.add(vec12);
        }
    }

    private Vector getVectorFromPoint(TTFPoint point) {
        double x = point.getX();
        double y = point.getY();
        Vector vecU = currentU.clone().multiply(x);
        Vector vecV = currentV.clone().multiply(y);
        Vector vec = vecU.add(vecV);
        return vec;
    }

}