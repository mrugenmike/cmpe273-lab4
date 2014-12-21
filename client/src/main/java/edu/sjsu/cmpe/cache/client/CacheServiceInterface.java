package edu.sjsu.cmpe.cache.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Cache Service Interface
 * 
 */
public interface CacheServiceInterface {
    public String get(long key);

    public void put(long key, String value);

    public Map<String,Boolean> asyncPut(long key, String value);

    void rollbackWrite(int key,Map<String,Boolean> status);

    public Map<String,String> asyncGet(int key);
}
