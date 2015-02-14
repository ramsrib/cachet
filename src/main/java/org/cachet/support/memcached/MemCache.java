package org.cachet.support.memcached;

import net.spy.memcached.MemcachedClient;
import org.cachet.core.Cache;
import org.cachet.core.CacheException;

/**
 * Created by sriram on 17/12/13.
 */
public class MemCache<K, V> implements Cache<K, V> {

    private MemcachedClient client;

    // namespace to differentiate each client from other
    private final String CACHE_REGION = "CACHET:";
    private static final int ttl = 0;
    private static final String options = "0";


    public MemCache(MemcachedClient client) {
            if (client == null) {
                throw new IllegalArgumentException("Cache argument cannot be null.");
            }
            this.client = client;
        }


        @Override
        public V get(K key) throws CacheException {

            Object cacheObject = client.get(CACHE_REGION + key);
            if(cacheObject == null) {
                System.out.println("Cache MISS for KEY: " + key);
            } else {
                System.out.println("Cache HIT for KEY: " + key);
            }
            return (V) cacheObject;
        }

        @Override
        public void put(K key, V value) throws CacheException {
            client.set(CACHE_REGION + key, ttl, options);
        }

        @Override
        public V remove(K key) throws CacheException {
            client.delete(CACHE_REGION + key);
            return null;
        }

        @Override
        public void clear() throws CacheException {
            try {
                //client.removeAll();
            } catch (Throwable t) {
                throw new CacheException(t);
            }
        }


        public int size() throws CacheException {
            try {
                //return client.getSize();
            } catch (Throwable t) {
                throw new CacheException(t);
            }
            return 0;
        }

        public String toString() {
            return "MemCached [" + client + "]";
        }

    }