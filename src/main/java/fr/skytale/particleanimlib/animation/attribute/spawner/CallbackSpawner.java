package fr.skytale.particleanimlib.animation.attribute.spawner;

import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import fr.skytale.particleanimlib.animation.attribute.spawner.parent.ISpawner;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.ParametrizedCallback;

public class CallbackSpawner extends CallbackVariable<ParticlesToSpawn> implements ISpawner {

    /**
     * Construct a callback variable
     *
     * @param callback the callback that will be able to return the current value
     */
    public CallbackSpawner(ParametrizedCallback<ParticlesToSpawn> callback) {
        super(callback);
    }
}
