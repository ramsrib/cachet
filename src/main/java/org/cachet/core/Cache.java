package org.cachet.core;

/**
 * Created with IntelliJ IDEA.
 * User: sriram
 * Date: 02/12/13
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public interface Cache<K, V> {

    public V get(K key) throws CacheException;

    // put should return the old values like JCache API
    public void put(K key, V value) throws CacheException;

    public V remove(K key) throws CacheException ;

    public void clear() throws CacheException ;

    public int size() throws CacheException;

    //public Set<K> keySet();
    //public Collection<V> values();
    //public Set<Entry<K,V>> entrySet();

}
