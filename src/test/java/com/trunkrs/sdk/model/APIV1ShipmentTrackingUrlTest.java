package com.trunkrs.sdk.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Shipment.trackingUrl")
public class APIV1ShipmentTrackingUrlTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should create a valid tracking URL")
  public void createsTrackingUrl() {
    val trunkrsNr = Mocks.getFakeTrunkrsNr();
    val zipCode = Mocks.faker.address().zipCode();
    val subject = new APIV1Shipment();
    subject.trunkrsNr = trunkrsNr;
    subject.deliveryAddress = new APIV1Address();
    subject.deliveryAddress.postal = zipCode;

    val trackingUrl = subject.getTrackingUrl();

    assertThat(trackingUrl).endsWith(String.format("%s/%s", trunkrsNr, zipCode));
  }
}
