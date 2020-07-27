package com.trunkrs.sdk.param;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public abstract class WebhookParams {
  public static class WebhookParamsBuilder {

  }
}

@Builder
class APIV1WebhookParams extends WebhookParams {
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
