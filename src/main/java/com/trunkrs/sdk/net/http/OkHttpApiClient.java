package com.trunkrs.sdk.net.http;

import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.net.ApiRequest;
import com.trunkrs.sdk.net.ApiResponse;

import lombok.SneakyThrows;
import lombok.val;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OkHttpApiClient implements HttpClient {
  public static final MediaType MEDIA_TYPE_JSON
    = MediaType.parse("application/json; charset=utf-8");

  private static Request toOkHttpRequest(ApiRequest request) {
    val headers = new Headers.Builder();
    for (val pair : request.getHeaders().entrySet()) {
      headers.add(pair.getKey(), pair.getValue());
    }

    val requestBuilder = new Request.Builder()
        .url(request.getUrl())
        .headers(headers.build());

    switch (request.getMethod()) {
      case GET:
        requestBuilder.get();
        break;
      case DELETE:
        requestBuilder.delete();
        break;
      default:
        val body = RequestBody.create(request.getBody(), MEDIA_TYPE_JSON);
        requestBuilder.method(request.getMethod().name(), body);
    }

    return requestBuilder.build();
  }

  private Map<String, String> fromOkHttpHeaders(Headers headers) {
    val headerMap = new HashMap<String, String>();
    for (val pair : headers) {
      headerMap.put(pair.getFirst(), pair.getSecond());
    }

    return headerMap;
  }

  private final OkHttpClient client = new OkHttpClient();

  @Override
  @SneakyThrows(GeneralApiException.class)
  public ApiResponse request(ApiRequest request) {
    try {
      val okHttpRequest = toOkHttpRequest(request);

      try (Response response = client.newCall(okHttpRequest).execute()) {
        return ApiResponse.builder()
          .status(response.code())
          .headers(fromOkHttpHeaders(response.headers()))
          .body(response.body().string())
          .build();
      }
    } catch (IOException ioException) {
      throw new GeneralApiException();
    }
  }
}
