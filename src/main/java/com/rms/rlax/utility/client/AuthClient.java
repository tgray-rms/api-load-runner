package com.rms.rlax.utility.client;

import com.rms.rlax.utility.domain.AuthRequest;
import com.rms.rlax.utility.domain.AuthResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class AuthClient {

  private final Client client = ClientBuilder.newClient();

  private String username;
  private String password;
  private String tenantName;
  private String url;

  public AuthClient(String username, String password, String tenantName, String url) {
    this.username = username;
    this.password = password;
    this.tenantName = tenantName;
    this.url = url;
  }

  public String getToken() {

    AuthRequest authRequest = new AuthRequest(username, password, tenantName);

    AuthResponse authResponse =
        client
            .target(url)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(authRequest), AuthResponse.class);

    return authResponse.getAccessToken();
  }
}
