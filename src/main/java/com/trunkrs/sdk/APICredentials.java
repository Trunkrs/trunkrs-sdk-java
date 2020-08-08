package com.trunkrs.sdk;

import java.util.HashMap;
import java.util.Map;
import lombok.val;

public abstract class APICredentials {
  /**
   * Creates API credentials from your client id and client secret used in the V1 API.
   *
   * @param clientId The Trunkrs provided client id.
   * @param clientSecret The Trunkrs provided client secret.
   * @return The credentials usable within the SDK.
   */
  public static APICredentials from(String clientId, String clientSecret) {
    return new APICredentials() {
      @Override
      public Map<String, String> getAuthHeaders() {
        val headers = new HashMap<String, String>();
        headers.put("X-API-ClientId", clientId);
        headers.put("X-API-ClientSecret", clientSecret);

        return headers;
      }
    };
  }

  /**
   * Creates API credentials from your API key used in the V2 API.
   *
   * @param apiKey The Trunkrs provided API key.
   * @return The credentials usable within the SDK.
   */
  public static APICredentials from(String apiKey) {
    return new APICredentials() {
      @Override
      public Map<String, String> getAuthHeaders() {
        val headers = new HashMap<String, String>();
        headers.put("X-API-Key", apiKey);

        return headers;
      }
    };
  }

  /**
   * Returns the authentication headers for the credentials.
   *
   * @return A map containing representing the authentication headers.
   */
  public abstract Map<String, String> getAuthHeaders();
}
