package com.trunkrs.sdk.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.trunkrs.sdk.enumeration.LabelType;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Label")
public class LabelTest {
  private byte[] labelData = "TeamRuntimeTerror1337".getBytes();
  private Label subject;

  @BeforeEach
  public void prepareLabel() {
    subject = Label.builder().content(labelData).type(LabelType.PDF).build();
  }

  @Test
  @DisplayName("Should reflect label data")
  public void returnsLabelData() {
    val data = subject.getBytes();

    assertThat(data).isEqualTo(labelData);
  }

  @Test
  @DisplayName("Should reflect label type")
  public void returnsLabelType() {
    val type = subject.getType();

    assertThat(type).isEqualTo(LabelType.PDF);
  }

  @Test
  @DisplayName("Should reflect base64 representation")
  public void returnsBase64Label() {
    val base64Expected = "VGVhbVJ1bnRpbWVUZXJyb3IxMzM3";

    val result = subject.getBase64String();

    assertThat(result).isEqualTo(base64Expected);
  }
}
