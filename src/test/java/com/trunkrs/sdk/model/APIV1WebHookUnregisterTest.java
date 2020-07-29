package com.trunkrs.sdk.model;

import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.WebHookNotFoundException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class APIV1WebHookUnregisterTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should make a call to the right endpoint")
  public void makesRightCallToEndpoint()
    throws NotAuthorizedException, GeneralApiException, WebHookNotFoundException {
    val webHookId = Mocks.faker.random().nextInt(4000, 9999999);
    mockResponseCallback(request -> {
      assertThat(request.getUrl())
        .endsWith(String.format("webhooks/%d", webHookId));

        return ApiResponse.builder()
          .status(204)
          .build();
    });

    WebHook.unregister(webHookId);
  }

  @Test
  @DisplayName("Should make a DELETE request")
  public void makesADELETERequest()
    throws NotAuthorizedException, GeneralApiException, WebHookNotFoundException {
    mockResponseCallback(request -> {
      assertThat(request.getMethod())
        .isEqualTo(ApiResource.HTTPMethod.DELETE);

      return ApiResponse.builder()
        .status(204)
        .build();
    });

    WebHook.unregister(100);
  }

  @Test
  @DisplayName("Should throw not found exceptions when not found")
  public void throwsWhenNotFound() {
    mockResponse(404);

    assertThatThrownBy(() -> WebHook.unregister(100))
      .isInstanceOf(WebHookNotFoundException.class);
  }
}
