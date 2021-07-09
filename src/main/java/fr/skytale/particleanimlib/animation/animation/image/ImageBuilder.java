package fr.skytale.particleanimlib.animation.animation.image;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;
import org.bukkit.util.Vector;

import java.awt.*;

public class ImageBuilder extends ARotatingAnimationBuilder<Image> {

    public ImageBuilder() {
        super();
        animation.setU(new Vector(1, 0, 0));
        animation.setV(new Vector(0, 1, 0));
        animation.setRotationAxis(null);
        animation.setRotationAngleAlpha(null);
        animation.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
    }

    @Override
    protected Image initAnimation() {
        return new Image();
    }

    /********* Image specific setters ***********/
    public void setDirectorVectors(Vector u, Vector v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException("Director vectors should not be null");
        }

        animation.setU(u);
        animation.setV(v.clone().multiply(-1));
    }

    public void setImageFileName(String imageFileName) {
        animation.setImageFileName(imageFileName);
    }


    @Override
    public Image getAnimation() {
        return super.getAnimation();
    }
}
