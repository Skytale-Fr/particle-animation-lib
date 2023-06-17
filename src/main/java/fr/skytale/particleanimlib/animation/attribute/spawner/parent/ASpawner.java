package fr.skytale.particleanimlib.animation.attribute.spawner.parent;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.ParametrizedCallbackWithPreviousValue;
import org.bukkit.Location;

public abstract class ASpawner extends CallbackWithPreviousValueVariable<ParticlesToSpawn> {

    public ASpawner(ParticlesToSpawn startValue, ParametrizedCallbackWithPreviousValue<ParticlesToSpawn> callback) {
        super(startValue, callback);
    }

    public ASpawner(CallbackWithPreviousValueVariable<ParticlesToSpawn> callbackWithPreviousValueVariable) {
        super(callbackWithPreviousValueVariable);
    }

}
