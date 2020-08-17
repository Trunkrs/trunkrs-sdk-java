package com.trunkrs.sdk.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public abstract class StringUtils {
  /**
   * URL encodes the specified string value.
   *
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
   * Encodes a set of bytes into a base64 string.
   *
   * @param content The content to encode.
   * @return The base64 encoded content.
   */
  public static String base64Encode(byte[] content) {
    return Base64.encodeBase64String(content);
  }

  /**
   * Creates an MD5 hash for the specified string content.
   *
   * @param content The string content to digest.
   * @return The MD5 hash for the content.
   */
  @SneakyThrows(NoSuchAlgorithmException.class)
  public static String md5Digest(String content) {
    val contentBytes = content.getBytes();
    val digest = MessageDigest.getInstance("MD5").digest(contentBytes);

    return Hex.encodeHexString(digest);
  }
}
