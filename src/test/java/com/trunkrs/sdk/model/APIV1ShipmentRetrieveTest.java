package com.trunkrs.sdk.model;

import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("V1 - Shipment.retrieve")
public class APIV1ShipmentRetrieveTest extends APIV1BaseTest {
  @ParameterizedTest(name = "Retrieve page {0} correctly")
  @ValueSource(ints = { 1, 2, 3, 9001 })
  @DisplayName("Should make call to correct resource and page")
  public void requestsRightResources(int expectedPage)
    throws NotAuthorizedException, GeneralApiException {
    mockResponseCallback(request -> {
      assertThat(request.getUrl())
        .endsWith(String.format("shipments?page=%d", expectedPage));

      return ApiResponse.builder()
        .status(200)
        .body(getJsonFixture("shipments.json"))
        .build();
    });

    Shipment.retrieve(expectedPage);
  }

  @Test
  @DisplayName("Should execute a GET request")
  public void executesGetRequest()
    throws NotAuthorizedException, GeneralApiException {
    mockResponseCallback(request -> {
      assertThat(request.getMethod())
        .isEqualTo(ApiResource.HTTPMethod.GET);

      return ApiResponse.builder()
        .status(200)
        .body(getJsonFixture("shipments.json"))
        .build();
    });

    Shipment.retrieve(1);
  }

  @Test
  @DisplayName("Should return a list of shipments")
  public void returnsListOfShipments()
    throws NotAuthorizedException, GeneralApiException {
    mockResponse(200, getJsonFixture("shipments.json"));

    val result = Shipment.retrieve(1);

    assertThat(result)
      .isInstanceOf(List.class);
    assertThat(result.size())
      .isEqualTo(1);
    assertThat(result.get(0))
      .isInstanceOf(APIV1Shipment.class);
  }
}
