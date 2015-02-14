package org.cachet.support.memcached;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import org.cachet.core.Cache;
import org.cachet.core.CacheException;
import org.cachet.core.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by sriram on 17/12/13.
 */
public class MemCachedManager implements CacheManager {

    private static final Logger log = LoggerFactory.getLogger(MemCachedManager.class);


    private MemcachedClient mCacheManager;
    private static MemcachedClient[] m = null;

    public MemCachedManager() {
        try {
            MemcachedClient mCacheManager =  new MemcachedClient(new BinaryConnectionFactory(), AddrUtil.getAddresses("127.0.0.1:11211"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {

        MemcachedClient c= null;
        try {
            int i = (int) (Math.random()* 20);
            if(m[i] == null) {
                m[i] = new MemcachedClient(new BinaryConnectionFactory(), AddrUtil.getAddresses("127.0.0.1:11211"));
            }
            c = m[i];
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new MemCache<>(c);
    }

    //@Override
    public void addCache(Cache cache) {
        // not needed, get cache has to handle this
        //mCacheManager.addCache(cache);
    }

/*
    public void createCache(Cache cache) {
        // not needed, get cache has to handle this
        //mCacheManager.addCache(cache);
    }
   */

    @Override
    public void removeCache(String name) {
    }

    @Override
    public void clearAll() {
    }

    @Override
    public String[] getAllCaches() {
        return null;
    }
}
