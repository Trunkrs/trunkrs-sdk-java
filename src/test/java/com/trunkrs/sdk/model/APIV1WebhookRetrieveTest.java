package com.trunkrs.sdk.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.trunkrs.sdk.enumeration.EventType;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("V1 - WebHook.retrieve")
public class APIV1WebhookRetrieveTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should make a call to the right endpoint")
  public void makesCallToRightEndpoint() throws NotAuthorizedException, GeneralApiException {
    mockResponseCallback(
        request -> {
          assertThat(request.getUrl()).endsWith("webhooks");

          return ApiResponse.builder().status(200).body(getJsonFixture("web_hooks.json")).build();
        });

    WebHook.retrieve();
  }

  @Test
  @DisplayName("Should make a GET request")
  public void makesAGETRequest() throws NotAuthorizedException, GeneralApiException {
    mockResponseCallback(
        request -> {
          assertThat(request.getMethod()).isEqualTo(ApiResource.HTTPMethod.GET);

          return ApiResponse.builder().status(200).body(getJsonFixture("web_hooks.json")).build();
        });

    WebHook.retrieve();
  }

  @Test
  @DisplayName("Should parse the right information")
  public void parsesTheRightInfo() throws NotAuthorizedException, GeneralApiException {
    mockResponse(200, getJsonFixture("web_hooks.json"));

    val results = WebHook.retrieve();

    assertThat(results).isInstanceOf(List.class);
    assertThat(results.size()).isEqualTo(2);

    val webHook1 = results.get(0);
    val webHook2 = results.get(1);
    assertThat(webHook1.getId()).isEqualTo(1337);
    assertThat(webHook1.getSessionHeaderName()).isEqualTo("X-Funky-Business");
    assertThat(webHook1.getEventType()).isEqualTo(EventType.onStateUpdate);
    assertThat(webHook2.getEventType()).isEqualTo(EventType.onCancellation);
  }
}
