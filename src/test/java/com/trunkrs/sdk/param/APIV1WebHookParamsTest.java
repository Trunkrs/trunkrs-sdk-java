package com.trunkrs.sdk.param;

import static org.assertj.core.api.Assertions.assertThat;

import com.trunkrs.sdk.enumeration.EventType;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("V1 - WebHookParams")
public class APIV1WebHookParamsTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should be V1 instance")
  public void createsV1Instance() {
    val params = WebHookParams.builder().build();

    assertThat(params).isInstanceOf(APIV1WebHookParams.class);
  }

  @Test
  @DisplayName("Should set web hook information")
  public void setsWebHookInfo() {
    val callbackURL = Mocks.faker.internet().url();
    val headerName = Mocks.faker.internet().slug();
    val sessionToken = Mocks.faker.crypto().sha256();

    val params =
        WebHookParams.builder()
            .callbackUrl(callbackURL)
            .headerName(headerName)
            .sessionToken(sessionToken)
            .build();

    assertThat(params)
        .hasFieldOrPropertyWithValue("callbackUrl", callbackURL)
        .hasFieldOrPropertyWithValue("sessionHeaderName", headerName)
        .hasFieldOrPropertyWithValue("sessionToken", sessionToken);
  }

  @Test
  @DisplayName("Should apply on creation event")
  public void appliesOnCreationEvent() {
    val params = WebHookParams.builder().event(EventType.onCreation).build();

    assertThat(params).hasFieldOrPropertyWithValue("onCreation", true);
  }

  @Test
  @DisplayName("Should apply on state update event")
  public void appliesOnStateUpdateEvent() {
    val params = WebHookParams.builder().event(EventType.onStateUpdate).build();

    assertThat(params).hasFieldOrPropertyWithValue("onStatusUpdate", true);
  }

  @Test
  @DisplayName("Should apply on cancellation event")
  public void appliesOnCancellationEvent() {
    val params = WebHookParams.builder().event(EventType.onCancellation).build();

    assertThat(params).hasFieldOrPropertyWithValue("onCancellation", true);
  }
}
