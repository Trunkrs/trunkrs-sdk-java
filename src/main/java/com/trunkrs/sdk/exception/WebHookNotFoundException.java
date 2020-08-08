package com.trunkrs.sdk.exception;

public class WebHookNotFoundException extends Exception {
  public WebHookNotFoundException(int webHookId) {
    super(String.format("The web hook with id '%d' couldn't be found.", webHookId));
  }
}
