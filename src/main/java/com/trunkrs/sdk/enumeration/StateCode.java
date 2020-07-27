package com.trunkrs.sdk.enumeration;

import lombok.Getter;

public enum StateCode {
  dataReceived("DATA_RECEIVED"),
  dataProcessed("DATA_PROCESSED"),
  shipmentSorted("SHIPMENT_SORTED"),
  shipmentSortedAtSubDepot("SHIPMENT_SORTED_AT_SUB_DEPOT"),
  shipmentAcceptedByDriver("SHIPMENT_ACCEPTED_BY_DRIVER"),
  shipmentDelivered("SHIPMENT_DELIVERED"),
  shipmentDeliveredToNeighbor("SHIPMENT_DELIVERED_TO_NEIGHBOR"),
  shipmentNotDelivered("SHIPMENT_NOT_DELIVERED"),
  shipmentNotArrived("EXCEPTION_SHIPMENT_NOT_ARRIVED"),
  shipmentMissSorted("EXCEPTION_SHIPMENT_MISS_SORTED"),
  shipmentDeclinedByDriver("EXCEPTION_SHIPMENT_DECLINED_BY_DRIVER"),
  shipmentMissing("EXCEPTION_SHIPMENT_MISSING"),
  shipmentLost("EXCEPTION_SHIPMENT_LOST"),
  shipmentCancelledBySender("EXCEPTION_SHIPMENT_CANCELLED_BY_SENDER"),
  getShipmentCancelledByTrunkrs("EXCEPTION_SHIPMENT_CANCELLED_BY_TRUNKRS");

  @Getter
  private final String code;

  StateCode(String code) {
    this.code = code;
  }
}
