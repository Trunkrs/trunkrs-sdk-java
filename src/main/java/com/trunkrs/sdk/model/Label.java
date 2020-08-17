package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.enumeration.LabelType;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.UnsupportedVersionException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.util.StringUtils;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.val;

@Builder(access = AccessLevel.PACKAGE)
public class Label {
  @Builder
  static class BatchDownloadLabelParams {
    @SerializedName("trunkrsNrs")
    public List<String> trunkrsNrs;
  }

  private static String getV1LabelUrl(String trunkrsNr, String postalCode, LabelType type) {
    val cleanPostalCode = postalCode.replace(" ", "").toLowerCase();
    val token = StringUtils.md5Digest(trunkrsNr + cleanPostalCode);

    switch (type) {
      case ZPL:
        return String.format("label/%s/%s/zpl", token, trunkrsNr);
      default: // PDF
        return String.format("label/%s/%s", token, trunkrsNr);
    }
  }

  /**
   * Retrieves the label for the specified shipment by its Trunkrs number and postal code.
   *
   * @param trunkrsNr The shipment Trunkrs number.
   * @param postalCode The postal code of the recipient.
   * @param type The label type to retrieve.
   * @return The downloaded Label.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  @SneakyThrows(UnsupportedVersionException.class)
  public static Label download(String trunkrsNr, String postalCode, LabelType type)
      throws NotAuthorizedException, GeneralApiException {
    ApiResponse response = null;
    switch (TrunkrsSDK.getApiVersion()) {
      case v1:
        response = ApiResource.get(getV1LabelUrl(trunkrsNr, postalCode, type));
        break;
      case v2:
        // Stub for V2, can never be called at this moment.
        throw new UnsupportedVersionException();
    }

    return Label.builder().content(response.getBody()).type(type).build();
  }

  /**
   * Batch retrieves labels in a single go for multiple shipments. All labels will be added into a
   * single file. ZPL is not supported at the moment.
   *
   * @param trunkrsNrs The trunkrs numbers of the shipments to get labels for.
   * @param type The label type. Only PDF is supported at the moment.
   * @return The Label reference containing the label data.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  @SneakyThrows
  public static Label downloadBatch(List<String> trunkrsNrs, LabelType type)
      throws NotAuthorizedException, GeneralApiException {
    val params = BatchDownloadLabelParams.builder().trunkrsNrs(trunkrsNrs).build();

    if (type == LabelType.ZPL) {
      throw new UnsupportedOperationException(
          "Only PDF labels are supported for label batching at this moment.");
    }

    ApiResponse response = null;
    switch (TrunkrsSDK.getApiVersion()) {
      case v1:
        response = ApiResource.put("shipments/labels", params);
        break;
      case v2:
        // Stub for V2, can never be called at this moment.
        throw new UnsupportedVersionException();
    }

    return Label.builder().content(response.getBody()).type(type).build();
  }

  private LabelType type;
  private byte[] content;

  /**
   * Get the type of the label.
   *
   * @return The label type.
   */
  public LabelType getType() {
    return type;
  }

  /**
   * Get the byte[] representation of the label.
   *
   * @return A byte[] containing the label data.
   */
  public byte[] getBytes() {
    return content;
  }

  /**
   * Get the base64 representation of the label. Especially good for ZPL labels.
   *
   * @return The base64 representation of the label.
   */
  public String getBase64String() {
    return StringUtils.base64Encode(getBytes());
  }
}
