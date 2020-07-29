package com.trunkrs.sdk;

import com.trunkrs.sdk.net.ApiRequest;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.net.http.HttpClient;
import com.trunkrs.sdk.net.http.OkHttpApiClient;
import com.trunkrs.sdk.util.Serializer;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SDKBaseTest {
  protected static HttpClient mockClient;

  @BeforeAll
  public static void beforeTests() {
    mockClient = Mockito.mock(HttpClient.class);
    ApiResource.setHttpClient(mockClient);
  }

  protected void mockResponse(int status) {
    mockResponse(status, null);
  }

  protected <Body> void mockResponse(int status, Body body) {
    mockResponse(status, body, null);
  }

  protected <Body> void mockResponse(int status, Body body, Map<String, String> headers) {
    val preppedBody = body instanceof String
      ? (String) body
      : Serializer.get().serialize(body);
    val response = ApiResponse.builder()
      .status(status)
      .body(preppedBody)
      .headers(headers)
      .build();

    when(mockClient.request(any(ApiRequest.class)))
      .thenReturn(response);
  }

  protected void mockResponseCallback(Function<ApiRequest, ApiResponse> callback) {

    when(mockClient.request(any(ApiRequest.class)))
      .then(invocation -> callback.apply(invocation.getArgument(0)));
  }

  @SneakyThrows
  protected String getJsonFixture(String fixtureName) {
    val resourcePath = getClass().getClassLoader()
      .getResource(fixtureName)
      .getFile();

    return new String(Files.readAllBytes(Paths.get(resourcePath)));
  }

  @AfterAll
  public static void afterTests() {
    ApiResource.setHttpClient(new OkHttpApiClient());
  }
}
