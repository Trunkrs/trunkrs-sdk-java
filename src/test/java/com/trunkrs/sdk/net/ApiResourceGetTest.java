package com.trunkrs.sdk.net;

import static org.assertj.core.api.Assertions.*;

import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.testing.SDKBaseTest;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ApiResource.get")
public class ApiResourceGetTest extends SDKBaseTest {
  @Test
  @DisplayName("Should execute a GET request")
  public void makesGetRequest() throws NotAuthorizedException, GeneralApiException {
    mockResponseCallback(
        request -> {
          assertThat(request.getMethod()).isEqualTo(ApiResource.HTTPMethod.GET);

          return ApiResponse.builder().status(200).build();
        });

    ApiResource.get("foo", TestModel.class);
  }

  @Test
  @DisplayName("Should add authentication headers")
  public void addsAuthHeaders() throws NotAuthorizedException, GeneralApiException {
    val authHeaders = TrunkrsSDK.getCredentials().getAuthHeaders();
    mockResponseCallback(
        request -> {
          for (val headerEntry : authHeaders.entrySet()) {
            assertThat(authHeaders).containsEntry(headerEntry.getKey(), headerEntry.getValue());
          }

          return ApiResponse.builder().status(200).build();
        });

    ApiResource.get("test", TestModel.class);
  }

  @Test
  @DisplayName("Should add parameters when supplied")
  public void addsParameters() throws NotAuthorizedException, GeneralApiException {
    val params = Parameters.builder().with("team", "rt").build();
    mockResponseCallback(
        request -> {
          assertThat(request.getUrl()).endsWith("team=rt");

          return ApiResponse.builder().status(200).build();
        });

    ApiResource.get("how-awesome-is", params, TestModel.class);
  }

  @Test
  @DisplayName("Should return requested model")
  public void returnsModel() throws NotAuthorizedException, GeneralApiException {
    val expected = TestModel.builder().test("Yupz!!").build();
    mockResponse(200, "{ \"test\": \"Yupz!!\" }");

    val model = ApiResource.get("test", TestModel.class);

    assertThat(model).isInstanceOf(TestModel.class).isEqualTo(expected);
  }

  @Test
  @DisplayName("Should throw not authorized when not authorized")
  public void throwsNotAuthorized() {
    mockResponse(401);

    assertThatThrownBy(() -> ApiResource.get("test", TestModel.class))
        .isInstanceOf(NotAuthorizedException.class);
  }

  @Test
  @DisplayName("Should throw general API exception")
  public void throwsGeneralApiException() {
    mockResponse(500);

    assertThatThrownBy(() -> ApiResource.get("test", TestModel.class))
        .isInstanceOf(GeneralApiException.class);
  }
}
