package com.trunkrs.sdk.model;

import static org.assertj.core.api.Assertions.*;

import com.sun.tools.javac.util.List;
import com.trunkrs.sdk.enumeration.LabelType;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.net.ApiResponse;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;
import com.trunkrs.sdk.util.Serializer;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("V1 - Label Download Batch")
public class APIV1DownloadBatchTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should make a PUT request")
  public void makesPUTRequest() throws NotAuthorizedException, GeneralApiException {
    val trunkrsNrs = List.from(new String[] {Mocks.getFakeTrunkrsNr()});
    mockResponseCallback(
        apiRequest -> {
          assertThat(apiRequest).hasFieldOrPropertyWithValue("method", ApiResource.HTTPMethod.PUT);

          return ApiResponse.builder().status(200).build();
        });

    Label.downloadBatch(trunkrsNrs, LabelType.PDF);
  }

  @Test
  @DisplayName("Should add the right payload")
  public void addsPayload() throws NotAuthorizedException, GeneralApiException {
    val trunkrsNrs = List.from(new String[] {Mocks.getFakeTrunkrsNr()});
    mockResponseCallback(
        apiRequest -> {
          val payload =
              Serializer.get()
                  .deserialize(apiRequest.getBody(), Label.BatchDownloadLabelParams.class);
          assertThat(payload).hasFieldOrPropertyWithValue("trunkrsNrs", trunkrsNrs);

          return ApiResponse.builder().status(200).build();
        });

    Label.downloadBatch(trunkrsNrs, LabelType.PDF);
  }

  @Test
  @DisplayName("Should return the right label data")
  public void returnsLabelData() throws NotAuthorizedException, GeneralApiException {
    val labelData = Mocks.faker.cat().name().getBytes();
    val trunkrsNrs = List.from(new String[] {Mocks.getFakeTrunkrsNr()});
    mockResponse(200, labelData);

    val label = Label.downloadBatch(trunkrsNrs, LabelType.PDF);

    assertThat(label).isInstanceOf(Label.class).hasFieldOrPropertyWithValue("bytes", labelData);
  }

  @Test
  @DisplayName("Should throw when requesting ZPL")
  public void throwsWhenZPLRequested() {
    val trunkrsNrs = List.from(new String[] {Mocks.getFakeTrunkrsNr()});

    assertThatThrownBy(() -> Label.downloadBatch(trunkrsNrs, LabelType.ZPL))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
