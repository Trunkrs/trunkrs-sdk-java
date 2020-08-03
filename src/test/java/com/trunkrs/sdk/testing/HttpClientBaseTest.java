package com.trunkrs.sdk.testing;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.APICredentials;
import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.enumeration.APIVersion;
import com.trunkrs.sdk.exception.UnsupportedVersionException;
import com.trunkrs.sdk.net.ApiRequest;
import com.trunkrs.sdk.util.Serializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.var;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class HttpClientBaseTest {
  private APIVersion origApiVersion;
  private APICredentials origCredentials;

  protected MockWebServer mockServer;

  @Builder
  @EqualsAndHashCode
  protected static class TestModel {
    @SerializedName("test")
    public String test;
  }

  @Builder
  @EqualsAndHashCode
  protected static class TestPayload {
    @SerializedName("foo")
    public String foo;
  }

  protected <Body> void mockResponse(int status) {
    mockResponse(status, "");
  }

  protected <Body> void mockResponse(int status, Body body) {
    mockResponse(status, body, null);
  }

  protected <Body> void mockResponse(int status, Body body, Map<String, String> headers) {
    var response =
        new MockResponse()
            .setResponseCode(status)
            .setBody(body instanceof String ? (String) body : Serializer.get().serialize(body));
    if (headers != null) {
      for (val entry : headers.entrySet()) {
        response = response.setHeader(entry.getKey(), entry.getValue());
      }
    }

    mockServer.enqueue(response);
  }

  protected ApiRequest buildRequest(
      Function<ApiRequest.ApiRequestBuilder, ApiRequest.ApiRequestBuilder> creator) {
    val builder =
        ApiRequest.builder().url(TrunkrsSDK.getBaseUrl()).headers(new HashMap<>()).body("");

    return creator.apply(builder).build();
  }

  @BeforeEach
  public void startScenario() throws IOException, NoSuchFieldException, IllegalAccessException {
    mockServer = new MockWebServer();
    mockServer.start();

    val baseUrlField = TrunkrsSDK.class.getDeclaredField("baseUrl");
    baseUrlField.setAccessible(true);
    baseUrlField.set(null, mockServer.url("/api").toString());

    origApiVersion = TrunkrsSDK.getApiVersion();
    origCredentials = TrunkrsSDK.getCredentials();
    TrunkrsSDK.setCredentials(APICredentials.from(UUID.randomUUID().toString()));
  }

  @AfterEach
  public void endScenario() throws IOException, UnsupportedVersionException {
    mockServer.shutdown();

    TrunkrsSDK.useProduction();
    TrunkrsSDK.setCredentials(origCredentials);
    TrunkrsSDK.setApiVersion(origApiVersion);
  }
}
