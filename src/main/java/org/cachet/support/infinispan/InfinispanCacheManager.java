package org.cachet.support.infinispan;

import org.apache.commons.lang.StringUtils;
import org.cachet.core.Cache;
import org.cachet.core.CacheException;
import org.cachet.core.CacheManager;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Set;

/**
 * Created by sriram on 12/12/13.
 */
public class InfinispanCacheManager implements CacheManager {


    private static final Logger log = LoggerFactory.getLogger(InfinispanCacheManager.class);
/*
    <cache-container name="cachetContainer" default-cache="default" jndi-name="java:jboss/infinispan/container/cachetContainer" start="EAGER">
        <local-cache name="default"/>
    </cache-container>
*/

    // resource injection is not working in the ejb 2.1
    // may be we can pack this in a separate jar to make it work
    // however field injection may not work in constructor

    @Resource(lookup = "java:jboss/infinispan/container/cachetContainer")
    private org.infinispan.manager.EmbeddedCacheManager mCacheManager;

    @Resource(mappedName = "java:jboss/infinispan/container/cachetContainer")
    private org.infinispan.manager.EmbeddedCacheManager mCacheManager1;

    public InfinispanCacheManager() {

    }

    public InfinispanCacheManager(EmbeddedCacheManager cacheManager){
        this.mCacheManager = cacheManager;
    }

    public InfinispanCacheManager(String configFile) {
        // configFile = "infinispan.xml";
        try {
            mCacheManager = new DefaultCacheManager(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        if (mCacheManager1 == null) {
            log.info("mCacheManager1 (mappedName) is null.");
        }
        if (mCacheManager == null) {
            if (log.isInfoEnabled()) {
                log.info("Cache Manager is not injected, initializing manually.");
            }
            try {
                mCacheManager = (EmbeddedCacheManager) new InitialContext().lookup("java:jboss/infinispan/container/cachetContainer");
            } catch (NamingException e) {
                e.printStackTrace();
            }
            if (mCacheManager == null) {
                log.info("Fallback to hibernate cache container.");
                try {
                    mCacheManager = (EmbeddedCacheManager) new InitialContext().lookup("java:jboss/infinispan/container/hibernate");
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
            if (log.isInfoEnabled()) {
                log.info("Initialized the cache manager manually");
            }
            //mCacheManager = new DefaultCacheManager();
        } else {
            System.out.println("Cache Manager initialized automatically using @Resource");
            if (log.isInfoEnabled()) {
                log.info("Cache manager was already initialized successfully");
            }
        }
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws IllegalArgumentException, CacheException {

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
    public void addCache(Cache cache) {
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
        init();
        Set<String> caches = mCacheManager.getCacheNames();
        return caches.toArray(new String[0]);
    }
}