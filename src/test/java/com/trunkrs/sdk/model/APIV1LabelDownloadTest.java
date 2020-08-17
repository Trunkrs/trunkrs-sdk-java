package com.trunkrs.sdk.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.trunkrs.sdk.enumeration.LabelType;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("V1 - Label Download")
public class APIV1LabelDownloadTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should create the right PDF URL")
  public void addsTokenToPDFUrl() throws NotAuthorizedException, GeneralApiException {
    val trunrksNr = "400029422";
    val postalCode = "1722 GM";
    val token = "7a1c43a00f5e5feb942c9f2f2298866a";
    mockResponseCallback(
        apiRequest -> {
          assertThat(apiRequest.getUrl())
              .contains(String.format("/%s/", token))
              .doesNotEndWith("zpl");

          return ApiResponse.builder().status(200).build();
        });

    Label.download(trunrksNr, postalCode, LabelType.PDF);
  }

  @Test
  @DisplayName("Should create the right ZPL URL")
  public void addsTokenToZPLUrl() throws NotAuthorizedException, GeneralApiException {
    val trunrksNr = "400029422";
    val postalCode = "1722 GM";
    val token = "7a1c43a00f5e5feb942c9f2f2298866a";
    mockResponseCallback(
        apiRequest -> {
          assertThat(apiRequest.getUrl()).contains(String.format("/%s/", token)).endsWith("zpl");

          return ApiResponse.builder().status(200).build();
        });

    Label.download(trunrksNr, postalCode, LabelType.ZPL);
  }

  @Test
  @DisplayName("Should make a GET request when requesting PDF label")
  public void makesGETRequestToPDF() throws NotAuthorizedException, GeneralApiException {
    val trunkrsNr = Mocks.getFakeTrunkrsNr();
    val postalCode = Mocks.faker.address().zipCode();
    mockResponseCallback(
        apiRequest -> {
          assertThat(apiRequest).hasFieldOrPropertyWithValue("method", ApiResource.HTTPMethod.GET);

          return ApiResponse.builder().status(200).build();
        });

    Label.download(trunkrsNr, postalCode, LabelType.PDF);
  }

  @Test
  @DisplayName("Should make a GET request when requesting ZPL label")
  public void makesGETRequestToZPL() throws NotAuthorizedException, GeneralApiException {
    val trunkrsNr = Mocks.getFakeTrunkrsNr();
    val postalCode = Mocks.faker.address().zipCode();
    mockResponseCallback(
        apiRequest -> {
          assertThat(apiRequest).hasFieldOrPropertyWithValue("method", ApiResource.HTTPMethod.GET);

          return ApiResponse.builder().status(200).build();
        });

    Label.download(trunkrsNr, postalCode, LabelType.ZPL);
  }

  @ParameterizedTest(name = "Should create for {0}")
  @EnumSource(LabelType.class)
  @DisplayName("Should create a label with label data")
  public void returnsValidLabel(LabelType type) throws NotAuthorizedException, GeneralApiException {
    val fakeLabelData = Mocks.faker.beer().name().getBytes();
    val trunkrsNr = Mocks.getFakeTrunkrsNr();
    val postalCode = Mocks.faker.address().zipCode();
    mockResponse(200, fakeLabelData);

    val label = Label.download(trunkrsNr, postalCode, type);

    assertThat(label).isInstanceOf(Label.class).hasFieldOrPropertyWithValue("bytes", fakeLabelData);
  }
}
