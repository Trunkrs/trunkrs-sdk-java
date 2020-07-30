package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import lombok.Getter;

@Getter
class APIV1TimeSlot extends TimeSlot {
  @SerializedName("id")
  int id;

  @SerializedName("dataWindow")
  Date cutOff;

  @SerializedName("deliveryWindow")
  APIV1TimeWindow deliveryWindow;

  @SerializedName("collectionWindow")
  APIV1TimeWindow collectionWindow;
}
