package org.cachet.support.memcached;

import org.cachet.core.CacheException;
import org.cachet.core.CacheWrapper;
import net.spy.memcached.MemcachedClient;

/**
 * Created by sriram on 17/12/13.
 */
public class MemCache<K, V> implements CacheWrapper<K, V>  {

        private MemcachedClient cache;

    // namespace to differentiate each cache from one another.
        private final String NAMESPACE= "TEAMFORGE:5d41402abc4b2a76b9719d91101";
        private static final int ttl = 0;
        private static final String options = "0";



        public MemCache(MemcachedClient cache) {
            if (cache == null) {
                throw new IllegalArgumentException("Cache argument cannot be null.");
            }
            this.cache = cache;
        }


        @Override
        public V get(K key) throws CacheException {

            Object o = cache.get(NAMESPACE + key);
            if(o == null) {
                System.out.println("Cache MISS for KEY: " + key);
            } else {
                System.out.println("Cache HIT for KEY: " + key);
            }
            return (V) o;
        }

        @Override
        public void put(K key, V value) throws CacheException {
            cache.set(NAMESPACE + key, ttl, options);
        }

        @Override
        public V remove(K key) throws CacheException {
            cache.delete(NAMESPACE + key);
            return null;
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
            return "MemCached [" + cache + "]";
        }

    }