package com.trunkrs.sdk.model;

import com.trunkrs.sdk.enumeration.OwnerType;
import java.util.EnumSet;

public abstract class PackageOwner {
  protected static final EnumSet<OwnerType> ownerTypes = EnumSet.allOf(OwnerType.class);

  /**
   * Get the package owner type.
   *
   * @return The package owner type.
   */
  public abstract OwnerType getOwnerType();

  /**
   * Get the name of the package owner.
   *
   * @return The package owner name.
   */
  public abstract String getName();

  /**
   * Get the address line of the package owner.
   *
   * @return The address line of the package owner.
   */
  public abstract String getAddressLine();

  /**
   * Get the postal code of the package owner.
   *
   * @return The postal code of the package owner.
   */
  public abstract String getPostalCode();

  /**
   * Get the city of the package owner.
   *
   * @return The city of the package owner.
   */
  public abstract String getCity();

  /**
   * Get the country of the package owner.
   *
   * @return The country of the package owner.
   */
  public abstract String getCountry();
}
