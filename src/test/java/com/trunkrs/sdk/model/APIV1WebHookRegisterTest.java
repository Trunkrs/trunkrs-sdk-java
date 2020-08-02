package com.trunkrs.sdk.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ServerValidationException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class APIV1WebHookRegisterTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should make a POST request")
  public void makesPostRequest()
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    mockResponseCallback(
        request -> {
          assertThat(request).hasFieldOrPropertyWithValue("method", ApiResource.HTTPMethod.POST);

          return ApiResponse.builder().status(200).body(getJsonFixture("web_hook.json")).build();
        });

    WebHook.register(Mocks.getFakeWebHook());
  }

  @Test
  @DisplayName("Should make a request to right URL")
  public void makesTheRightRequestUrl()
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    mockResponseCallback(
        request -> {
          assertThat(request.getUrl()).endsWith("webhooks");

          return ApiResponse.builder().status(200).body(getJsonFixture("web_hook.json")).build();
        });

    WebHook.register(Mocks.getFakeWebHook());
  }

  @Test
  @DisplayName("Should return a V1 webhook instance")
  public void returnsList()
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    mockResponse(200, getJsonFixture("web_hook.json"));

    val result = WebHook.register(Mocks.getFakeWebHook());

    assertThat(result).isInstanceOf(APIV1WebHook.class);
  }
}
