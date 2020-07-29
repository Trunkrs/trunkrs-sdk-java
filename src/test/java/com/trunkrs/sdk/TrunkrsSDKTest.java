package com.trunkrs.sdk;

import com.trunkrs.sdk.enumeration.APIVersion;
import com.trunkrs.sdk.exception.UnsupportedVersionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("TrunkrsSDK")
public class TrunkrsSDKTest {
  @Test
  @DisplayName("Should not throw when selecting supported version")
  public void notThrowsWhenSupportedVersion() {
    assertThatCode(() -> TrunkrsSDK.setApiVersion(APIVersion.v1))
      .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Should throw when selecting unsupported version")
  public void throwsWhenUnsupportedVersion() {
    assertThatThrownBy(() -> TrunkrsSDK.setApiVersion(APIVersion.v2))
      .isInstanceOf(UnsupportedVersionException.class);
  }

  @Test
  @DisplayName("Should switch to staging environment")
  public void switchesToStaging() {
    TrunkrsSDK.useStaging();

    assertThat(TrunkrsSDK.getBaseUrl())
      .contains("staging-api");
    assertThat(TrunkrsSDK.getTrackingBaseUrl())
      .contains("staging-parcel");
  }

  @Test
  @DisplayName("Should switch back to production environment")
  public void switchesToProduction() {
    TrunkrsSDK.useStaging();

    TrunkrsSDK.useProduction();

    assertThat(TrunkrsSDK.getBaseUrl())
      .doesNotContain("staging-api");
    assertThat(TrunkrsSDK.getTrackingBaseUrl())
      .doesNotContain("staging-parcel");
  }
}
