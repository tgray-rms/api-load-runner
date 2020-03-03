package com.rms.rlax.utility.common;

import com.rms.rlax.utility.domain.ResponseData;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.Callable;

public class RequestTask implements Callable<ResponseData> {

  private String uri;
  private HttpMethod method;
  private String body;
  private String token;

  public RequestTask(String uri, HttpMethod method, String body, String token) {
    this.uri = uri;
    this.method = method;
    this.body = body;
    this.token = token;
  }

  @Override
  public ResponseData call() {

    Client client = ClientBuilder.newClient();
    Invocation.Builder builder =
        client
            .target(uri)
            .request(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token);
    Response response = null;

    long startTime = System.currentTimeMillis();

    switch (method) {
      case GET:
        response = builder.get();
        break;
      case POST:
        response = builder.post(Entity.json(body));
        break;
      case PUT:
        response = builder.put(Entity.json(body));
        break;
      case DELETE:
        response = builder.delete();
        break;
      case HEAD:
        response = builder.head();
        break;
      case OPTIONS:
        response = builder.options();
        break;
      default:
        throw new RuntimeException("Unexpected HttpMethod");
    }

    long responseTime = System.currentTimeMillis() - startTime;

    // Collect metrics
    ResponseData responseData = new ResponseData();
    responseData.setStatus(response.getStatus());
    responseData.setResponseTime(responseTime);

    return responseData;
  }
}
