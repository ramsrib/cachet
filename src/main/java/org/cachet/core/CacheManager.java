package org.cachet.core;

/**
 * Created with IntelliJ IDEA.
 * User: sriram
 * Date: 05/12/13
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public interface CacheManager {

    <K, V> Cache<K, V> getCache(String name) throws CacheException;

//    <K,V> void putCache(String name, K key, V value) throws CacheException;

    // not needed, has to be handled by get cache
    void addCache(Cache cache);

    void removeCache(String name);

    void clearAll();

    String[] getAllCaches();

    }
