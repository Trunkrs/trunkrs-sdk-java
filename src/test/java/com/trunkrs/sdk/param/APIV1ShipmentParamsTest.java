package com.trunkrs.sdk.param;

import static org.assertj.core.api.Assertions.*;

import com.trunkrs.sdk.exception.ShipmentWithoutParcelsException;
import com.trunkrs.sdk.testing.APIV1BaseTest;
import com.trunkrs.sdk.testing.mocks.Mocks;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("V1 - ShipmentParams")
public class APIV1ShipmentParamsTest extends APIV1BaseTest {
  @Test
  @DisplayName("Should create V1 instance")
  public void createsRightInstance() throws ShipmentWithoutParcelsException {
    val parcel = Mocks.getFakeParcel();

    val result = ShipmentParams.builder().addParcel(parcel).build();

    assertThat(result).isInstanceOf(APIV1ShipmentParams.class);
  }

  @Test
  @DisplayName("Should set the pick-up address")
  public void setsPickUpAddress() throws ShipmentWithoutParcelsException {
    val address = Mocks.getFakeAddress();
    val parcel = Mocks.getFakeParcel();

    val params = ShipmentParams.builder().pickupAddress(address).addParcel(parcel).build();

    assertThat(params)
        .hasFieldOrPropertyWithValue("pickupCompanyName", address.companyName)
        .hasFieldOrPropertyWithValue("pickupContactName", address.contactName)
        .hasFieldOrPropertyWithValue("pickupAddress", address.addressLine)
        .hasFieldOrPropertyWithValue("pickupPostalCode", address.postalCode)
        .hasFieldOrPropertyWithValue("pickupCity", address.city)
        .hasFieldOrPropertyWithValue("pickupCountry", address.countryCode)
        .hasFieldOrPropertyWithValue("pickupEmail", address.email)
        .hasFieldOrPropertyWithValue("pickupPhoneNumber", address.phoneNumber)
        .hasFieldOrPropertyWithValue("pickupRemarks", address.remarks);
  }

  @Test
  @DisplayName("Should set the delivery address")
  public void setsDeliveryAddress() throws ShipmentWithoutParcelsException {
    val address = Mocks.getFakeAddress();
    val parcel = Mocks.getFakeParcel();

    val params = ShipmentParams.builder().deliveryAddress(address).addParcel(parcel).build();

    assertThat(params)
        .hasFieldOrPropertyWithValue("deliveryCompanyName", address.companyName)
        .hasFieldOrPropertyWithValue("deliveryContactName", address.contactName)
        .hasFieldOrPropertyWithValue("deliveryAddress", address.addressLine)
        .hasFieldOrPropertyWithValue("deliveryPostalCode", address.postalCode)
        .hasFieldOrPropertyWithValue("deliveryCity", address.city)
        .hasFieldOrPropertyWithValue("deliveryCountry", address.countryCode)
        .hasFieldOrPropertyWithValue("deliveryEmail", address.email)
        .hasFieldOrPropertyWithValue("deliveryPhoneNumber", address.phoneNumber)
        .hasFieldOrPropertyWithValue("deliveryRemarks", address.remarks);
  }

  @Test
  @DisplayName("Should set parcel information")
  public void setsParcelInfo() throws ShipmentWithoutParcelsException {
    val parcel1 = Mocks.getFakeParcel();
    val parcel2 = Mocks.getFakeParcel();

    val params = ShipmentParams.builder().addParcel(parcel1).addParcel(parcel2).build();

    assertThat(params)
        .hasFieldOrPropertyWithValue("orderReference", parcel1.reference)
        .hasFieldOrPropertyWithValue("nrParcels", 2);
  }
}
