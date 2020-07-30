package com.trunkrs.sdk.net.http;

import com.trunkrs.sdk.net.ApiRequest;
import com.trunkrs.sdk.net.ApiResponse;

public interface HttpClient {
  /**
   * Executes the request based on the specified request details. Returns the response details.
   *
   * @param request The request details to executes.
   * @return The response details of the executed request.
   */
  ApiResponse request(ApiRequest request);
}
