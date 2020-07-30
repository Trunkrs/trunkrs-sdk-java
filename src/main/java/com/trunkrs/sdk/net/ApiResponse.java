package com.trunkrs.sdk.net;

import com.trunkrs.sdk.util.Serializer;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponse {
  private int status;
  private Map<String, String> headers;
  private String body;

  /**
   * Checks whether the status code represents a successful request.
   *
   * @return Whether status code is successful.
   */
  public boolean isSuccessful() {
    return status >= 200 && status <= 204;
  }

  /**
   * Retrieves the body of the request as a model representation.
   *
   * @param modelClass The model class reference.
   * @param <Model> The model type of the response body.
   * @return The model representation of the response body.
   */
  public <Model> Model getModelBody(Class<Model> modelClass) {
    if (body == null || body.isEmpty()) {
      return null;
    }

    return Serializer.get().deserialize(body, modelClass);
  }
}
