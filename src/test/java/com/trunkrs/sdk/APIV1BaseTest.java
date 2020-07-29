package com.trunkrs.sdk;

import com.trunkrs.sdk.enumeration.APIVersion;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import java.util.UUID;

public class APIV1BaseTest extends SDKBaseTest {
  @SneakyThrows
  @BeforeAll
  public static void beforeTests() {
    SDKBaseTest.beforeTests();

    TrunkrsSDK.setApiVersion(APIVersion.v1);
    TrunkrsSDK.setCredentials(
      APICredentials.from(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
      )
    );
  }

  @Override
  protected String getJsonFixture(String fixtureName) {
    return super.getJsonFixture(String.format("v1_fixtures/%s", fixtureName));
  }
}
