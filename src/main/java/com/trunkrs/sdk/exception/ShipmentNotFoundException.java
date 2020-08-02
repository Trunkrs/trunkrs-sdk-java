package com.trunkrs.sdk.exception;

@SuppressWarnings("serial")
public class ShipmentNotFoundException extends Exception {
  public ShipmentNotFoundException(int shipmentId) {
    super(String.format("The shipment with id '%d' couldn't be found.", shipmentId));
  }
}
