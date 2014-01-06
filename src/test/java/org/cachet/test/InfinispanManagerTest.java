package org.cachet.test;

import org.cachet.core.Cache;
import org.cachet.core.CacheException;
import org.cachet.core.CacheManager;
import org.cachet.support.infinispan.InfinispanCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by sriram on 06/01/14.
 */
public class InfinispanManagerTest {

    private static final String configFile = "infinispan.xml";

    @Before
    public void setUp() {
        //cacheManager = new EhCacheManager();
    }

    @After
    public void tearDown() {
        //
    }

    @Test
    public void testCacheManager() {
        CacheManager cacheManager = new InfinispanCacheManager(configFile);
        assertNotNull(cacheManager);

        Cache<String, String> cache = null;
        try {
            cache = cacheManager.getCache("test");
            assertNotNull(cache);
            cache.put("hello", "world");
            String value = cache.get("hello");
            assertNotNull(value);
            assertEquals(value, "world");
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }
}
