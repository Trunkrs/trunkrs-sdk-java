package com.trunkrs.sdk.net;

import com.trunkrs.sdk.util.Form;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public class Parameters {
  private Map<String, String> params = new HashMap<>();

  public static class ParametersBuilder {
    private Map<String, String> getParams() {
      if (params == null) {
        params = new HashMap<>();
      }
      return params;
    }

    private ParametersBuilder params(Map<String, String> initial) {
      this.params = initial;
      return this;
    }

    /**
     * Adds the parameter and its value to the parameters.
     * @param name The name of the parameters.
     * @param value The value of the parameter
     * @return The parameters instance.
     */
    public ParametersBuilder with(String name, int value) {
      return with(name, Integer.toString(value));
    }

    /**
     * Adds the parameter and its value to the parameters.
     * @param name The name of the parameters.
     * @param value The value of the parameter
     * @return The parameters instance.
     */
    public ParametersBuilder with(String name, String value) {
      getParams().put(name, value);
      return this;
    }
  }

  String asQueryString() {
    return Form.createQueryString(params);
  }
}
