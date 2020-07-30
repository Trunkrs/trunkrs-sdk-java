package com.trunkrs.sdk.model;

import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.exception.*;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.Parameters;
import com.trunkrs.sdk.param.ShipmentParams;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import lombok.val;

public abstract class Shipment extends ApiResource {
  /**
   * Creates a new shipment based on the specified parameters.
   *
   * @param params The shipment parameters.
   * @return The newly created shipments. In case of a multiple parcels this will return multiple
   *     shipment models.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws ServerValidationException Thrown when the shipment param don't match the expectation of
   *     Trunkrs.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  @SneakyThrows(UnsupportedVersionException.class)
  public static List<Shipment> create(ShipmentParams params)
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    Shipment[] shipments = null;

    switch (TrunkrsSDK.getApiVersion()) {
      case v1:
        shipments = post("shipments", params, APIV1Shipment[].class);
        break;
      case v2:
        // Stub for V2, can never be called at this moment.
        throw new UnsupportedVersionException();
    }

    return Arrays.asList(shipments);
  }

  /**
   * Gets a shingle shipment by its id.
   *
   * @param id The shipment identifier.
   * @return The shipment details.
   * @throws ShipmentNotFoundException Thrown when the specified shipment couldn't be found.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static Shipment find(int id)
      throws NotAuthorizedException, ShipmentNotFoundException, GeneralApiException {
    try {
      return get(String.format("shipments/%d", id), APIV1Shipment.class);
    } catch (GeneralApiException apiException) {
      if (apiException.getStatusCode() == 404) {
        throw new ShipmentNotFoundException(id);
      }

      throw apiException;
    }
  }

  /**
   * Retrieves all your shipments in a paginated fashion.
   *
   * @param page The page to retrieve.
   * @return The shipments of the requested page.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  @SneakyThrows(UnsupportedVersionException.class)
  public static List<Shipment> retrieve(int page)
      throws NotAuthorizedException, GeneralApiException {
    val params = Parameters.builder().with("page", page).build();

    Shipment[] shipments = null;
    switch (TrunkrsSDK.getApiVersion()) {
      case v1:
        shipments = get("shipments", params, APIV1Shipment[].class);
        break;
      case v2:
        // Stub for V2, can never be called at this moment.
        throw new UnsupportedVersionException();
    }

    return Arrays.asList(shipments);
  }

  /**
   * Cancels the shipment by its shipment id.
   *
   * @param id The identifier of the shipment to cancel.
   * @throws ShipmentNotFoundException Thrown when the specified shipment couldn't be found.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static void cancel(int id)
      throws NotAuthorizedException, ShipmentNotFoundException, GeneralApiException {
    try {
      delete(String.format("shipments/%d", id));
    } catch (GeneralApiException apiException) {
      if (apiException.getStatusCode() == 404) {
        throw new ShipmentNotFoundException(id);
      }

      throw apiException;
    }
  }

  /**
   * Get the shipment id of the shipment.
   *
   * @return The shipment id.
   */
  public abstract int getId();

  /**
   * Get the trunks number for the shipment.
   *
   * @return The Trunkrs number.
   */
  public abstract String getTrunkrsNr();

  /**
   * Get the delivery address of the shipment.
   *
   * @return The delivery address.
   */
  public abstract Address getDeliveryAddress();

  /**
   * Get the pickup address of the shipment.s
   *
   * @return The pickup address.
   */
  public abstract Address getPickupAddress();

  /**
   * Get the timeslot for this shipment.
   *
   * @return The timeslot for the shipment.
   */
  public abstract TimeSlot getTimeslot();

  /**
   * Cancels the shipment.
   *
   * @throws ShipmentNotFoundException Thrown when the shipment couldn't be found.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public abstract void cancel()
      throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException;

  /**
   * Gets the current state of the shipment.
   *
   * @return The state details of the shipment.
   * @throws ShipmentNotFoundException Thrown when the shipment couldn't be found.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public abstract ShipmentState getState()
      throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException;

  /**
   * Creates the tracking URL for the shipment.
   *
   * @return The tracking url for the shipment.
   */
  public String getTrackingUrl() {
    return String.format(
        "%s/%s/%s",
        TrunkrsSDK.getTrackingBaseUrl(),
        getTrunkrsNr(),
        getDeliveryAddress().getPostal().replace(" ", ""));
  }
}
