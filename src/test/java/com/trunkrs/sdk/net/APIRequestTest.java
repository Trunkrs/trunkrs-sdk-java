package com.trunkrs.sdk.net;

import static org.assertj.core.api.Assertions.*;

import com.trunkrs.sdk.param.WebHookParams;
import com.trunkrs.sdk.testing.mocks.Mocks;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ApiRequest")
public class APIRequestTest {
  @Test
  @DisplayName("Should have content-type header when body present")
  public void addsContentTypeHeader() {
    val subject = ApiRequest.builder().body(WebHookParams.builder().build()).build();

    assertThat(subject.getHeaders()).containsKey("Content-Type");
  }

  @Test
  @DisplayName("Should create a full url w/o query string")
  public void createsAUrlWOQuery() {
    val url = Mocks.faker.internet().url();

    val subject = ApiRequest.builder().url(url).build();

    assertThat(subject.getUrl()).isEqualTo(url);
  }

  @Test
  @DisplayName("Should create a full url with query string")
  public void createsWithQuery() {
    val url = Mocks.faker.internet().url();
    val params = Parameters.builder().with("foo", "bar").build();
    val expected = String.format("%s?foo=bar", url);

    val subject = ApiRequest.builder().url(url).params(params).build();

    assertThat(subject.getUrl()).isEqualTo(expected);
  }

  @Test
  @DisplayName("Should append to existing query string")
  public void appendsToURLWithQuery() {
    val url = String.format("%s?baz=200", Mocks.faker.internet().url());
    val params = Parameters.builder().with("foo", "bar").build();
    val expected = String.format("%s&foo=bar", url);

    val subject = ApiRequest.builder().url(url).params(params).build();

    assertThat(subject.getUrl()).isEqualTo(expected);
  }
}
