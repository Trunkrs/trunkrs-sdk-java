package com.trunkrs.sdk.net;

import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ServerValidationException;
import com.trunkrs.sdk.testing.SDKBaseTest;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ApiResource.put")
public class ApiResourcePutTest extends SDKBaseTest {
  @Test
  @DisplayName("Should execute a PUT request")
  public void makesGetRequest() throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    val payload = TestPayload.builder().build();
    mockResponseCallback(request -> {
      assertThat(request.getMethod())
        .isEqualTo(ApiResource.HTTPMethod.PUT);

      return ApiResponse.builder()
        .status(200)
        .build();
    });

    ApiResource.put("foo", payload, TestModel.class);
  }

  @Test
  @DisplayName("Should add authentication headers")
  public void addsAuthHeaders() throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    val payload = TestPayload.builder().build();
    val authHeaders = TrunkrsSDK.getCredentials().getAuthHeaders();
    mockResponseCallback(request -> {
      for (val headerEntry : authHeaders.entrySet()) {
        assertThat(authHeaders)
          .containsEntry(headerEntry.getKey(), headerEntry.getValue());
      }

      return ApiResponse.builder()
        .status(200)
        .build();
    });

    ApiResource.put("test", payload, TestModel.class);
  }

  @Test
  @DisplayName("Should add payload onto request")
  public void addsPayload()
    throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    val payload = TestPayload.builder()
      .foo("bar")
      .build();
    mockResponseCallback(request -> {
      assertThat(request.getBody())
        .isEqualTo("{\"foo\":\"bar\"}");

      return ApiResponse.builder()
        .status(200)
        .build();
    });

    ApiResource.put("test", payload, TestModel.class);
  }

  @Test
  @DisplayName("Should return requested model")
  public void returnsModel() throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    val payload = TestPayload.builder().build();
    val expected = ApiResourceGetTest.TestModel.builder().test("Yupz!!").build();
    mockResponse(200, "{ \"test\": \"Yupz!!\" }");

    val model = ApiResource.put("test", payload, ApiResourceGetTest.TestModel.class);

    assertThat(model)
      .isInstanceOf(ApiResourceGetTest.TestModel.class)
      .isEqualTo(expected);
  }

  @Test
  @DisplayName("Should throw server validation exception")
  public void throwsServerValidation() {
    val validationMsg = "The field 'foo' is required.";
    val response = String.format("{ \"message\": \"%s\" }", validationMsg);
    mockResponse(422, response);

    assertThatThrownBy(() -> ApiResource.put("test", TestPayload.builder().build(), TestModel.class))
      .isInstanceOf(ServerValidationException.class)
      .hasMessageContaining(validationMsg)
      .hasFieldOrPropertyWithValue("validationMessage", validationMsg);
  }

  @Test
  @DisplayName("Should throw not authorized when not authorized")
  public void throwsNotAuthorized() {
    val payload = TestPayload.builder().build();
    mockResponse(401);

    assertThatThrownBy(() -> ApiResource.put("test", payload, ApiResourceGetTest.TestModel.class))
      .isInstanceOf(NotAuthorizedException.class);
  }

  @Test
  @DisplayName("Should throw general API exception")
  public void throwsGeneralApiException() {
    val payload = TestPayload.builder().build();
    mockResponse(500);

    assertThatThrownBy(() -> ApiResource.put("test", payload, ApiResourceGetTest.TestModel.class))
      .isInstanceOf(GeneralApiException.class);
  }
}
