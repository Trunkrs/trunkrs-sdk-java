package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ShipmentNotFoundException;
import lombok.Getter;

@Getter
class APIV1Shipment extends Shipment {
  @SerializedName("shipmentId")
  int id;

  @SerializedName("trunkrsNr")
  String trunkrsNr;

  @SerializedName("recipient")
  APIV1Address deliveryAddress;

  @SerializedName("sender")
  APIV1Address pickupAddress;

  @SerializedName("timeSlot")
  APIV1TimeSlot timeslot;

  @Override
  public void cancel()
      throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    Shipment.cancel(getId());
  }

  @Override
  public ShipmentState getState()
      throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    return ShipmentState.find(getId());
  }
}
