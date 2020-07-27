package com.trunkrs.sdk.model;

public abstract class Address {
  /**
   * Get the name of the contact person of the address.
   * @return The contact person of the address.
   */
  public abstract String getContactName();

  /**
   * Get the address line of the address.
   * @return The address line of the address.
   */
  public abstract String getAddressLine();

  /**
   * Get the postal code of the address
   * @return The postal code.
   */
  public abstract String getPostal();

  /**
   * Get the city of the address.
   * @return The city.
   */
  public abstract String getCity();

  /**
   * Get the country code of the address.
   * @return The ISO 3166 Alpha-2 country code.
   */
  public abstract String getCountryCode();

  /**
   * Get the contact email of the address.
   * @return The email address.
   */
  public abstract String getEmail();

  /**
   * Get the contact phone number of the address.
   * @return The phone number.
   */
  public abstract String getPhoneNumber();

  /**
   * The remarks for handling of the address.
   * @return The handling remarks.
   */
  public abstract String getRemarks();
}
