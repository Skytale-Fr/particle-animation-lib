package fr.skytale.particleanimlib.animation.animation.text;

import fr.skytale.particleanimlib.animation.animation.text.parser.FontParsingService;
import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.ttfparser.TTFAlphabet;
import fr.skytale.ttfparser.TTFCharacter;
import fr.skytale.ttfparser.TTFString;
import fr.skytale.ttfparser.tables.TTFPoint;
import fr.skytale.ttfparser.tables.glyf.Glyf;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextTask extends AAnimationTask<Text> {

    private static final FontParsingService fontParsingService = FontParsingService.getInstance();

    private TTFAlphabet ttfAlphabet;

    @IVariableCurrentValue
    private Double detailsLevel;

    @IVariableCurrentValue
    private String text;

    @IVariableCurrentValue
    private Double fontSize;

    public TextTask(Text text) {
        super(text);
        fontParsingService.getTTFAlphabet(text)
                .thenAccept(builtTTFAlphabet -> {
                    this.ttfAlphabet = builtTTFAlphabet;
                    startTask();
                });
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        List<AnimationPointData> animationPointsData = getAnimationPointData();
        if (animation.isAlignCenter()) {
            animationPointsData = alignCenter(animationPointsData);
        }
        return animationPointsData;
    }

    private List<AnimationPointData> alignCenter(List<AnimationPointData> animationPointsData) {
        double maxX = 0;
        double maxY = 0;
        double maxZ = 0;

        for (AnimationPointData animationPointData : animationPointsData) {
            maxX = Math.max(maxX, animationPointData.getFromCenterToPoint().getX());
            maxY = Math.max(maxY, animationPointData.getFromCenterToPoint().getY());
            maxZ = Math.max(maxZ, animationPointData.getFromCenterToPoint().getZ());
        }

        Vector offset = new Vector(-maxX / 2d, -maxY / 2d, -maxZ / 2d);

        return animationPointsData.stream()
                .map(animationPointData -> new AnimationPointData(
                        animationPointData.getFromCenterToPoint().clone().add(offset),
                        animationPointData.getPointDefinitionModifier()
                ))
                .collect(Collectors.toList());
    }

    @NotNull
    private List<AnimationPointData> getAnimationPointData() {
        List<AnimationPointData> animationPointsData = new ArrayList<>();

        //transform String to ttfString
        BaseComponent[] components = TextComponent.fromLegacyText(text, ChatColor.WHITE);
        String parsedString = Arrays.stream(components)
                .map(baseComponent -> baseComponent.toPlainText())
                .reduce("", String::concat);
        // The scale factor of 1.3 his to fit a Minecraft block size.
        // So a font size of 10.0 will create an upper case character of 10.0 blocks height.
        TTFString ttfString = ttfAlphabet.getString(parsedString, fontSize * 1.3d);

        // loop over chars
        int characterIndex = 0;
        for (BaseComponent component : components) {
            TextComponent textComponent = (TextComponent) component;
            String componentCharacters = textComponent.toPlainText();
            for (int i = 0; i < componentCharacters.length(); i++) {
                TTFCharacter ttfCharacter = ttfString.getCharacter(characterIndex);
                Point2D.Double position = ttfString.getPosition(characterIndex);
                Vector vecU = TextTask.U.clone().multiply(position.getX());
                Vector vecV = TextTask.V.clone().multiply(position.getY());
                Vector charPadding = vecU.add(vecV);

                animationPointsData.addAll(getCharacterPoints(ttfCharacter, charPadding, component.getColor(), detailsLevel));

                characterIndex++;
            }
        }
        return animationPointsData;
    }


    private List<AnimationPointData> getCharacterPoints(TTFCharacter character, Vector charPadding, ChatColor color, double detailsLevel) {
        List<AnimationPointData> characterPointsData = new ArrayList<>();
        Glyf glyf = character.getGlyf();
        List<List<TTFPoint>> contours = glyf.getContours();
        for (List<TTFPoint> points : contours) {
            for (int i = 0; i < points.size(); i++) {
                TTFPoint point1 = points.get(i);
                TTFPoint point2;
                if (point1.isLastPointOfContour()) {
                    point2 = points.get(0);
                } else {
                    point2 = points.get(i + 1);
                }

                characterPointsData.addAll(fillLines(point1, point2, charPadding, detailsLevel, color));
            }
        }
        return characterPointsData;
    }

    private List<AnimationPointData> fillLines(TTFPoint point1, TTFPoint point2, Vector charPadding, double padding, ChatColor color) {
        List<AnimationPointData> linePoints = new ArrayList<>();

        Vector vec1 = getVectorFromPoint(point1);
        Vector vec2 = getVectorFromPoint(point2);
        Vector move = vec2.clone().subtract(vec1).normalize().multiply(padding);

        Vector currentPoint = vec1.clone().add(charPadding);
        Vector endPoint = vec2.clone().add(charPadding);

        while (currentPoint.distance(endPoint) > padding) {
            linePoints.add(new AnimationPointData(currentPoint, AnimationPointData.getPointModifierForColor(color.getColor())));
            currentPoint.add(move);
        }
        return linePoints;
    }

    private Vector getVectorFromPoint(TTFPoint point) {
        double x = point.getX();
        double y = point.getY();
        Vector vecU = U.clone().multiply(x);
        Vector vecV = V.clone().multiply(y);
        return vecU.add(vecV);
    }

}