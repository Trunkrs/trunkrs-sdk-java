package com.trunkrs.sdk.model;

import com.trunkrs.sdk.enumeration.EventType;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

class APIV1WebHook extends WebHook {
  @Getter
  @SerializedName("id")
  int id;

  @Getter
  @SerializedName("url")
  String callbackUrl;

  @Getter
  @SerializedName("key")
  String sessionHeaderName;

  @SerializedName("uponShipmentCreation")
  boolean onShipmentCreation;

  @SerializedName("uponStatusUpdate")
  boolean onStatusUpdate;

  @SerializedName("uponShipmentCancellation")
  boolean onShipmentCancellation;

  @Override
  public EventType getEventType() {
    if (onShipmentCreation) {
      return EventType.onCreation;
    }
    if (onStatusUpdate) {
      return EventType.onStateUpdate;
    }
    if (onShipmentCancellation) {
      return EventType.onCancellation;
    }

    return null;
  }
}
