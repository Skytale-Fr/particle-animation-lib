package fr.skytale.particleanimlib.animation.parent.animation;

import fr.skytale.particleanimlib.animation.attribute.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AAnimation implements Cloneable {

    protected APosition position;
    protected ParticleTemplate mainParticle;
    protected JavaPlugin plugin;
    protected int ticksDuration;
    protected IVariable<Integer> showFrequency;
    protected AnimationEndedCallback callback;

    public abstract void show();

    /***********GETTERS & SETTERS***********/

    public APosition getPosition() {
        return position;
    }

    public void setPosition(APosition position) {
        this.position = position;
    }

    public ParticleTemplate getMainParticle() {
        return mainParticle;
    }

    public void setMainParticle(ParticleTemplate mainParticle) {
        this.mainParticle = mainParticle;
    }

    public int getTicksDuration() {
        return ticksDuration;
    }

    public void setTicksDuration(int ticksDuration) {
        this.ticksDuration = ticksDuration;
    }

    public IVariable<Integer> getShowFrequency() {
        return showFrequency;
    }

    public void setShowFrequency(IVariable<Integer> showFrequency) {
        this.showFrequency = showFrequency;
    }

    public AnimationEndedCallback getCallback() {
        return callback;
    }

    public void setCallback(AnimationEndedCallback callback) {
        this.callback = callback;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public AAnimation clone() {
        AAnimation obj = null;
        try {
            obj = (AAnimation) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        obj.position = this.position.clone();
        obj.mainParticle = mainParticle == null ? null : new ParticleTemplate(this.mainParticle);
        return obj;
    }

}
