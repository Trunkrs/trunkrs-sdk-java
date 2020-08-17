package com.trunkrs.sdk.util;

import static com.trunkrs.sdk.util.StringUtils.urlEncode;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class FormUtils {
  /**
   * Creates a query string from the the specified map.
   *
   * @param values The values to be put into the query string.
   * @return The query string representation of the values.
   */
  public static String createQueryString(Map<String, String> values) {
    return values.entrySet().stream()
        .map(
            entry -> String.format("%s=%s", urlEncode(entry.getKey()), urlEncode(entry.getValue())))
        .collect(Collectors.joining("&"));
  }
}
