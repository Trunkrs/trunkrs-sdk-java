package com.trunkrs.sdk.testing;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.APICredentials;
import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.enumeration.APIVersion;
import com.trunkrs.sdk.exception.UnsupportedVersionException;
import com.trunkrs.sdk.net.ApiRequest;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.net.http.HttpClient;
import com.trunkrs.sdk.net.http.OkHttpApiClient;
import com.trunkrs.sdk.util.Serializer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public abstract class SDKBaseTest {
  private APIVersion origVersion;
  private APICredentials origCredentials;

  protected HttpClient mockClient;

  @Builder
  @EqualsAndHashCode
  protected static class TestModel {
    @SerializedName("test")
    String test;
  }

  @Builder
  @EqualsAndHashCode
  protected static class TestPayload {
    @SerializedName("foo")
    String foo;
  }

  @BeforeEach
  public void beforeScenario() {
    origVersion = TrunkrsSDK.getApiVersion();
    origCredentials = TrunkrsSDK.getCredentials();

    mockClient = Mockito.mock(HttpClient.class);
    ApiResource.setHttpClient(mockClient);
    TrunkrsSDK.setCredentials(
      APICredentials.from(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
  }

  protected void mockResponse(int status) {
    mockResponse(status, null);
  }

  protected <Body> void mockResponse(int status, Body body) {
    mockResponse(status, body, null);
  }

  protected <Body> void mockResponse(int status, Body body, Map<String, String> headers) {
    val preppedBody = body instanceof String ? (String) body : Serializer.get().serialize(body);
    val response = ApiResponse.builder().status(status).body(preppedBody).headers(headers).build();

    when(mockClient.request(any(ApiRequest.class))).thenReturn(response);
  }

  protected void mockResponseCallback(Function<ApiRequest, ApiResponse> callback) {

    when(mockClient.request(any(ApiRequest.class)))
        .then(invocation -> callback.apply(invocation.getArgument(0)));
  }

  @SneakyThrows
  protected String getJsonFixture(String fixtureName) {
    val resourcePath = getClass().getClassLoader().getResource(fixtureName).getFile();

    return new String(Files.readAllBytes(Paths.get(resourcePath)), StandardCharsets.UTF_8);
  }

  @AfterEach
  public void afterScenario() throws UnsupportedVersionException {
    TrunkrsSDK.setCredentials(origCredentials);
    TrunkrsSDK.setApiVersion(origVersion);
    TrunkrsSDK.useProduction();

    ApiResource.setHttpClient(new OkHttpApiClient());
  }
}
