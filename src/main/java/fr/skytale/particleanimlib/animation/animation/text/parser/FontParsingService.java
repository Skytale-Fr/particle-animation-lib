package fr.skytale.particleanimlib.animation.animation.text.parser;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import fr.skytale.particleanimlib.animation.animation.text.Text;
import fr.skytale.ttfparser.TTFAlphabet;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class FontParsingService {

    private static final int CACHE_EXPIRATION_MINUTES_DURATION = 5;
    private static FontParsingService instance;

    private final AsyncLoadingCache<FontCacheCompositeKey, TTFAlphabet> ttfAlphabetCache = Caffeine.newBuilder()
            .expireAfterWrite(CACHE_EXPIRATION_MINUTES_DURATION, TimeUnit.MINUTES)
            .buildAsync(this::buildTTFAlphabet);

    private FontParsingService() {
    }

    public static FontParsingService getInstance() {
        if (instance == null) {
            instance = new FontParsingService();
        }
        return instance;
    }

    public CompletableFuture<TTFAlphabet> getTTFAlphabet(Text textAnimation) {
        return ttfAlphabetCache.get(new FontCacheCompositeKey(textAnimation));
    }

    private @NotNull CompletableFuture<TTFAlphabet> buildTTFAlphabet(FontCacheCompositeKey fontCacheKey, Executor executor) {
        return CompletableFuture.supplyAsync(
                () -> FontParsingUtils.buildTTFAlphabet(fontCacheKey.getJavaPlugin(), fontCacheKey.getFontFileName()),
                executor
        );
    }

}
