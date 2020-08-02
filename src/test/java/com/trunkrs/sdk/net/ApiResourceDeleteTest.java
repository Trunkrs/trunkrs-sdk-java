package com.trunkrs.sdk.net;

import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.testing.SDKBaseTest;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ApiResource.delete")
public class ApiResourceDeleteTest extends SDKBaseTest {
  @Test
  @DisplayName("Should execute a DELETE request")
  public void makesGetRequest() throws NotAuthorizedException, GeneralApiException {
    mockResponseCallback(request -> {
      assertThat(request.getMethod())
        .isEqualTo(ApiResource.HTTPMethod.DELETE);

      return ApiResponse.builder()
        .status(200)
        .build();
    });

    ApiResource.delete("foo");
  }

  @Test
  @DisplayName("Should add authentication headers")
  public void addsAuthHeaders() throws NotAuthorizedException, GeneralApiException {
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

    ApiResource.delete("test");
  }

  @Test
  @DisplayName("Should add parameters when supplied")
  public void addsParameters() throws NotAuthorizedException, GeneralApiException {
    val params = Parameters.builder()
      .with("team", "rt")
      .build();
    mockResponseCallback(request -> {
      assertThat(request.getUrl())
        .endsWith("team=rt");

      return ApiResponse.builder()
        .status(200)
        .build();
    });

    ApiResource.delete("how-awesome-is", params);
  }

  @Test
  @DisplayName("Should throw not authorized when not authorized")
  public void throwsNotAuthorized() {
    mockResponse(401);

    assertThatThrownBy(() -> ApiResource.delete("test"))
      .isInstanceOf(NotAuthorizedException.class);
  }

  @Test
  @DisplayName("Should throw general API exception")
  public void throwsGeneralApiException() {
    mockResponse(500);

    assertThatThrownBy(() -> ApiResource.delete("test"))
      .isInstanceOf(GeneralApiException.class);
  }
}
