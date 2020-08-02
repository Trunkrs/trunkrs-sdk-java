package com.trunkrs.sdk.testing.mocks;

import com.github.javafaker.Faker;
import com.trunkrs.sdk.enumeration.EventType;
import com.trunkrs.sdk.exception.ShipmentWithoutParcelsException;
import com.trunkrs.sdk.param.AddressParams;
import com.trunkrs.sdk.param.ParcelParams;
import com.trunkrs.sdk.param.ShipmentParams;
import com.trunkrs.sdk.param.WebHookParams;
import lombok.SneakyThrows;

public class Mocks {
  public static Faker faker = new Faker();

  public static AddressParams getFakeAddress() {
    return AddressParams.builder()
        .companyName(faker.company().name())
        .contactName(faker.overwatch().hero())
        .addressLine(faker.address().streetAddress())
        .postalCode(faker.address().zipCode())
        .city(faker.witcher().location())
        .countryCode(faker.address().countryCode())
        .phoneNumber(faker.phoneNumber().phoneNumber())
        .email(faker.internet().emailAddress())
        .remarks(faker.overwatch().quote())
        .build();
  }

  public static ParcelParams getFakeParcel() {
    return ParcelParams.builder()
        .reference(faker.code().ean13())
        .description(faker.commerce().productName())
        .height(faker.random().nextInt(20, 200))
        .width(faker.random().nextInt(20, 200))
        .depth(faker.random().nextInt(20, 200))
        .weight(faker.random().nextInt(100, 10000))
        .build();
  }

  @SneakyThrows(ShipmentWithoutParcelsException.class)
  public static ShipmentParams getFakeShipment() {
    return ShipmentParams.builder()
        .timeSlotId(faker.random().nextInt(100, 999999999))
        .deliveryAddress(getFakeAddress())
        .pickupAddress(getFakeAddress())
        .addParcel(getFakeParcel())
        .build();
  }

  public static WebHookParams getFakeWebHook() {
    return WebHookParams.builder()
        .callbackUrl(faker.internet().url())
        .headerName(faker.internet().slug())
        .sessionToken(faker.witcher().character())
        .event(EventType.onStateUpdate)
        .build();
  }
}
