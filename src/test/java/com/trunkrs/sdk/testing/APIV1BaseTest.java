package com.trunkrs.sdk.testing;

import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.enumeration.APIVersion;
import lombok.SneakyThrows;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;

@Category(V1Tests.class)
public abstract class APIV1BaseTest extends SDKBaseTest {
  @SneakyThrows
  @BeforeEach
  @Override
  public void beforeScenario() {
    super.beforeScenario();

    TrunkrsSDK.setApiVersion(APIVersion.v1);
  }

  protected byte[] getJsonFixture(String fixtureName) {
    return getJsonResource(String.format("v1_fixtures/%s", fixtureName));
  }

  protected byte[] getJsonPayload(String fixtureName) {
    return getJsonResource(String.format("v1_payloads/%s", fixtureName));
  }
}
