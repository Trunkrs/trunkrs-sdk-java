package com.trunkrs.sdk.exception;

public class NotAuthorizedException extends Exception {
  public NotAuthorizedException() {
    super("Your API credentials seem to be incorrect or have expired. Please contact Trunkrs support.");
  }
}
