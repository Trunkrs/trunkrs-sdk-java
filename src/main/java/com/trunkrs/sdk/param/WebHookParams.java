package com.trunkrs.sdk.param;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.enumeration.EventType;

import lombok.Builder;
import lombok.val;

@Builder
public abstract class WebHookParams {
  public static class WebHookParamsBuilder {
    private String callbackUrl;
    private String headerName;
    private String sessionToken;
    private EventType eventType;

    private APIV1WebHookParams buildV1() {
      val params = new APIV1WebHookParams();
      params.callbackUrl = callbackUrl;
      params.sessionHeaderName = headerName;
      params.sessionToken = sessionToken;
      params.onCreation = eventType == EventType.onCreation;
      params.onLabelReady = false;
      params.onStatusUpdate = eventType == EventType.onStateUpdate;
      params.onCancellation = eventType == EventType.onCancellation;

      return params;
    }

    /**
     * Set the callback URL which will be notified when the event is fired.
     * @param url The callback URL.
     * @return The builder.
     */
    public WebHookParamsBuilder callbackUrl(String url) {
      callbackUrl = url;
      return this;
    }

    /**
     * Set the name of the header which will communicate the pre-defined session token.
     * @param name The name of the header to communicate the session token.
     * @return The builder.
     */
    public WebHookParamsBuilder headerName(String name) {
      headerName = name;
      return this;
    }

    /**
     * Set the session token to communicate when the web hook is notified.
     * @param key The session token to communicate.
     * @return The builder.
     */
    public WebHookParamsBuilder sessionToken(String key) {
      sessionToken = key;
      return this;
    }

    /**
     * The event on which to notify the configured web hook.
     * @param event The type of the event on which to notify the web hook.
     * @return The builder.
     */
    public WebHookParamsBuilder event(EventType event) {
      eventType = event;
      return this;
    }

    /**
     * Build the parameters and construct a new instance.
     * @return The parameters.
     */
    public WebHookParams build() {
      switch (TrunkrsSDK.getApiVersion()) {
        case v1:
          return buildV1();
        case v2:
          return null;
      }

      return null;
    }
  }
}

class APIV1WebHookParams extends WebHookParams {
  @SerializedName("url")
  String callbackUrl;

  @SerializedName("key")
  String sessionHeaderName;

  @SerializedName("token")
  String sessionToken;

  @SerializedName("uponShipmentCreation")
  boolean onCreation;

  @SerializedName("uponLabelReady")
  boolean onLabelReady;

  @SerializedName("uponStatusUpdate")
  boolean onStatusUpdate;

  @SerializedName("uponShipmentCancellation")
  boolean onCancellation;
}
