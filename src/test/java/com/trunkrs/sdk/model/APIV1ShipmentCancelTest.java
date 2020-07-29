package com.trunkrs.sdk.model;

import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ShipmentNotFoundException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;

import com.trunkrs.sdk.testing.mocks.Mocks;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("V1 - Shipment.cancel")
public class APIV1ShipmentCancelTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should make a request to the right endpoint")
  public void callsRightEndpoint()
    throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    val shipmentId = Mocks.faker.random().nextInt(40000, 99999999);
    mockResponseCallback(request -> {
      assertThat(request.getUrl())
        .endsWith(String.format("shipments/%d", shipmentId));

      return ApiResponse.builder()
        .status(204)
        .build();
    });

    Shipment.cancel(shipmentId);
  }

  @Test
  @DisplayName("Should make a DELETE request")
  public void makesDeleteRequest()
    throws NotAuthorizedException, GeneralApiException, ShipmentNotFoundException {
    mockResponseCallback(request -> {
      assertThat(request.getMethod())
        .isEqualTo(ApiResource.HTTPMethod.DELETE);

      return ApiResponse.builder()
        .status(204)
        .build();
    });

    Shipment.cancel(100);
  }

  @Test
  @DisplayName("Should throw not found exception when not found")
  public void throwsShipmentNotFound() {
    mockResponse(404);

    assertThatThrownBy(() -> Shipment.cancel(100))
      .isInstanceOf(ShipmentNotFoundException.class);
  }
}
