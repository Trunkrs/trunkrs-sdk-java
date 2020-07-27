package com.trunkrs.sdk.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.Date;

public abstract class TimeWindow {
  /**
   * Get the moment the window opens
   * @return The moment the window opens.
   */
  public abstract Date getOpen();

  /**
   * Get the moment the window closes.
   * @return The moment the window closes.
   */
  public abstract Date getClose();
}

@Getter
class APIV1TimeWindow extends TimeWindow {
  @SerializedName("from")
  Date open;

  @SerializedName("to")
  Date close;
}
