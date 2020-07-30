package com.trunkrs.sdk.model;

import static org.assertj.core.api.Assertions.*;

import com.trunkrs.sdk.enumeration.OwnerType;
import com.trunkrs.sdk.enumeration.StateCode;
import com.trunkrs.sdk.enumeration.StateReasonCode;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ShipmentNotFoundException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("V1 - ShipmentState.find")
public class APIV1ShipmentStateFindTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should make a request to the right endpoint")
  public void makesRequestToEndpoint()
      throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    val shipmentId = 100;
    mockResponseCallback(
        request -> {
          assertThat(request.getUrl()).endsWith(String.format("state/%d", shipmentId));

          return ApiResponse.builder()
              .status(200)
              .body(getJsonFixture("shipment_state.json"))
              .build();
        });

    ShipmentState.find(100);
  }

  @Test
  @DisplayName("Should make a GET request")
  public void makesGETRequest()
      throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    mockResponseCallback(
        request -> {
          assertThat(request.getMethod()).isEqualTo(ApiResource.HTTPMethod.GET);

          return ApiResponse.builder()
              .status(200)
              .body(getJsonFixture("shipment_state.json"))
              .build();
        });

    ShipmentState.find(100);
  }

  @Test
  @DisplayName("Should return the right information")
  public void getsRightInformation()
      throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    mockResponse(200, getJsonFixture("shipment_state.json"));

    val state = ShipmentState.find(100);

    assertThat(state.getShipmentId()).isEqualTo(100);
    assertThat(state.getStateCode()).isEqualTo(StateCode.shipmentNotDelivered);
    assertThat(state.getReasonCode()).isEqualTo(StateReasonCode.parcelMissing);
    assertThat(state.getPackageOwner().getOwnerType()).isEqualTo(OwnerType.trunkrs);
    assertThat(state.getPackageOwner().getName()).isEqualTo("Trunkrs B.V.");
    assertThat(state.getPackageOwner().getAddressLine()).isEqualTo("Liesbosch 90");
    assertThat(state.getPackageOwner().getPostalCode()).isEqualTo("3439 LC");
    assertThat(state.getPackageOwner().getCity()).isEqualTo("Nieuwegein");
    assertThat(state.getPackageOwner().getCountry()).isEqualTo("NL");
  }

  @Test
  @DisplayName("Should throw not found exception when shipment not found")
  public void shouldThrowWhenNotFound() {
    mockResponse(404);

    assertThatThrownBy(() -> Shipment.find(100)).isInstanceOf(ShipmentNotFoundException.class);
  }
}
