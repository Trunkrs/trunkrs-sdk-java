package com.trunkrs.sdk;

import com.trunkrs.sdk.enumeration.APIVersion;
import com.trunkrs.sdk.exception.UnsupportedVersionException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class TrunkrsSDK {
  private static final ArrayList<APIVersion> supportedVersions =
      new ArrayList<>(Arrays.asList(APIVersion.v1));
  private static volatile APIVersion apiVersion = APIVersion.v1;

  private static volatile APICredentials credentials;

  private static volatile String baseUrl = "https://api.trunkrs.nl/api";
  private static volatile String trackingBaseUrl = "https://parcel.trunkrs.nl";

  private static final String sdkVersion = "1.0.0";

  /**
   * Gets the base url for the currently selected target.
   *
   * @return The base URL for the currently selected target,
   */
  public static String getBaseUrl() {
    return baseUrl;
  }

  /**
   * Gets the tracking base url for the currently selected target.
   *
   * @return The tracking base url for
   */
  public static String getTrackingBaseUrl() {
    return trackingBaseUrl;
  }

  /**
   * Gets the current SDK version.
   *
   * @return The current SDK version
   */
  public static String getSdkVersion() {
    return sdkVersion;
  }

  /**
   * Gets the URL safe version identifier of the selected version.
   *
   * @return The URL safe version identifier.
   */
  public static String getVersionIdentifier() {
    return apiVersion.getVersionIdentifier();
  }

  /**
   * Returns the currently targeted API version.
   *
   * @return The currently targeted version identifier.
   */
  public static APIVersion getApiVersion() {
    return apiVersion;
  }

  /**
   * Sets the API version target. Currently only v1 is supported.
   *
   * @param version The version of the API to target.
   * @throws UnsupportedVersionException When a target that is not supported is requested.
   */
  public static void setApiVersion(APIVersion version) throws UnsupportedVersionException {
    if (!supportedVersions.contains(version)) {
      throw new UnsupportedVersionException(apiVersion.getVersionIdentifier(), supportedVersions);
    }

    apiVersion = version;
  }

  /**
   * Gets the currently set credentials for internal use.s
   *
   * @return The currently set API credentials.
   */
  public static APICredentials getCredentials() {
    return credentials;
  }

  /**
   * Sets the credentials that will be used in subsequent requests.
   *
   * @param credentials The credentials to be used.
   */
  public static void setCredentials(APICredentials credentials) {
    TrunkrsSDK.credentials = credentials;
  }

  /** Switches the SDK to the staging environment. */
  public static void useStaging() {
    baseUrl = "https://staging-api.trunkrs.nl/api";
    trackingBaseUrl = "https://staging-parcel.trunkrs.nl";
  }

  /** Switches the SDK to the production environment. This environment is the default setting. */
  public static void useProduction() {
    baseUrl = "https://api.trunkrs.nl/api";
    trackingBaseUrl = "https://parcel.trunkrs.nl";
  }
}
