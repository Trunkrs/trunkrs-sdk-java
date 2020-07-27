package com.trunkrs.sdk.model;

import com.trunkrs.sdk.enumeration.StateCode;
import com.trunkrs.sdk.enumeration.StateReasonCode;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.Date;

@Getter
class APIV1ShipmentState extends ShipmentState {
  static class APIV1StateDetails {
    @SerializedName("status")
    String code;

    @SerializedName("reasonCode")
    String reasonCode;
  }

  @SerializedName("shipmentId")
  int shipmentId;

  @SerializedName("stateObj")
  APIV1StateDetails state;

  @SerializedName("currentOwner")
  APIV1PackageOwner packageOwner;

  @SerializedName("timestamp")
  Date timestamp;

  @Override
  public StateCode getStateCode() {
    if (state.code == null || state.code.isEmpty()) {
      return null;
    }

    return stateCodes.stream()
      .filter(stateCode -> stateCode.getCode().equals(state.code))
      .findAny()
      .orElse(null);
  }

  @Override
  public StateReasonCode getReasonCode() {
    if (state.reasonCode == null || state.reasonCode.isEmpty()) {
      return null;
    }

    return stateReasonCodes.stream()
      .filter(stateReasonCode -> stateReasonCode.getCode().equals(state.reasonCode))
      .findAny()
      .orElse(null);
  }
}
