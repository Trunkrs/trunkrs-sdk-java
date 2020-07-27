package com.trunkrs.sdk.enumeration;

import lombok.Getter;

public enum APIVersion {
  v1("v1"), v2("v2");

  @Getter
  private final String versionIdentifier;

  APIVersion(String versionIdentifier) {
    this.versionIdentifier = versionIdentifier;
  }
}
