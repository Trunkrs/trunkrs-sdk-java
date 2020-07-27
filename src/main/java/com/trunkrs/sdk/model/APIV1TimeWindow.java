package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.Date;

@Getter
class APIV1TimeWindow extends TimeWindow {
  @SerializedName("from")
  Date open;

  @SerializedName("to")
  Date close;
}
