package com.trunkrs.sdk.enumeration;

import lombok.Getter;

public enum OwnerType {
  trunkrs("TRUNKRS"),
  driver("DRIVER"),
  customer("CUSTOMER"),
  merchant("MERCHANT");

  @Getter
  private String code;

  OwnerType(String code) {
    this.code = code;
  }
}
