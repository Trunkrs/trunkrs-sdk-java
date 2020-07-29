package com.trunkrs.sdk.net;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ApiResponse")
public class APIResponseTest {
  static class TestPayload {
    String foo;
  }

  @ParameterizedTest(name = "Reflect status {0} as successful: {1}")
  @CsvSource({
    "200, true",
    "201, true",
    "202, true",
    "204, true",
    "400, false",
    "422, false",
    "500, false"
  })
  @DisplayName("Should reflect the status code as successful or not")
  public void reflectsSuccessful(int statusCode, boolean isSuccess) {
    val subject = ApiResponse.builder()
      .status(statusCode)
      .build();

    assertThat(subject.isSuccessful()).isEqualTo(isSuccess);
  }

  @Test
  @DisplayName("Should deserialize a JSON body")
  public void deserializesBody() {
    val json = "{\"foo\":\"bar\"}";
    val subject = ApiResponse.builder()
      .body(json)
      .build();

    val model = subject.getModelBody(TestPayload.class);

    assertThat(model.foo)
      .isEqualTo("bar");
  }
}
