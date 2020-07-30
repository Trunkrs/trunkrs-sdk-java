package com.trunkrs.sdk.model;

import java.util.Date;

public abstract class TimeWindow {
  /**
   * Get the moment the window opens
   *
   * @return The moment the window opens.
   */
  public abstract Date getOpen();

  /**
   * Get the moment the window closes.
   *
   * @return The moment the window closes.
   */
  public abstract Date getClose();
}
