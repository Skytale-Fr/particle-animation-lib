package fr.skytale.particleanimlib.animation.attribute.position.parent;

import fr.skytale.particleanimlib.animation.attribute.position.attr.PositionType;
import fr.skytale.particleanimlib.animation.attribute.position.attr.TrailPositionBuilderCallback;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.entity.Player;

public abstract class ATrailPosition implements IPosition {

    private final TrailPositionBuilderCallback trailPositionBuilderCallback;

    protected ATrailPosition(TrailPositionBuilderCallback trailPositionBuilderCallback) {
        this.trailPositionBuilderCallback = trailPositionBuilderCallback;
    }

    @Override
    public final PositionType getType() {
        return PositionType.TRAIL;
    }

    @Override
    public IPosition copy() {
        return this;
    }

    public void updateAAnimationPosition(AAnimation animationToShow, Player player, TrailPlayerLocationData trailPlayerData) {
        trailPositionBuilderCallback.buildPositionFromTrailPosition(animationToShow, player, trailPlayerData);
    }

    //TODO
    // - StaticTrailPosition: position fixe dans la trainée, orientée dans la direction du mouvement du joueur
    // - TargetingEntityTrailPosition (param rotateAnimationToShow)
    // - TargetingLocationTrailPosition (param rotateAnimationToShow)
    // - RelativeStaticPosition (param rotateAnimationToShow)

    //TODO
    // - vérifier que trail stocke bien les AnimationMove (ce qui ne doit pas être le cas aujourd'hui)
    // - traiter les autres todo en rapport avec les trails

}
