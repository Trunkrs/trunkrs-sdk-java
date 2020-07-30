package com.trunkrs.sdk.net;

import static org.assertj.core.api.Assertions.*;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Parameters")
public class ParametersTest {
  @Test
  @DisplayName("Should represent params as query string")
  public void createsQueryString() {
    val city = "Utrecht";

    val subject = Parameters.builder().with("city", city).build();

    assertThat(subject.asQueryString()).isEqualTo("city=%s", city);
  }
}
