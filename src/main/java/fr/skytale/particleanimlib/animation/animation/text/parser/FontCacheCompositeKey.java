package fr.skytale.particleanimlib.animation.animation.text.parser;

import fr.skytale.particleanimlib.animation.animation.text.Text;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class FontCacheCompositeKey {
    // composite key attributes
    private final String fontFileName;

    // not part of the key
    private final JavaPlugin javaPlugin;

    public FontCacheCompositeKey(Text text) {
        this.fontFileName = text.getFontFileName();
        this.javaPlugin = text.getPlugin();
    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    public String getFontFileName() {
        return fontFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontCacheCompositeKey that = (FontCacheCompositeKey) o;
        return Objects.equals(fontFileName, that.fontFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fontFileName);
    }
}
