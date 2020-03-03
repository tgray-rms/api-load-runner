package com.rms.rlax.utility.domain;

public class AuthRequest {

  private String username;
  private String password;
  private String tenantName;

  public AuthRequest() {}

  public AuthRequest(String username, String password, String tenantName) {
    this.username = username;
    this.password = password;
    this.tenantName = tenantName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTenantName() {
    return tenantName;
  }

  public void setTenantName(String tenantName) {
    this.tenantName = tenantName;
  }
}
