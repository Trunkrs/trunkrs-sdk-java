package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public abstract class Address {
  public abstract String getContactName();
  public abstract String getAddressLine();
  public abstract String getPostal();
  public abstract String getCity();
  public abstract String getCountryCode();
  public abstract String getEmail();
  public abstract String getPhoneNumber();
  public abstract String getRemarks();
}

@Getter
class APIV1Address extends Address {
  @SerializedName("name")
  String contactName;

  @SerializedName("address")
  String addressLine;

  @SerializedName("postCode")
  String postal;

  @SerializedName("city")
  String city;

  @SerializedName("country")
  String countryCode;

  @SerializedName("email")
  String email;

  @SerializedName("phoneNumber")
  String phoneNumber;

  @SerializedName("remarks")
  String remarks;
}
