package com.trunkrs.sdk.model;

import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ShipmentNotFoundException;
import com.trunkrs.sdk.model.enumeration.StateCode;
import com.trunkrs.sdk.model.enumeration.StateReasonCode;
import com.trunkrs.sdk.net.ApiResource;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.Date;

public abstract class ShipmentState extends ApiResource {
  /**
   * Get the current state for the specified shipment id.
   * @param shipmentId The shipment id to fetch state for.
   * @return The shipment state.
   * @throws ShipmentNotFoundException Thrown when the specified shipment couldn't be found.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static ShipmentState find(int shipmentId)
    throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    try {
      return get(String.format("state/%d", shipmentId), APIV1ShipmentState.class);
    } catch (GeneralApiException apiException) {
      if (apiException.getStatusCode() == 404) {
        throw new ShipmentNotFoundException(shipmentId);
      }
      throw apiException;
    }
  }

  /**
   * Gets the shipment id to which the state details belong.
   * @deprecated The shipment id will be removed in favor of the Trunkrs number.
   * @return
   */
  public abstract int getShipmentId();

  /**
   * Gets the current owner of the package.
   * @return The shipments current package owner.
   */
  public abstract PackageOwner getPackageOwner();

  /**
   * Gets the current state code of the shipment.
   * @return The state code of the shipment.
   */
  public abstract StateCode getStateCode();

  /**
   * Get the reason code for the current state, if not available will return {@literal null} instead.
   * @return The reason code of the current state.
   */
  public abstract StateReasonCode getReasonCode();

  /**
   * The timestamp of when the current state was set.
   * @return The timestamp.
   */
  public abstract Date getTimestamp();
}

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
    return Enum.valueOf(StateCode.class, state.code);
  }

  @Override
  public StateReasonCode getReasonCode() {
    if (state.reasonCode == null || state.reasonCode.isEmpty()) {
      return null;
    }
    return Enum.valueOf(StateReasonCode.class, state.reasonCode);
  }
}
