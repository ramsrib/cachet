package org.cachet.core;

/**
 * Created with IntelliJ IDEA.
 * User: sriram
 * Date: 02/12/13
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public interface CacheWrapper<K, V> {

    public V get(K key) throws CacheException;

    public void put(K key, V value) throws CacheException;

    public V remove(K key) throws CacheException ;

    public void clear() throws CacheException ;

    public int size() throws CacheException;

}
