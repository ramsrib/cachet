package org.cachet.support.ehcache;

import org.cachet.core.CacheManagerWrapper;
import org.cachet.core.CacheWrapper;
import org.cachet.core.CacheException;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: sriram
 * Date: 05/12/13
 * Time: 23:03
 * To change this template use File | Settings | File Templates.
 */
public class EhCacheManager implements CacheManagerWrapper {

    private net.sf.ehcache.CacheManager mCacheManager;

    public EhCacheManager() {
        URL cacheConfigurationUrl = getClass().getResource("ehcache.xml");
        System.out.println("Cache Configuration File Url : " + cacheConfigurationUrl.toString());
        mCacheManager = net.sf.ehcache.CacheManager.create(cacheConfigurationUrl);
    }

    @Override
    public <K, V> CacheWrapper<K, V> getCache(String name) throws CacheException {
        try {
            net.sf.ehcache.Ehcache cache = mCacheManager.getEhcache(name);
            if (cache == null) {
                mCacheManager.addCache(name);
                cache = mCacheManager.getCache(name);
            }
            return new EhCache<K, V>(cache);
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

    //@Override
    public void addCache(CacheWrapper cache) {
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
