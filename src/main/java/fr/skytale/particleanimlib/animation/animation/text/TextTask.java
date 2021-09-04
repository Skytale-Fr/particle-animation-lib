package fr.skytale.particleanimlib.animation.animation.text;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.animation.parent.task.ARotatingAnimationTask;
import fr.skytale.ttfparser.TTFAlphabet;
import fr.skytale.ttfparser.TTFCharacter;
import fr.skytale.ttfparser.TTFString;
import fr.skytale.ttfparser.tables.TTFPoint;
import fr.skytale.ttfparser.tables.glyf.Glyf;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class TextTask extends ARotatingAnimationTask<Text> {

    private static final double FILL_LINE_PADDING = 0.2;

    TTFAlphabet ttfAlphabet;
    Vector startU, startV;
    Vector currentU, currentV;

    boolean hasColor;
    ParticleTemplate particleTemplate;

    public TextTask(Text text) {
        super(text);
        ttfAlphabet = animation.getTTFAlphabet();
        startU = animation.getU().clone();
        startV = animation.getV().clone();
        particleTemplate = animation.getMainParticle();
        hasColor = particleTemplate.getParticleEffect() == ParticleEffect.REDSTONE;
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

        double detailsLevel = animation.getDetailsLevel().getCurrentValue(iterationCount);

        String notParsedString = animation.getBaseString().getCurrentValue(iterationCount);
        BaseComponent[] components = TextComponent.fromLegacyText(notParsedString, ChatColor.WHITE);
        String baseString = Arrays.stream(components).map(component -> component.toPlainText()).reduce("", String::concat);
        // The scale factor of 1.3 his to fit a Minecraft block size.
        // So a font size of 10.0 will create an upper case character of 10.0 blocks height.
        double fontSize = animation.getFontSize().getCurrentValue(iterationCount) * 1.3d;
        TTFString ttfString = ttfAlphabet.getString(baseString, fontSize);

        int characterIndex = 0;
        for(BaseComponent component : components) {
            TextComponent textComponent = (TextComponent) component;
            String componentCharacters = textComponent.toPlainText();
            for (int i = 0; i < componentCharacters.length(); i++) {
                TTFCharacter ttfCharacter = ttfString.getCharacter(characterIndex);
                Point2D.Double position = ttfString.getPosition(characterIndex);
                Vector vecU = currentU.clone().multiply(position.getX());
                Vector vecV = currentV.clone().multiply(position.getY());
                Vector charPadding = vecU.add(vecV);

                showCharacter(ttfCharacter, charPadding, iterationBaseLocation, component.getColor(), detailsLevel);

                characterIndex++;
            }
        }

//        for (int i = 0; i < ttfString.length(); i++) {
//            TTFCharacter ttfCharacter = ttfString.getCharacter(i);
//            Point2D.Double position = ttfString.getPosition(i);
//            Vector vecU = currentU.clone().multiply(position.getX());
//            Vector vecV = currentV.clone().multiply(position.getY());
//            Vector charPadding = vecU.add(vecV);
//            showCharacter(ttfCharacter, charPadding, iterationBaseLocation);
//        }

    }

    private void showCharacter(TTFCharacter character, Vector charPadding, Location iterationBaseLocation, ChatColor color, double detailsLevel) {
        Glyf glyf = character.getGlyf();
        List<List<TTFPoint>> contours = glyf.getContours();
        for (int contourIndex = 0; contourIndex < contours.size(); contourIndex++) {
            List<TTFPoint> points = contours.get(contourIndex);
//            for(TTFPoint point : points) {
//                displayPoint(point, charPadding, iterationBaseLocation, color);
//            }
            for (int i = 0; i < points.size(); i++) {
                TTFPoint point1 = points.get(i);
                TTFPoint point2;
                if (point1.isLastPointOfContour()) {
                    point2 = points.get(0);
                } else {
                    point2 = points.get(i + 1);
                }

                fillLines(point1, point2, charPadding, detailsLevel, iterationBaseLocation, color);
            }
        }
    }

    private void displayPoint(TTFPoint point, Vector charPadding, Location iterationBaseLocation, ChatColor color) {
        final Collection<? extends Player> viewers = animation.getViewers().getPlayers(iterationBaseLocation);

        Vector vec = getVectorFromPoint(point);
        Location particleLocation = iterationBaseLocation.clone().add(vec).add(charPadding);
        ParticleBuilder particleBuilder = particleTemplate.getParticleBuilder(particleLocation);
        if(hasColor) {
            particleBuilder.setColor(color.getColor());
        }
        particleBuilder.display(viewers);
    }

    private void fillLines(TTFPoint point1, TTFPoint point2, Vector charPadding, double padding, Location iterationBaseLocation, ChatColor color) {
        final Collection<? extends Player> viewers = animation.getViewers().getPlayers(iterationBaseLocation);

        Vector vec1 = getVectorFromPoint(point1);
        Vector vec2 = getVectorFromPoint(point2);
        Vector vec12 = vec2.clone().subtract(vec1).normalize().multiply(padding);

        Location particleLocation = iterationBaseLocation.clone().add(vec1).add(charPadding);
        Location endLocation = iterationBaseLocation.clone().add(vec2).add(charPadding);
        while (particleLocation.distance(endLocation) > padding) {
            ParticleBuilder particleBuilder = particleTemplate.getParticleBuilder(particleLocation);
            if(hasColor) {
                particleBuilder.setColor(color.getColor());
            }
            particleBuilder.display(viewers);
//            showPoint(animation.getPointDefinition(), particuleLocation, iterationBaseLocation);
            particleLocation.add(vec12);
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