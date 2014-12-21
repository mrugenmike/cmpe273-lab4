package edu.sjsu.cmpe.cache.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;

import java.util.concurrent.Callable;

class GetCall implements Callable<HttpResponse<JsonNode>> {
    private final String serverUrl;
    private final String key;
    private final Callback callback;

    public GetCall(String serverUrl,String key,Callback callback){
        this.serverUrl = serverUrl;
        this.key = key;
        this.callback = callback;
    }

    @Override
    public HttpResponse<JsonNode> call() throws Exception {
        System.out.println(String.format("Trying to get %s from node %s", key,this.serverUrl));
        return (HttpResponse<JsonNode>)  Unirest.get(this.serverUrl + "/cache/{key}")
                .header("accept", "application/json")
                .routeParam("key", key)
                .asJsonAsync(callback).get();
    }
}
