package com.trunkrs.sdk.model;

import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ShipmentNotFoundException;
import com.trunkrs.sdk.testing.mocks.Mocks;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("V1 - Shipment.find")
public class APIV1ShipmentFindTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should make a request to right url")
  public void usesRightUrl()
    throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    val shipmentId = Mocks.faker.random().nextInt(40000, 9999999);
    mockResponseCallback(request -> {
      assertThat(request.getUrl())
        .endsWith(String.format("shipments/%d", shipmentId));

      return ApiResponse.builder()
        .status(200)
        .body(getJsonFixture("shipment.json"))
        .build();
    });

    Shipment.find(shipmentId);
  }

  @Test
  @DisplayName("Should make a GET request")
  public void makesAGETRequest()
    throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    mockResponseCallback(request -> {
      assertThat(request.getMethod())
        .isEqualTo(ApiResource.HTTPMethod.GET);

      return ApiResponse.builder()
        .status(200)
        .body(getJsonFixture("shipment.json"))
        .build();
    });

    Shipment.find(100);
  }

  @Test
  @DisplayName("Should return the right information")
  public void returnsRightData()
    throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    mockResponse(200, getJsonFixture("shipment.json"));

    val result = Shipment.find(100);

    assertThat(result.getId())
      .isEqualTo(100);
    assertThat(result.getTrunkrsNr())
      .isEqualTo("400026435");

    assertThat(result.getPickupAddress().getContactName())
      .isEqualTo("Freddy's Fresh Fish shop");
    assertThat(result.getPickupAddress().getAddressLine())
      .isEqualTo("Fishlane 17");
    assertThat(result.getPickupAddress().getPostal())
      .isEqualTo("1000AB");
    assertThat(result.getPickupAddress().getCity())
      .isEqualTo("Amsterdam");
    assertThat(result.getPickupAddress().getCountryCode())
      .isEqualTo("NL");
    assertThat(result.getPickupAddress().getEmail())
      .isEqualTo("fish@fredys.nl");
    assertThat(result.getPickupAddress().getPhoneNumber())
      .isEqualTo("020-freddys");
    assertThat(result.getPickupAddress().getRemarks())
      .isEqualTo("Go around the back!");

    assertThat(result.getDeliveryAddress().getContactName())
      .isEqualTo("The Dude");
    assertThat(result.getDeliveryAddress().getAddressLine())
      .isEqualTo("Bowling alley 6");
    assertThat(result.getDeliveryAddress().getPostal())
      .isEqualTo("3454AB");
    assertThat(result.getDeliveryAddress().getCity())
      .isEqualTo("Utrecht");
    assertThat(result.getDeliveryAddress().getCountryCode())
      .isEqualTo("NL");
    assertThat(result.getDeliveryAddress().getEmail())
      .isEqualTo("strike@dude.com");
    assertThat(result.getDeliveryAddress().getPhoneNumber())
      .isEqualTo("030-dude");
    assertThat(result.getDeliveryAddress().getRemarks())
      .isEqualTo("DND when throwing ball!");

    assertThat(result.getTimeslot())
      .isInstanceOf(APIV1TimeSlot.class);
  }

  @Test
  @DisplayName("Should throw not found exception when shipment not found")
  public void throwsWhenNotFound() {
    mockResponse(404);

    assertThatThrownBy(() -> Shipment.find(100))
      .isInstanceOf(ShipmentNotFoundException.class);
  }
}
