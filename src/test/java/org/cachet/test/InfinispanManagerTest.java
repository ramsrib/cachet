package org.cachet.test;

import org.cachet.core.Cache;
import org.cachet.core.CacheException;
import org.cachet.core.CacheManager;
import org.cachet.support.infinispan.InfinispanCacheManager;
import org.infinispan.commons.configuration.Builder;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntriesEvicted;
import org.infinispan.notifications.cachelistener.event.CacheEntriesEvictedEvent;
import org.infinispan.notifications.cachelistener.event.Event;
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
/*
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
    }*/

/*    @Test
    public void testLRU() throws InterruptedException {
        org.infinispan.manager.EmbeddedCacheManager smCacheManager = new DefaultCacheManager();

        String cacheName = "test1";
        int maxTime = 1000;
        int maxEntries = 5;

        if (!smCacheManager.cacheExists(cacheName)) {
            System.out.println("Cache does not exists. Creating it " + cacheName);

            smCacheManager.defineConfiguration(cacheName, new ConfigurationBuilder()
                            .eviction().maxEntries(maxEntries).strategy(EvictionStrategy.LRU)
                            .expiration().lifespan(maxTime).maxIdle(-1)
                            .build()
            );
        }

        org.infinispan.Cache cache = smCacheManager.getCache(cacheName);
        cache.addListener(new EvictionListener());

        System.out.println(cache.getAdvancedCache().getEvictionManager().isEnabled());

        String id = "id-";
        String object = "object-";

        System.out.println("Size : " + cache.size());
        for (int i = 0; i < maxEntries * 2; i++) {
            System.out.println("inserting into cache : " + id + i + " with value : " + object + i);
            cache.put(id + i, object + i);
//            Thread.sleep(10);
            assertEquals(object + i, cache.get(id + i));
        }

        cache = smCacheManager.getCache(cacheName);
        System.out.println("Size : " + cache.size());

        for (int i = 1; i < maxEntries * 2; i++) {
            System.out.println("Validating the inserted cache : " + id + i + " with expected value : " + object + i);
            System.out.println("Actual Value : "  + cache.get(id + i));
        }
        System.out.println("Size : " + cache.size());

    }*/

    @Test
    public void testCache() {
        int maxTime = 1000;
        int maxEntries = 5;
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.eviction().maxEntries(maxEntries).strategy(EvictionStrategy.FIFO)
                .expiration().lifespan(maxTime).maxIdle(-1);

        org.infinispan.manager.EmbeddedCacheManager smCacheManager = new DefaultCacheManager(builder.build());

        String cacheName = "test1";


        if (!smCacheManager.cacheExists(cacheName)) {
            System.out.println("Cache does not exists. Creating it " + cacheName);
            smCacheManager.defineConfiguration(cacheName, builder.build());
        }


        String key = "key-";
        String value = "value-";
        org.infinispan.Cache cache = smCacheManager.getCache(cacheName);
        // put values
        for (int j = 0; j <maxEntries*2; j++) {
            cache.put(key + j, value + j);
        }

        for (int j = 0; j <maxEntries*2; j++) {
            System.out.println(cache.get(key + j));
        }
        System.out.println("Size : " + cache.size());

    }

    @Listener
    public static class EvictionListener {

        @CacheEntriesEvicted
        public void nodeEvicted(CacheEntriesEvictedEvent e){
            assert e.isPre() || !e.isPre();
            Object key = e.getEntries().keySet().iterator().next();
            assert key != null;
            assert e.getCache() != null;
            assert e.getType() == Event.Type.CACHE_ENTRY_EVICTED;
            System.out.println("Evicting cache with key : " + key);
        }
    }

}
