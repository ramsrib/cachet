package org.cachet.test;

import org.cachet.core.Cache;
import org.cachet.core.CacheException;
import org.cachet.core.CacheManager;
import org.cachet.support.ehcache.EhCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by sriram on 06/01/14.
 */
public class EhCacheManagerTest {

    // private EhCacheManager cacheManager;

    @Before
    public void setUp() {
        //cacheManager = new EhCacheManager();
    }

    @After
    public void tearDown() {
        //
    }

    @Test
    public void testCacheManagerCreation() {

        CacheManager cacheManager = new EhCacheManager();
        assertNotNull(cacheManager);

        /*net.sf.ehcache.CacheManager ehCacheManager = cacheManager.getCacheManager();
        assertNull(ehCacheManager);
        cacheManager.init();
        //now assert that an internal CacheManager has been created:
        ehCacheManager = cacheManager.getCacheManager();
        assertNotNull(ehCacheManager);*/
    }

    @Test
    public void testGetCache() {

        CacheManager cacheManager = new EhCacheManager();
        assertNotNull(cacheManager);

        Cache<String, String> cache = null;
        try {
            cache = cacheManager.getCache("test");
            assertNotNull(cache);
        } catch (CacheException e) {
            e.printStackTrace();
        }

        //now assert that an internal CacheManager has been created:
        //ehCacheManager = cacheManager.getCacheManager();


/*      cache.put("hello", "world");
        String value = cache.get("hello");
        assertNotNull(value);
        assertEquals(value, "world");*/
    }


    @Test
    public void testPutCache() {

        CacheManager cacheManager = new EhCacheManager();
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
