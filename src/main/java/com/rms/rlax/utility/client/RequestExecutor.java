package com.rms.rlax.utility.client;

import com.rms.rlax.utility.common.HttpMethod;
import com.rms.rlax.utility.common.RequestTask;
import com.rms.rlax.utility.domain.ResponseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class RequestExecutor {

  private AuthClient authClient;

  public RequestExecutor(AuthClient authClient) {
    this.authClient = authClient;
  }

  public void execute(Integer threads, String uri, HttpMethod method, String body) {

    String token = authClient.getToken();

    ExecutorService executorService = Executors.newCachedThreadPool();
    List<Future<ResponseData>> responses = new ArrayList<>();

    for (int i = 0; i < threads; i++) {
      RequestTask requestTask = new RequestTask(uri, method, body, token);
      responses.add(executorService.submit(requestTask));
    }

    List<ResponseData> responseDataList =
        responses.stream().map(this::extract).collect(Collectors.toList());

    executorService.shutdown();

    // Count response codes
    Map<Integer, Integer> responseCodes = new HashMap<>();
    Long responseTimeTotal = 0L;
    Long maxResponseTime = 0L;
    for (ResponseData responseData : responseDataList) {
      responseCodes.put(
          responseData.getStatus(), responseCodes.getOrDefault(responseData.getStatus(), 0) + 1);
      responseTimeTotal += responseData.getResponseTime();
      if (maxResponseTime < responseData.getResponseTime())
        maxResponseTime = responseData.getResponseTime();
    }

    System.out.println("");
    System.out.println("Load Tester");
    System.out.println("==========================");
    System.out.println("URL: " + uri);
    System.out.println("Method: " + method.name());
    System.out.println("==========================");
    System.out.println("Total requests made: " + responseDataList.size());
    System.out.println("Response Code / Amount");
    responseCodes
        .keySet()
        .forEach(
            key -> {
              System.out.println("\t" + key + ": " + responseCodes.get(key));
            });

    System.out.println(
        "Average response time: " + (responseTimeTotal / responseDataList.size()) + "ms");
    System.out.println("Max response time: " + maxResponseTime + "ms");
  }

  private ResponseData extract(Future<ResponseData> future) {
    try {
      return future.get();
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
}
