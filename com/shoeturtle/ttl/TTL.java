package com.shoeturtle.ttl;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: Binay
 * Date: 23/07/14
 * Time: 8:22 PM
 */
public class TTL implements TTLCache {

    protected static Map<String, Object> ttlCache = new HashMap<String, Object>();
    protected long timeToLiveSeconds;
    protected String key;

    /**
     * @param key ­ The key to associate with the cache
     * @param value ­ The actual value to store in the cache
     * @param timeToLiveSeconds ­ The time to live for this object in the cache
     * @param timeToLiveSeconds ­ The time to live for this object in the cache
     */
    @Override
    public void put(String key, Object value, long timeToLiveSeconds) {
        ttlCache.put(key, value);
        this.timeToLiveSeconds = timeToLiveSeconds;
        this.key = key;

        Timer timer = new Timer();
        timer.schedule(new RemoveObject(key), timeToLiveSeconds * 1000);
    }

    /**
     * Returns the Object in the cache that is associated with passed key, or NULL if no value is associated with the key
     * @param key ­ The key associated with the value to retrieve
     *
     */
    @Override
    public Object get(String key) {
        Object ttlObject = ttlCache.containsKey(key) ? ttlCache.get(key) : null;
        return ttlObject;
    }

    /**
     *
     * @return - The number of objects in the cache
     */
    @Override
    public int size() {
        return ttlCache.size();
    }

    protected class RemoveObject extends TimerTask {
        String key;
        RemoveObject(String key) {
            this.key = key;
        }
        @Override
        public void run() {
            if (ttlCache.containsKey(key)) {
                ttlCache.remove(key);
            }
        }
    }
}
