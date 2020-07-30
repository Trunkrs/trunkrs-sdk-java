package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.enumeration.OwnerType;
import lombok.Getter;

class APIV1PackageOwner extends PackageOwner {
  @SerializedName("type")
  String typeName;

  @Getter
  @SerializedName("name")
  String name;

  @Getter
  @SerializedName("address")
  String addressLine;

  @Getter
  @SerializedName("postCode")
  String postalCode;

  @Getter
  @SerializedName("city")
  String city;

  @Getter
  @SerializedName("country")
  String country;

  @Override
  public OwnerType getOwnerType() {
    return ownerTypes.stream()
        .filter(type -> type.getCode().equals(typeName))
        .findAny()
        .orElse(null);
  }
}
