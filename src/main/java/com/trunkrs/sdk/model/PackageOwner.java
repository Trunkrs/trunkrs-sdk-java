package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.enumeration.OwnerType;
import lombok.Getter;

import java.util.EnumSet;
import java.util.stream.Stream;

public abstract class PackageOwner {
  protected final static EnumSet<OwnerType> ownerTypes = EnumSet.allOf(OwnerType.class);

  /**
   * Get the package owner type.
   * @return The package owner type.
   */
  public abstract OwnerType getOwnerType();

  /**
   * Get the name of the package owner.
   * @return The package owner name.
   */
  public abstract String getName();

  /**
   * Get the address line of the package owner.
   * @return The address line of the package owner.
   */
  public abstract String getAddressLine();

  /**
   * Get the postal code of the package owner.
   * @return The postal code of the package owner.
   */
  public abstract String getPostalCode();

  /**
   * Get the city of the package owner.
   * @return The city of the package owner.
   */
  public abstract String getCity();

  /**
   * Get the country of the package owner.
   * @return The country of the package owner.
   */
  public abstract String getCountry();
}

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
    if (typeName == null || typeName.isEmpty()) {
      return null;
    }

    return ownerTypes.stream()
      .filter(type -> type.getCode().equals(typeName))
      .findAny()
      .orElse(null);
  }
}


