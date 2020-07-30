package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.enumeration.StateCode;
import com.trunkrs.sdk.enumeration.StateReasonCode;
import java.util.Date;
import lombok.Getter;

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
    return stateCodes.stream()
        .filter(stateCode -> stateCode.getCode().equals(state.code))
        .findAny()
        .orElse(null);
  }

  @Override
  public StateReasonCode getReasonCode() {
    return stateReasonCodes.stream()
        .filter(stateReasonCode -> stateReasonCode.getCode().equals(state.reasonCode))
        .findAny()
        .orElse(null);
  }
}
