package com.trunkrs.sdk.model;

import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.UnsupportedVersionException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.Parameters;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class Timeslot extends ApiResource {
  /**
   * Gets all the available time slots for the Netherlands.
   * @return A list of available time slots in the Netherlands.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static List<Timeslot> retrieve()
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
  public static List<Timeslot> retrieve(String countryCode)
    throws NotAuthorizedException, GeneralApiException {
    val params = Parameters.builder()
      .with("country", countryCode)
      .build();

    Timeslot[] timeslots = null;
    switch (TrunkrsSDK.getApiVersion()) {
      case v1:
        timeslots = get("timeslots", params, APIV1Timeslot[].class);
        break;
      case v2:
        // Stub for V2, can never be called at this moment.
        throw new UnsupportedVersionException();
    }

    return Arrays.asList(timeslots);
  }

  public abstract int getId();
  public abstract Date getCutOff();
  public abstract TimeWindow getDeliveryWindow();
  public abstract TimeWindow getCollectionWindow();
}

@Getter
class APIV1Timeslot extends Timeslot {
  @SerializedName("id")
  int id;

  @SerializedName("dataWindow")
  Date cutOff;

  @SerializedName("deliveryWindow")
  APIV1TimeWindow deliveryWindow;

  @SerializedName("collectionWindow")
  APIV1TimeWindow collectionWindow;
}
