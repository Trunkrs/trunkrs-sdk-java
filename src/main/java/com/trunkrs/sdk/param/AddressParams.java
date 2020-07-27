package com.trunkrs.sdk.param;

import lombok.Builder;

@Builder
public class AddressParams {
  String companyName;
  String contactName;
  String addressLine;
  String postalCode;
  String city;
  String countryCode = "NL";
  String email;
  String phoneNumber;
  String remarks;
}
