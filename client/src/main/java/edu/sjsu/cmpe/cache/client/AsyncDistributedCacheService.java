package edu.sjsu.cmpe.cache.client;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

//public class AsyncDistributedCacheService implements  CacheServiceInterface {

   /* private final List<String> serverUrls;
    private final ConcurrentHashMap<String,Boolean> operation;

    public AsyncDistributedCacheService(List<String> serverUrls){
        this.serverUrls = serverUrls;

        operation = new ConcurrentHashMap<String, Boolean>();
        for (String url:serverUrls) {
            operation.put(url, false);
        }
    }
    @Override
    public String get(long key) {

        Future<HttpResponse<JsonNode>> future = Unirest.post("http://httpbin.org/post")
                .header("accept", "application/json")
                .field("param1", "value1")
                .field("param2", "value2")
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        System.out.println("The request has failed");
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        int code = response.getStatus();
                        Headers headers = response.getHeaders();
                        JsonNode body = response.getBody();
                        InputStream rawBody = response.getRawBody();
                    }

                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }

                });
        return null;
    }

    @Override
    public void put(long key, String value) {
        *//*ExecutorService executorService = Executors.newFixedThreadPool(10);

        ArrayList<Callable<Future<HttpResponse<JsonNode>>>> calls = new ArrayList<Callable<Future<HttpResponse<JsonNode>>>>();
        for (String serverUrl: serverUrls){
            CallBackImpl callBack = new CallBackImpl(serverUrl);
            calls.add(new PutCall(serverUrl,Long.toString(key),value,callBack));
        }
        try {
            List<Future<Future<HttpResponse<JsonNode>>>> futures = executorService.invokeAll(calls);
            executorService.awaitTermination(20,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*//*
    }

    @Override
    public List<Future<HttpResponse<JsonNode>>> asyncPut(long key, String value) {
        return null;
    }
}*/
