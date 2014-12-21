package edu.sjsu.cmpe.cache.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.*;
import java.util.concurrent.*;

public class CRDTClient {
    private final List<String> servers;
    private final DistributedCacheService distributedCacheService;

    public CRDTClient(List<String> servers) {
        this.servers = servers;
        this.distributedCacheService = new DistributedCacheService(null, servers);
    }

    public void put(int key, String value) {
        Map<String, Boolean> writeStatus = distributedCacheService.asyncPut(key, value);

        if(!hasSufficientSuccessRate(writeStatus)){
            distributedCacheService.rollbackWrite(key,writeStatus);
        }
    }

    private boolean hasSufficientSuccessRate(Map<String, Boolean> statusMap) {
        int successFullWrites = 0;
        for(Map.Entry<String, Boolean> statusEntry:statusMap.entrySet()){
            if(statusEntry.getValue()){
                successFullWrites+=1;
            }
        }
        if (successFullWrites >=2)
            return true;
        else return false;
    }

    public void get(int key) {
        final Map<String, String> serverAndValues = distributedCacheService.asyncGet(key);
        HashMap<String, Integer> alphabetAndCount = new HashMap<String, Integer>(3);

        for(Map.Entry<String,String> entry: serverAndValues.entrySet()){
            String serverUrl = entry.getKey();
            String alphabet = entry.getValue();
            if(alphabetAndCount.containsKey(alphabet) && alphabet!=null){
                int value = alphabetAndCount.get(alphabet);
                alphabetAndCount.put(alphabet, value + 1);
            }else{
                alphabetAndCount.put(alphabet, 1);
            }
        }

        Integer max = null;
        if (alphabetAndCount.size()>0){
            max = Collections.max(alphabetAndCount.values());
        }

        String majorityValue = null;
        for(Map.Entry entry :alphabetAndCount.entrySet()) {
            if(entry.getValue() == max){
                majorityValue = (String) entry.getKey();
            }
        }

        for(Map.Entry<String,String> entry: serverAndValues.entrySet()){
            if(entry.getValue()==null){
                System.out.println(String.format("Now Read repairing the Key %s for node %s ",key,entry.getKey()));
                String nodeToBeRepaired = entry.getKey();
                DistributedCacheService repairDistributed = new DistributedCacheService(nodeToBeRepaired, null);
                System.out.println("majorityValue is: "+majorityValue);
                repairDistributed.put(key,majorityValue);
            }
        }

    }
}



class PutCall implements Callable<HttpResponse<JsonNode>> {
    private final String serverUrl;
    private final String key;
    private final String value;
    private final Callback callback;

    public PutCall(String serverUrl,String key,String value,Callback callback){
        this.serverUrl = serverUrl;
        this.key = key;
        this.value = value;
        this.callback = callback;
    }

    @Override
    public HttpResponse<JsonNode> call() throws Exception {
        System.out.println(String.format("Trying to put %s => %s in node %s",key,value,this.serverUrl));
        return (HttpResponse<JsonNode>) Unirest.put(this.serverUrl + "/cache/{key}/{value}")
                .header("accept", "application/json")
                .routeParam("key", key)
                .routeParam("value", value)
                .asJsonAsync(callback).get();
    }
}

class PutCallBackImpl implements Callback<JsonNode> {
    private final String serverUrl;
    private final Map<String, Boolean> statusMap;

    public PutCallBackImpl(String serverUrl, Map<String, Boolean> statusMap){
        this.serverUrl = serverUrl;
        this.statusMap = statusMap;
    }

    public void failed(UnirestException e) {
        System.out.println("Request Failed to node with url "+ serverUrl);
        statusMap.put(serverUrl, false);
    }

    public void completed(HttpResponse<JsonNode> response) {
        if(response.getStatus()==200){
            System.out.println("Request Succeeded for node "+ serverUrl);
            statusMap.put(serverUrl,true);
        }else{
            statusMap.put(serverUrl,false);
        }
    }

    public void cancelled() {
        System.out.println("The request has been cancelled");
    }
}