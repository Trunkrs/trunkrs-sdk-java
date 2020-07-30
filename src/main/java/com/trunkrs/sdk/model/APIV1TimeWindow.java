package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import lombok.Getter;

@Getter
class APIV1TimeWindow extends TimeWindow {
  @SerializedName("from")
  Date open;

  @SerializedName("to")
  Date close;
}
