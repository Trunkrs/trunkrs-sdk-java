package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

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
