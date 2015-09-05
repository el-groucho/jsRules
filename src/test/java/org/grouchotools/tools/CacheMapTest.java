package org.grouchotools.tools;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by el-groucho 9/5/2015.
 */
public class CacheMapTest {
    private final String key = "testKey";
    private final String value = "testValue";

    private Map<String, String> cacheMap;

    @Test
    public void timeToLiveTest() throws Exception {
        int cacheSize = 0; // unlimited
        long timeToLive = 100; // 100 milliseconds

        cacheMap = new CacheMap<>(0, timeToLive);

        cacheMap.put(key, value);

        Thread.sleep(timeToLive * 5);

        assertNull(cacheMap.get(key));
    }

    @Test
    public void defaultCacheMapTest() throws Exception {
        cacheMap = new CacheMap<>();

        cacheMap.put(key, value);

        assertEquals(value, cacheMap.get(key));
    }

    @Test
    public void cacheSizeeTest() throws Exception {
        int cacheSize = 1;

        cacheMap = new CacheMap<>(1);

        cacheMap.put(key, value);
        cacheMap.put("key2", "value2");

        assertNull(cacheMap.get(key));
    }
}
