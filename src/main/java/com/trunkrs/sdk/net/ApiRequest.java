package com.trunkrs.sdk.net;

import com.trunkrs.sdk.util.Serializer;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
public class ApiRequest {
  @Getter ApiResource.HTTPMethod method;

  String url;

  @Getter Map<String, String> headers;

  @Getter Parameters params;

  @Getter String body;

  public static class ApiRequestBuilder {
    private Map<String, String> getHeaders() {
      if (headers == null) {
        headers = new HashMap<>();
      }
      return headers;
    }

    public ApiRequestBuilder headers(Map<String, String> headers) {
      getHeaders().putAll(headers);

      return this;
    }

    public <Payload> ApiRequestBuilder body(Payload payload) {
      body = Serializer.get().serialize(payload);
      getHeaders().put("Content-Type", "application/json; charset=utf-8");

      return this;
    }
  }

  /**
   * Retrieve the URL including query string parameters.
   *
   * @return The URL including its query string parameters.
   */
  public String getUrl() {
    StringBuilder sb = new StringBuilder(url);

    if (params != null) {
      if (!url.contains("?")) {
        sb.append("?");
      } else {
        sb.append("&");
      }

      sb.append(params.asQueryString());
    }

    return sb.toString();
  }
}
