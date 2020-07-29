package com.trunkrs.sdk;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("APICredentials")
public class APICredentialsTest {
  @Test
  @DisplayName("Should create V1 authentication headers")
  public void createsV1Headers() {
    val clientId = UUID.randomUUID().toString();
    val clientSecret = UUID.randomUUID().toString();

    val subject = APICredentials.from(clientId, clientSecret);

    assertThat(subject.getAuthHeaders())
      .containsEntry("X-API-ClientId", clientId)
      .containsEntry("X-API-ClientSecret", clientSecret);
  }

  @Test
  @DisplayName("Should create V2 authentication headers")
  public void createsV2Headers() {
    val apiKey = UUID.randomUUID().toString();

    val subject = APICredentials.from(apiKey);

    assertThat(subject.getAuthHeaders())
      .containsEntry("X-API-Key", apiKey);
  }
}
