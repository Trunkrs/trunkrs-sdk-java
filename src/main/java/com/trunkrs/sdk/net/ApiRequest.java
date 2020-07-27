package com.trunkrs.sdk.net;

import com.trunkrs.sdk.util.Serializer;

import lombok.Builder;
import lombok.Getter;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

@Builder
public class ApiRequest {
  @Getter
  ApiResource.HTTPMethod method;

  String url;

  @Getter
  Map<String, String> headers = new HashMap<String, String>();

  @Getter
  Parameters params;

  @Getter
  String body;

  public static class ApiRequestBuilder {
    public ApiRequestBuilder headers(Map<String, String> headers) {
      this.headers.putAll(headers);

      return this;
    }

    public <Payload> ApiRequestBuilder body(Payload payload) {
      body = Serializer.get().serialize(payload);
      headers.put("Content-Type", "application/json; charset=utf-8");

      return this;
    }
  }

  /**
   * Retrieve the URL including query string parameters.
   * @return The URL including its query string parameters.
   */
  public String getUrl() {
    StringBuilder sb = new StringBuilder(url);
    val hasNoBody = method == ApiResource.HTTPMethod.GET || method == ApiResource.HTTPMethod.DELETE;

    if (hasNoBody && params != null) {
      if (!url.contains("?")) {
        sb.append("?");
      }
      sb.append(params.asQueryString());
    }

    return sb.toString();
  }
}
