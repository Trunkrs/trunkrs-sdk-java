package com.trunkrs.sdk.exception;

import com.trunkrs.sdk.net.ApiResponse;

@SuppressWarnings("serial")
public class GeneralApiException extends Exception {
  private ApiResponse response = null;

  public GeneralApiException(ApiResponse response) {
    super(
      String.format(
        "The Trunkrs API responded with an unexpected answer of: %d",
        response.getStatus()
      )
    );

    this.response = response;
  }

  public GeneralApiException() {
    super("The connection to the Trunkrs API was impossible. Please check you connection settings.");
  }

  public int getStatusCode() {
    if (response == null) {
      return -1;
    }
    return response.getStatus();
  }
}
