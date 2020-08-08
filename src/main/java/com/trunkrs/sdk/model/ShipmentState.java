package com.trunkrs.sdk.model;

import com.trunkrs.sdk.enumeration.StateCode;
import com.trunkrs.sdk.enumeration.StateReasonCode;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ShipmentNotFoundException;
import com.trunkrs.sdk.net.ApiResource;
import java.util.Date;
import java.util.EnumSet;

public abstract class ShipmentState extends ApiResource {
  protected static final EnumSet<StateCode> stateCodes = EnumSet.allOf(StateCode.class);
  protected static final EnumSet<StateReasonCode> stateReasonCodes =
      EnumSet.allOf(StateReasonCode.class);

  /**
   * Get the current state for the specified shipment id.
   *
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
   *
   * @return The shipment identifier.
   */
  public abstract int getShipmentId();

  /**
   * Gets the current owner of the package.
   *
   * @return The shipments current package owner.
   */
  public abstract PackageOwner getPackageOwner();

  /**
   * Gets the current state code of the shipment.
   *
   * @return The state code of the shipment.
   */
  public abstract StateCode getStateCode();

  /**
   * Get the reason code for the current state, if not available will return {@literal null}
   * instead.
   *
   * @return The reason code of the current state.
   */
  public abstract StateReasonCode getReasonCode();

  /**
   * The timestamp of when the current state was set.
   *
   * @return The timestamp.
   */
  public abstract Date getTimestamp();
}
