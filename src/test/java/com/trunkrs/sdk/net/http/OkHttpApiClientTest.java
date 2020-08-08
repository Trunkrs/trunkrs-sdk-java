package com.trunkrs.sdk.net.http;

import static org.assertj.core.api.Assertions.*;

import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.testing.HttpClientBaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("OkHttpApiClient")
public class OkHttpApiClientTest extends HttpClientBaseTest {
  private static Object[] methodStatusDataSet() {
    final Integer[] statusCodes = {200, 201, 204, 401, 422, 500};

    return EnumSet.allOf(ApiResource.HTTPMethod.class).stream()
        .map(
            method ->
                Stream.of(statusCodes)
                    .map(statusCode -> Arrays.asList(method, statusCode).toArray())
                    .toArray())
        .flatMap(Arrays::stream)
        .toArray();
  }

  private OkHttpApiClient subject;

  @BeforeEach
  public void beforeScenario() {
    subject = new OkHttpApiClient();
  }

  @Test
  @DisplayName("Should make a GET request")
  public void callsGETMethod() throws InterruptedException {
    val request = buildRequest(builder -> builder.method(ApiResource.HTTPMethod.GET));
    mockResponse(200);

    subject.request(request);

    assertThat(mockServer.takeRequest()).hasFieldOrPropertyWithValue("method", "GET");
  }

  @Test
  @DisplayName("Should make a POST request")
  public void callsPOSTMethod() throws InterruptedException {
    val request = buildRequest(builder -> builder.method(ApiResource.HTTPMethod.POST));
    mockResponse(200);

    subject.request(request);

    assertThat(mockServer.takeRequest()).hasFieldOrPropertyWithValue("method", "POST");
  }

  @Test
  @DisplayName("Should make a PUT request")
  public void callsPUTMethod() throws InterruptedException {
    val request = buildRequest(builder -> builder.method(ApiResource.HTTPMethod.PUT));
    mockResponse(200);

    subject.request(request);

    assertThat(mockServer.takeRequest()).hasFieldOrPropertyWithValue("method", "PUT");
  }

  @Test
  @DisplayName("Should make a DELETE request")
  public void callsDELETEMethod() throws InterruptedException {
    val request = buildRequest(builder -> builder.method(ApiResource.HTTPMethod.DELETE));
    mockResponse(200);

    subject.request(request);

    assertThat(mockServer.takeRequest()).hasFieldOrPropertyWithValue("method", "DELETE");
  }

  @Test
  @DisplayName("Should add payload to request when POSTing")
  public void addsPostPayload() throws InterruptedException {
    val payload = TestPayload.builder().foo(Mocks.faker.witcher().character()).build();
    val jsonPayload = String.format("{\"foo\":\"%s\"}", payload.foo);
    val request =
        buildRequest(builder -> builder.method(ApiResource.HTTPMethod.POST).body(payload));
    mockResponse(200);

    subject.request(request);

    assertThat(mockServer.takeRequest().getBody())
        .matches(
            buffer ->
                new String(buffer.readByteArray(), StandardCharsets.UTF_8).equals(jsonPayload),
            "is correct JSON payload");
  }

  @Test
  @DisplayName("Should add payload to request when PUTing")
  public void addsPutPayload() throws InterruptedException {
    val payload = TestPayload.builder().foo(Mocks.faker.overwatch().hero()).build();
    val jsonPayload = String.format("{\"foo\":\"%s\"}", payload.foo);
    val request = buildRequest(builder -> builder.method(ApiResource.HTTPMethod.PUT).body(payload));
    mockResponse(200);

    subject.request(request);

    assertThat(mockServer.takeRequest().getBody())
        .matches(
            buffer ->
                new String(buffer.readByteArray(), StandardCharsets.UTF_8).equals(jsonPayload),
            "is correct JSON payload");
  }

  @DisplayName("Should add headers to a request")
  @ParameterizedTest(name = "Adds to a {0} request")
  @EnumSource(ApiResource.HTTPMethod.class)
  public void addsHeaders(ApiResource.HTTPMethod method) throws InterruptedException {
    val headerName = Mocks.faker.internet().slug();
    val headerValue = Mocks.faker.crypto().sha256();
    val headers = new HashMap<String, String>();
    headers.put(headerName, headerValue);
    val request = buildRequest(builder -> builder.method(method).headers(headers));
    mockResponse(200);

    subject.request(request);

    assertThat(mockServer.takeRequest().getHeader(headerName)).isEqualTo(headerValue);
  }

  @DisplayName("Should return the status code")
  @ParameterizedTest(name = "From a {0} request with {1}")
  @MethodSource("methodStatusDataSet")
  public void returnStatusCode(ApiResource.HTTPMethod method, int statusCode)
      throws InterruptedException {
    val request = buildRequest(builder -> builder.method(method));
    mockResponse(statusCode);

    val response = subject.request(request);

    assertThat(response).hasFieldOrPropertyWithValue("status", statusCode);
  }

  @DisplayName("Should return the response headers")
  @ParameterizedTest(name = "Returns from {0} request")
  @EnumSource(ApiResource.HTTPMethod.class)
  public void returnResponseHeaders(ApiResource.HTTPMethod method) {
    val headerName = "X-Custom-Header";
    val headerValue = "Custom Header Value";
    val headers = new HashMap<String, String>();
    headers.put(headerName, headerValue);
    val request = buildRequest(builder -> builder.method(method));
    mockResponse(200, "{\"test\": 1}", headers);

    val response = subject.request(request);

    assertThat(response.getHeaders()).containsEntry(headerName, headerValue);
  }

  @DisplayName("Should return the response body")
  @ParameterizedTest(name = "Returns from {0} request")
  @EnumSource(ApiResource.HTTPMethod.class)
  public void returnResponseBody(ApiResource.HTTPMethod method) {
    val value = Mocks.faker.overwatch().hero();
    val jsonBody = String.format("{\"test\":\"%s\"}", value);
    val request = buildRequest(builder -> builder.method(method));
    mockResponse(200, jsonBody);

    val response = subject.request(request);

    assertThat(response.getModelBody(TestModel.class)).hasFieldOrPropertyWithValue("test", value);
  }

  @AfterEach
  public void afterScenario() {
    subject = null;
  }
}
