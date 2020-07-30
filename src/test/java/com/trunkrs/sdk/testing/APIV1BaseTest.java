package com.trunkrs.sdk.testing;

import com.trunkrs.sdk.APICredentials;
import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.enumeration.APIVersion;
import java.util.UUID;
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
    TrunkrsSDK.setCredentials(
        APICredentials.from(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
  }

  @Override
  protected String getJsonFixture(String fixtureName) {
    return super.getJsonFixture(String.format("v1_fixtures/%s", fixtureName));
  }
}
