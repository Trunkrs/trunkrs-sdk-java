package com.trunkrs.sdk.net;

import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ServerValidationException;
import com.trunkrs.sdk.net.http.HttpClient;
import com.trunkrs.sdk.net.http.OkHttpApiClient;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.val;

public abstract class ApiResource {
  public enum HTTPMethod {
    GET,
    POST,
    PUT,
    DELETE
  }

  private static HttpClient client = new OkHttpApiClient();

  private static String buildUrl(String resource) {
    return String.format(
        "%s/%s/%s", TrunkrsSDK.getBaseUrl(), TrunkrsSDK.getVersionIdentifier(), resource);
  }

  private static Map<String, String> buildHeaders(boolean isDownload) {
    val headers = new HashMap<String, String>();

    headers.put("User-Agent", String.format("Trunkrs SDK/Java/%s", TrunkrsSDK.getSdkVersion()));
    headers.putAll(TrunkrsSDK.getCredentials().getAuthHeaders());

    if (!isDownload) {
      headers.put("Accept", "application/json; charset=utf-8");
    }

    return headers;
  }

  private static void throwResponseException(ApiResponse response)
      throws NotAuthorizedException, ServerValidationException, GeneralApiException {
    switch (response.getStatus()) {
      case 401:
        throw new NotAuthorizedException();
      case 422:
        throw new ServerValidationException(response);
      default:
        throw new GeneralApiException(response);
    }
  }

  /**
   * Executes a GET request for the specified resource.
   *
   * @param resource The resource name to GET.
   * @param modelClass The resulting model class.
   * @param <Model> The model returned by the GET request
   * @return The resulting model the resource name represents.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static <Model> Model get(String resource, Class<Model> modelClass)
      throws NotAuthorizedException, GeneralApiException {
    return get(resource, null, modelClass);
  }

  /**
   * Executes a GET request for the specified resource.
   *
   * @param resource The resource name to GET.
   * @return The resulting model the resource name represents.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static ApiResponse get(String resource)
      throws NotAuthorizedException, GeneralApiException {
    return get(resource, (Parameters) null);
  }

  /**
   * Executes a GET request for the specified resource.
   *
   * @param resource The resource name to GET.
   * @param params The parameters to be passed to the resource.
   * @param modelClass The resulting model class.
   * @param <Model> The model returned by the GET request
   * @return The resulting model the resource name represents.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static <Model> Model get(String resource, Parameters params, Class<Model> modelClass)
      throws NotAuthorizedException, GeneralApiException {
    val response = get(resource, params);
    return response.getModelBody(modelClass);
  }

  /**
   * Executes a GET request for the specified resources.
   *
   * @param resource The resource name to GET.
   * @param params The parameters to be passed to the resource.
   * @return The server response.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  @SneakyThrows(ServerValidationException.class)
  public static ApiResponse get(String resource, Parameters params)
      throws NotAuthorizedException, GeneralApiException {
    val request =
        ApiRequest.builder()
            .method(HTTPMethod.GET)
            .headers(buildHeaders(false))
            .url(buildUrl(resource))
            .params(params)
            .build();

    val response = client.request(request);

    if (!response.isSuccessful()) {
      throwResponseException(response);
    }
    return response;
  }

  /**
   * Executes a POST request on the specified resource with the specified payload.
   *
   * @param resource The resource name of the resource to create.
   * @param payload The request payload.
   * @param modelClass The resulting model class.
   * @param <Model> The model returned by the POST request
   * @param <Payload> The payload of the request body.
   * @return The resulting model the resource name represents.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws ServerValidationException Thrown when the request payload doesn't match the expectation
   *     of the API.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static <Model, Payload> Model post(
      String resource, Payload payload, Class<Model> modelClass)
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    val response = post(resource, payload);
    return response.getModelBody(modelClass);
  }

  /**
   * Executes a POST request on the specified resource with the specified payload.
   *
   * @param resource The resource name of the resource to create.
   * @param payload The request payload.
   * @param <Payload> The payload of the request body.
   * @return The resulting model the resource name represents.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws ServerValidationException Thrown when the request payload doesn't match the expectation
   *     of the API.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static <Payload> ApiResponse post(String resource, Payload payload)
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    val request =
        ApiRequest.builder()
            .method(HTTPMethod.POST)
            .headers(buildHeaders(false))
            .url(buildUrl(resource))
            .body(payload)
            .build();

    val response = client.request(request);

    if (!response.isSuccessful()) {
      throwResponseException(response);
    }
    return response;
  }

  /**
   * Executes a PUT request on the specified resource with the specified payload.
   *
   * @param resource The resource name of the resource to update.
   * @param payload The request payload.
   * @param modelClass The resulting model class.
   * @param <Model> The model returned by the PUT request
   * @param <Payload> The payload of the request body.
   * @return The resulting model the resource name represents.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws ServerValidationException Thrown when the request payload doesn't match the expectation
   *     of the API.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static <Payload, Model> Model put(
      String resource, Payload payload, Class<Model> modelClass)
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    val response = put(resource, payload);
    return response.getModelBody(modelClass);
  }

  /**
   * Executes a PUT request on the specified resource with the specified payload.
   *
   * @param resource The resource name of the resource to update.
   * @param payload The request payload.
   * @param <Payload> The payload of the request body.
   * @return The resulting model the resource name represents.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws ServerValidationException Thrown when the request payload doesn't match the expectation
   *     of the API.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static <Payload> ApiResponse put(String resource, Payload payload)
      throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    val request =
        ApiRequest.builder()
            .method(HTTPMethod.PUT)
            .headers(buildHeaders(false))
            .url(buildUrl(resource))
            .body(payload)
            .build();

    val response = client.request(request);

    if (!response.isSuccessful()) {
      throwResponseException(response);
    }
    return response;
  }

  /**
   * Executes a DELETE request on the specified resource.
   *
   * @param resource The resource name to DELETE.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static void delete(String resource) throws NotAuthorizedException, GeneralApiException {
    delete(resource, null);
  }

  /**
   * Executes a DELETE request on the specified resource.
   *
   * @param resource The resource name to DELETE.
   * @param params The parameters to be passed to the resource.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  @SneakyThrows(ServerValidationException.class)
  public static void delete(String resource, Parameters params)
      throws NotAuthorizedException, GeneralApiException {
    val request =
        ApiRequest.builder()
            .method(HTTPMethod.DELETE)
            .headers(buildHeaders(false))
            .url(buildUrl(resource))
            .params(params)
            .build();

    val response = client.request(request);

    if (!response.isSuccessful()) {
      throwResponseException(response);
    }
  }

  /**
   * Sets the specified client implementation as the underlying HTTP client.
   *
   * @param client The client to be used for the SDK.
   */
  public static void setHttpClient(HttpClient client) {
    ApiResource.client = client;
  }
}
