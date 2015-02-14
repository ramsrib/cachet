package org.cachet.support.ehcache;

import net.sf.ehcache.Element;
import org.cachet.core.Cache;
import org.cachet.core.CacheException;
import org.cachet.core.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: sriram
 * Date: 05/12/13
 * Time: 23:03
 * To change this template use File | Settings | File Templates.
 */
public class EhCacheManager implements CacheManager {

    private static final Logger log = LoggerFactory.getLogger(EhCacheManager.class);

    private net.sf.ehcache.CacheManager mCacheManager;


    public EhCacheManager() {
        URL cacheConfigurationUrl = getClass().getClassLoader().getResource("ehcache.xml");
        log.info("TESTER");
        if (log.isInfoEnabled()) {
            log.trace("Cache Configuration File Url : " + cacheConfigurationUrl.toString());
        }
        mCacheManager = net.sf.ehcache.CacheManager.create(cacheConfigurationUrl);
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        try {
            net.sf.ehcache.Ehcache cache = mCacheManager.getEhcache(name);
            cache.removeAll();
            if (cache == null) {
                mCacheManager.addCache(name);
                cache = mCacheManager.getCache(name);
            }
            return new EhCache<K, V>(cache);
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

/*    @Override
    public <K, V> void putCache(String name, K key, V value) throws CacheException {
        try {
            net.sf.ehcache.Ehcache cache = mCacheManager.getEhcache(name);
            if (cache != null) {
                Element element = new Element(key, value);
                cache.put(element);
            } else {
                throw new CacheException(name + " Cache not found.");
            }
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }*/

    //@Override
    public void addCache(Cache cache) {
        // not needed, get cache has to handle this
        //mCacheManager.addCache(cache);
    }

    @Override
    public void removeCache(String name) {
        mCacheManager.removeCache(name);
    }

    @Override
    public void clearAll() {
        mCacheManager.clearAll();
    }

    @Override
    public String[] getAllCaches() {
        return mCacheManager.getCacheNames();
    }
}
