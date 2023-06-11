package fr.skytale.particleanimlib.animation.animation.obj.parser;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import fr.skytale.particleanimlib.animation.animation.obj.Obj;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class ObjParsingService {

    private static final int CACHE_EXPIRATION_MINUTES_DURATION = 5;
    private static ObjParsingService instance;

    private final AsyncLoadingCache<ObjPointsCacheCompositeKey, List<Vector>> objPointsCache = Caffeine.newBuilder()
            .expireAfterWrite(CACHE_EXPIRATION_MINUTES_DURATION, TimeUnit.MINUTES)
            .buildAsync(this::buildObjPoints);

    private ObjParsingService() {
    }

    public static ObjParsingService getInstance() {
        if (instance == null) {
            instance = new ObjParsingService();
        }
        return instance;
    }

    public CompletableFuture<List<Vector>> getObjPoints(Obj obj) {
        return objPointsCache.get(new ObjPointsCacheCompositeKey(obj));
    }

    private @NotNull CompletableFuture<List<Vector>> buildObjPoints(ObjPointsCacheCompositeKey objPointsCacheCompositeKey, Executor executor) {
        return CompletableFuture.supplyAsync(
                () -> ObjParsingUtils.buildObjPoints(objPointsCacheCompositeKey),
                executor
        );
    }

}
