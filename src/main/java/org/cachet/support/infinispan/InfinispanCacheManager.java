package com.vasoftware.sf.server.apps.sfmain.cache.infinispan;

import com.vasoftware.sf.server.apps.sfmain.cache.CacheException;
import com.vasoftware.sf.server.apps.sfmain.cache.CacheManagerWrapper;
import com.vasoftware.sf.server.apps.sfmain.cache.CacheWrapper;


import org.apache.commons.lang.StringUtils;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.EmbeddedCacheManager;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Set;

/**
 * Created by sriram on 12/12/13.
 */
public class InfinispanCacheManager implements CacheManagerWrapper {

    // resource injection is not working in the ejb 2.1
    // may be we can pack this in a separate jar to make it work
    // however field injection may not work in constructor

/*
    @Resource(lookup = "java:jboss/infinispan/container/hibernate")
    private CacheContainer container;

    @Resource(lookup = "java:jboss/infinispan/container/hibernate")
    private static EmbeddedCacheManager container1;
*/

    // or

    @Resource(lookup="java:jboss/infinispan/container/ctfcache")
    private org.infinispan.manager.EmbeddedCacheManager mCacheManager;

    //private org.infinispan.manager.CacheManager mCacheManager;


    public InfinispanCacheManager() {
        //URL cacheConfigurationUrl = getClass().getResource("ehcache.xml");
        //System.out.println("Cache Configuration File Url : " + cacheConfigurationUrl.toString());
        if (mCacheManager == null) {
            System.out.println("Cache is not initialized, initializing manually. NO");

            try {
                mCacheManager = (EmbeddedCacheManager) new InitialContext().lookup("java:jboss/infinispan/container/hibernate");
            } catch (NamingException e) {
                e.printStackTrace();
            }
            System.out.println("Initialized the cache manager manually");
            //mCacheManager = new DefaultCacheManager();
        } else {
            System.out.println("Cache manager was initialized successfully");
        }
/*

        if (container == null) {
            System.out.println("Cache Container instance cannot be null");
        } else {
            System.out.println("Cache Container instance initialized successfully.");
            System.out.println("Tring to get entity cache from the cache container");
            if (container.getCache("entity") == null) {
                System.out.println("Cannot get cache (entity) from available cache container instance");
            } else {
                System.out.println("Got Cache (entity) from cache container instance.");
            }
        }

        if (container1 == null) {
            System.out.println("Static Cache Container is null");
        } else {
            System.out.println("Static Cache container was initialized successfully");
        }

        System.out.println("Initializing manually using jndi context.");

        Context ctx = null;
        try {
            ctx = new InitialContext();
            container1 = (EmbeddedCacheManager) ctx.lookup("java:jboss/infinispan/container/hibernate");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        System.out.println("Checking Initialization of cache container.");
        if (container1 == null) {
            System.out.println("Cache Container Initialization is not successful");
        } else {
            System.out.println("Cache Container Initialization is successfully");
            System.out.println("Checking the local-query cache.");
            org.infinispan.Cache cache = container1.getCache("local-query");
            if (cache == null) {
                System.out.println("local-query cache is null");
            } else {
                System.out.println("Got local-query cache");
                System.out.println("inserting a value");
                cache.put("testkey", "testvalue");
                System.out.println("Value in Cache : testkey : " + cache.get("testkey"));
            }


            System.out.println("Checking the non-existing 'test' cache.");
            org.infinispan.Cache cache1 = container1.getCache("test");
            if (cache == null) {
                System.out.println("'test' cache is null, as expected");
            } else {
                System.out.println("Got 'test' cache...");
                System.out.println("inserting a value");
                cache1.put("testkey", "testvalue");
                System.out.println("Value in test Cache : testkey : " + cache1.get("testkey"));
            }

        }
*/

/*
        try {
            //mCacheManager = new DefaultCacheManager("infinispan.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public <K, V> CacheWrapper<K, V> getCache(String name) throws IllegalArgumentException, com.vasoftware.sf.server.apps.sfmain.cache.CacheException {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Cache name cannot be null or empty.");
        }

        try {
            // will return new cache if not present
            //org.infinispan.Cache cache = mCacheManager.getCache(name);
            //return new InfinispanCache<K, V>(cache);

            // or

            org.infinispan.Cache cache = mCacheManager.getCache(name);
            return new InfinispanCache<>(cache);

        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    //@Override
    public void addCache(CacheWrapper cache) {
        //mCacheManager
        // not needed. get cache has to handle this.
    }

    @Override
    public void removeCache(String name) {
        mCacheManager.removeCache(name);
    }

    @Override
    public void clearAll() {
        //mCacheManager.clearAll();
    }

    @Override
    public String[] getAllCaches() {
        Set<String> caches = mCacheManager.getCacheNames();
        // return (String[]) caches.toArray();
        // or
        return caches.toArray(new String[0]);
    }
}