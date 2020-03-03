package com.rms.rlax.utility.domain;

public class ResponseData {

  private Integer status;
  private Long responseTime;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(Long responseTime) {
    this.responseTime = responseTime;
  }
}
