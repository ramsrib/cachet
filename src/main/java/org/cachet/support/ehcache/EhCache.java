package org.cachet.support.ehcache;

import net.sf.ehcache.Element;
import org.cachet.core.Cache;
import org.cachet.core.CacheException;


/**
 * Created with IntelliJ IDEA.
 * User: sriram
 * Date: 02/12/13
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */
public class EhCache<K, V> implements Cache<K, V> {

//    private final String cacheName;
//    private final CacheManager cacheManager;

    private net.sf.ehcache.Ehcache cache;

/*    public EhCache(String cacheName, CacheManager cacheManager) {
        this.cacheName = cacheName;
        this.cacheManager = cacheManager;
    }*/

    public EhCache(net.sf.ehcache.Ehcache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.cache = cache;
    }


/*    @Override
    public Cache getCache() throws CacheException {
        return cacheManager.getCache(cacheName);
    }*/

    @Override
    public void put(K key, V value) throws CacheException {

        try {
            //V previous = get(key);
            Element element = new Element(key, value);
            cache.put(element);
            //return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }

        //getCache().put(key, value);
    }

    @Override
    public V get(K key) throws CacheException {

        try {
            if (key == null) {
                return null;
            } else {
                Element element = cache.get(key);
                if (element != null) {
                    return (V) element.getObjectValue();
                }
                return null;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }

/*        Element element = getCache().get(key);
        if (element!=null) {
            return (V) element.getObjectValue();
        }
        return null;*/
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
            cache.removeAll();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public int size() throws CacheException {
        try {
            return cache.getSize();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public String toString() {
        return "EhCache [" + cache.getName() + "]";
    }

}
