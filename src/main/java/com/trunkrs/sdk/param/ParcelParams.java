package com.trunkrs.sdk.param;

import lombok.Builder;
import lombok.Getter;

@Builder
public class ParcelParams {
  String reference;
  String description;
  int weight;
  int width;
  int height;
  int depth;
}
