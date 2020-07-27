package com.trunkrs.sdk.model;

import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.UnsupportedVersionException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.Parameters;

import lombok.SneakyThrows;
import lombok.val;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class TimeSlot extends ApiResource {
  /**
   * Gets all the available time slots for the Netherlands.
   * @return A list of available time slots in the Netherlands.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static List<TimeSlot> retrieve()
    throws NotAuthorizedException, GeneralApiException {
    return retrieve("NL");
  }

  /**
   * Gets all the available time slots for the a delivery in a country.
   * @param countryCode The ISO 3166 Alpha-2 country code, e.g. NL for the Netherlands.
   * @return A list of available time slots.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  @SneakyThrows(UnsupportedVersionException.class)
  public static List<TimeSlot> retrieve(String countryCode)
    throws NotAuthorizedException, GeneralApiException {
    val params = Parameters.builder()
      .with("country", countryCode)
      .build();

    TimeSlot[] timeslots = null;
    switch (TrunkrsSDK.getApiVersion()) {
      case v1:
        timeslots = get("timeslots", params, APIV1TimeSlot[].class);
        break;
      case v2:
        // Stub for V2, can never be called at this moment.
        throw new UnsupportedVersionException();
    }

    return Arrays.asList(timeslots);
  }

  /**
   * Get the identifier of the timeslot.
   * @return The identifier of the time slot.
   */
  public abstract int getId();

  /**
   * Get the cut-off moment for the time slot.
   * @return The cut-off moment.
   */
  public abstract Date getCutOff();

  /**
   * Get the delivery window for the time slot.
   * @return Get the delivery window for the time slot.
   */
  public abstract TimeWindow getDeliveryWindow();

  /**
   * Get the collection window for the time slot.
   * @return The collection window for the time slot.
   */
  public abstract TimeWindow getCollectionWindow();
}
