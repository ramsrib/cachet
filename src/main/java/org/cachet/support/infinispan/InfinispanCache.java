package org.cachet.support.infinispan;

import org.cachet.core.CacheException;
import org.cachet.core.CacheManagerWrapper;
import org.cachet.core.CacheWrapper;

/**
 * Created by sriram on 12/12/13.
 */
public class InfinispanCache<K, V> implements CacheWrapper<K, V> {


        private org.infinispan.Cache<K,V> cache;
/*
        private final String cacheName;
        private final CacheManagerWrapper cacheManager;*/

        public InfinispanCache(org.infinispan.Cache cache) {
            if (cache == null) {
                throw new IllegalArgumentException("Cache argument cannot be null.");
            }
            this.cache = cache;
        }

/*        public InfinispanCache(String cacheName, CacheManagerWrapper cacheManager) {
            this.cacheName = cacheName;
            this.cacheManager = cacheManager;
        }*/
/*
        @Override
        public CacheWrapper<K,V> getCache() {
            return cacheManager.getCache(cacheName);
        }

        @Override
        public void put(K key, V value) {
            getCache().put(key, value);
        }

        @Override
        public V get(K key) {
            getCache().get(key);
        }*/


    @Override
    public V get(K key) throws CacheException {

        try {
            if (key != null) {
                V value = (V) cache.get(key);
                return value;
            }
            return null;
        } catch (Throwable t) {
            throw new CacheException(t);
        }

    }

    @Override
    public void put(K key, V value) throws CacheException {
        try {
            cache.put(key, value);
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        try {
            V previous = get(key);
            cache.remove(key);
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        try {
            //cache.removeAll();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }


    public int size() throws CacheException {
        try {
            //return cache.getSize();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
        return 0;
    }

    public String toString() {
        return "Infinispan [" + cache.getName() + "]";
    }

}
