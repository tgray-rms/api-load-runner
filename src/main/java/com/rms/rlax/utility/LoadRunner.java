package com.rms.rlax.utility;

import com.rms.rlax.utility.client.AuthClient;
import com.rms.rlax.utility.client.RequestExecutor;
import com.rms.rlax.utility.common.HttpMethod;

public class LoadRunner {

  // How many concurrent threads/requests?
  static Integer threads = 10;

  // Auth info
  static String username = "YOUR_USERNAME";
  static String password = "YOUR_PASSWORD";
  static String tenantName = "rms-npe-rlax-dev-general";
  static String url =
      "https://service-management.apps.dev.npe.rms-internal.com/auth/v1/login/implicit";

  // Request information
  static HttpMethod method = HttpMethod.GET;
  static String uri = "https://api.npe.rms.com/mi/api/v1/policies/7743/?datasource=Test_US_1M";
  static String body = ""; // paste in json string if applicable

  public static void main(String[] args) {
    AuthClient authClient = new AuthClient(username, password, tenantName, url);
    (new RequestExecutor(authClient)).execute(threads, uri, method, body);
  }
}
