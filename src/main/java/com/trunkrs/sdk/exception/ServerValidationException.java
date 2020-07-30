package com.trunkrs.sdk.exception;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.net.ApiResponse;
import lombok.Getter;
import lombok.val;

@SuppressWarnings("serial")
public class ServerValidationException extends Exception {
  @Getter
  private static class ServerValidationModel {
    @SerializedName("message")
    String message;
  }

  private final String validationMessage;

  public ServerValidationException(ApiResponse response) {
    val messageWrapper = response.getModelBody(ServerValidationModel.class);
    validationMessage = messageWrapper.getMessage();
  }

  @Override
  public String getMessage() {
    return String.format(
        "our payload did not match the expectation of the Trunkrs API\n\nValidation message: %s",
        validationMessage);
  }

  /**
   * Gets the underlying validation message.
   *
   * @return The validation message returned by the API.
   */
  public String getValidationMessage() {
    return validationMessage;
  }
}
