package com.trunkrs.sdk.util;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Form {
  /**
   * URL encodes the specified string value.
   * @param value The value to be url encoded.
   * @return A url encoded representation of the value.
   */
  @SneakyThrows(UnsupportedEncodingException.class)
  public static String urlEncode(String value) {
    if (value == null) {
      return null;
    }

    return URLEncoder.encode(value, StandardCharsets.UTF_8.name())
      .replaceAll("%5B", "[")
      .replaceAll("%5D", "]");
  }

  /**
   * Creates a query string from the the specified map.
   * @param values The values to be put into the query string.
   * @return The query string representation of the values.
   */
  public static String createQueryString(Map<String, String> values) {
    return values.entrySet().stream()
      .map(entry -> String.format("%s=%s", urlEncode(entry.getKey()), urlEncode(entry.getValue())))
      .collect(Collectors.joining("&"));
  }
}
