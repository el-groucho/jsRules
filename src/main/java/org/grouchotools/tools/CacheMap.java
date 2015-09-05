package org.grouchotools.tools;

import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HashMap implementation suitable for simple caching
 * <p>
 * Created by el-groucho 9/5/2015.
 */
public class CacheMap<K, V> extends LinkedHashMap<K, V> {
    private final int cacheSize;   // the maximum size of the cache
    private final long timeToLive; // the time, in milliseconds that entries can live on the cache
    private final Map<K, StopWatch> stopWatchMap = new HashMap<>();

    private static final int DEFAULT_CACHE_SIZE = 0;        // cache is unlimited
    private static final long DEFAULT_TIME_TO_LIVE = 0;     // entries last until manually removed
    private static final float DEFAULT_LOAD_FACTOR = 0.75f; // same as in HashMap
    private static final boolean DEFAULT_LFU = true;        // cache is least frequently used by default

    public CacheMap() {
        this(DEFAULT_CACHE_SIZE, DEFAULT_TIME_TO_LIVE, DEFAULT_LFU);
    }

    public CacheMap(int cacheSize) {
        this(cacheSize, DEFAULT_TIME_TO_LIVE, DEFAULT_LFU);
    }

    public CacheMap(int cacheSize, long timeToLive) {
        this(cacheSize, timeToLive, DEFAULT_LFU);
    }

    public CacheMap(int cacheSize, long timeToLive, boolean lfu) {
        super(cacheSize, DEFAULT_LOAD_FACTOR, lfu);
        this.cacheSize = cacheSize;
        this.timeToLive = timeToLive;
    }

    @Override
    public V put(K key, V value) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        stopWatchMap.put(key, stopWatch);
        return super.put(key, value);
    }

    @Override
    /**
     * If the time elapsed is greater than the time to live, delete the value from the map and return null
     */
    public V get(Object key) {
        V value;

        StopWatch stopWatch = stopWatchMap.get(key);

        if (stopWatch == null) {
            value = null;
            remove(key);
        } else {
            if (timeToLive > 0 && stopWatch.getTime() > timeToLive) {
                value = null;
                remove(key);
            } else {
                value = super.get(key);
            }
        }

        return value;
    }

    @Override
    public V remove(Object key) {
        stopWatchMap.remove(key);
        return super.remove(key);
    }

    @Override
    /*
        If we go over the max entries, delete the eldest
     */
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return cacheSize > 0 && this.size() > cacheSize;
    }
}
