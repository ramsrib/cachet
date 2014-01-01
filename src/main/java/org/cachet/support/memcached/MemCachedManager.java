package com.vasoftware.sf.server.apps.sfmain.cache.memcached;

import com.vasoftware.sf.server.apps.sfmain.cache.CacheException;
import com.vasoftware.sf.server.apps.sfmain.cache.CacheManagerWrapper;
import com.vasoftware.sf.server.apps.sfmain.cache.CacheWrapper;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.URL;

/**
 * Created by sriram on 17/12/13.
 */
public class MemCachedManager implements CacheManagerWrapper {

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
    public <K, V> CacheWrapper<K, V> getCache(String name) throws CacheException {

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
    public void addCache(CacheWrapper cache) {
        // not needed, get cache has to handle this
        //mCacheManager.addCache(cache);
    }

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
