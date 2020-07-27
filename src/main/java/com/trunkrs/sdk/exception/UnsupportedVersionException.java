package com.trunkrs.sdk.exception;

import com.trunkrs.sdk.enumeration.APIVersion;

import java.util.ArrayList;

/**
 * Thrown when an invalid API version has been selected.
 */
@SuppressWarnings("serial")
public class UnsupportedVersionException extends Exception {
  public UnsupportedVersionException(String requested, ArrayList<APIVersion> supported) {
    super(
      String.format(
        "You requested an invalid API version: '%s'. Supported API versions are: %s.",
        requested,
        String.join(", ", (String[]) supported.stream().map(Enum<APIVersion>::toString).toArray())
      )
    );
  }

  public UnsupportedVersionException() {
    super("This method has not yet been implemented for the current version of the API. Please open a new issue on Github.");
  }
}
