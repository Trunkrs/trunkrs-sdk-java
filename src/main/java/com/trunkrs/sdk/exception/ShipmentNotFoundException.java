package com.trunkrs.sdk.exception;

@SuppressWarnings("serial")
public class ShipmentNotFoundException extends Exception {
  public ShipmentNotFoundException(int shipmentId) {
    super(String.format("The shipment with id '%d' couldn't be found.", shipmentId));
  }

  public ShipmentNotFoundException(String trunkrsNr) {
    super(String.format("The shipment with Trunkrs number '%s' couldn't be found.", trunkrsNr));
  }
}
