package com.trunkrs.sdk.enumeration;

import lombok.Getter;

public enum StateReasonCode {
  addressNotFound("ADDRESS_NOT_FOUND"),
  businessClosed("BUSINESS_CLOSED"),
  recipientNotHome("RECIPIENT_NOT_AT_HOME"),
  noTimeLeftInTimeslot("NO_TIME_LEFT_IN_TIMESLOT"),
  refusedByCustomer("REFUSED_BY_CUSTOMER"),
  deliveryDamaged("DELIVERY_DAMAGED"),
  driverInAccident("DRIVER_INVOLVED_IN_ACCIDENT"),
  badAddress("BAD_ADDRESS"),
  extremeWeather("EXTREME_WEATHER_CONDITIONS"),
  parcelMissing("PARCEL_MISSING"),
  inaccessibleAddress("DELIVER_ADDRESS_NOT_ACCESSIBLE");

  @Getter
  private String code;

  StateReasonCode(String code) {
    this.code = code;
  }
}
