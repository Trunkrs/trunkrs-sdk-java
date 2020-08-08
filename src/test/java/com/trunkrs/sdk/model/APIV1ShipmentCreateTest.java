package com.trunkrs.sdk.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ServerValidationException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("V1 - Shipment.create")
public class APIV1ShipmentCreateTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should make a POST request")
  public void makesPostRequest()
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    mockResponseCallback(
        request -> {
          assertThat(request).hasFieldOrPropertyWithValue("method", ApiResource.HTTPMethod.POST);

          return ApiResponse.builder().status(200).body(getJsonFixture("shipments.json")).build();
        });

    Shipment.create(Mocks.getFakeShipment());
  }

  @Test
  @DisplayName("Should make a request to right URL")
  public void makesTheRightRequestUrl()
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    mockResponseCallback(
        request -> {
          assertThat(request.getUrl()).endsWith("shipments");

          return ApiResponse.builder().status(200).body(getJsonFixture("shipments.json")).build();
        });

    Shipment.create(Mocks.getFakeShipment());
  }

  @Test
  @DisplayName("Should return a list of shipments")
  public void returnsList()
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    mockResponse(200, getJsonFixture("shipments.json"));

    val result = Shipment.create(Mocks.getFakeShipment());

    assertThat(result).isInstanceOf(List.class).hasSize(1);
  }
}
