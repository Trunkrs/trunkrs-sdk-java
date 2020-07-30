package com.trunkrs.sdk.model;

import static org.assertj.core.api.Assertions.*;

import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("V1 - TimeSlot.retrieve")
public class APIV1TimeSlotRetrieveTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should call the right endpoint")
  public void callsTheRightEndpoint() throws NotAuthorizedException, GeneralApiException {
    val countryCode = Mocks.faker.country().countryCode2();
    mockResponseCallback(
        request -> {
          assertThat(request.getUrl()).endsWith(String.format("timeslots?country=%s", countryCode));

          return ApiResponse.builder().status(200).body(getJsonFixture("time_slots.json")).build();
        });

    TimeSlot.retrieve(countryCode);
  }

  @Test
  @DisplayName("Should make a GET request")
  public void makesAGetRequest() throws NotAuthorizedException, GeneralApiException {
    mockResponseCallback(
        request -> {
          assertThat(request.getMethod()).isEqualTo(ApiResource.HTTPMethod.GET);

          return ApiResponse.builder().status(200).body(getJsonFixture("time_slots.json")).build();
        });

    TimeSlot.retrieve();
  }

  @Test
  @DisplayName("Should parse the right information")
  public void parsesRightInfo() throws NotAuthorizedException, GeneralApiException {
    mockResponse(200, getJsonFixture("time_slots.json"));

    val timeSlots = TimeSlot.retrieve();

    assertThat(timeSlots).isInstanceOf(List.class);
    assertThat(timeSlots.size()).isEqualTo(1);

    val timeSlot = timeSlots.get(0);
    assertThat(timeSlot.getId()).isEqualTo(1001);
    assertThat(timeSlot.getCutOff())
        .withDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .isEqualTo("2020-07-29T10:00:00+00:00");

    assertThat(timeSlot.getCollectionWindow().getOpen())
        .withDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .isEqualTo("2020-07-29T10:00:00+00:00");
    assertThat(timeSlot.getCollectionWindow().getClose())
        .withDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .isEqualTo("2020-07-29T11:00:00+00:00");

    assertThat(timeSlot.getDeliveryWindow().getOpen())
        .withDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .isEqualTo("2020-07-29T15:00:00+00:00");
    assertThat(timeSlot.getDeliveryWindow().getClose())
        .withDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .isEqualTo("2020-07-29T20:00:00+00:00");
  }
}
