package com.trunkrs.sdk.net;

import com.trunkrs.sdk.util.Serializer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.val;

@Builder
@Data
public class ApiResponse {
  private int status;
  private Map<String, String> headers;
  private byte[] body;

  /**
   * Checks whether the status code represents a successful request.
   *
   * @return Whether status code is successful.
   */
  public boolean isSuccessful() {
    return status >= 200 && status <= 204;
  }

  /**
   * Reads the body stream into a string representation.
   *
   * @return The string representation of the body stream.
   */
  public String getModelString() {
    if (body == null) {
      return null;
    }
    return new String(body, Charset.forName(StandardCharsets.UTF_8.name()));
  }

  /**
   * Retrieves the body of the request as a model representation.
   *
   * @param modelClass The model class reference.
   * @param <Model> The model type of the response body.
   * @return The model representation of the response body.
   */
  public <Model> Model getModelBody(Class<Model> modelClass) {
    val bodyString = getModelString();

    if (bodyString == null || bodyString.isEmpty()) {
      return null;
    }
    return Serializer.get().deserialize(bodyString, modelClass);
  }
}
