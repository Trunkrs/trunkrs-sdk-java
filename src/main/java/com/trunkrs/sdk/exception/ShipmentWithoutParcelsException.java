package com.trunkrs.sdk.exception;

public class ShipmentWithoutParcelsException extends Exception {
  public ShipmentWithoutParcelsException() {
    super("The shipment needs at least a single parcel to be valid. Please provide the details of your parcel.");
  }
}
