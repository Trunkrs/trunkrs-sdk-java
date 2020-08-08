package com.trunkrs.sdk.param;

import lombok.Builder;

@Builder
public class ParcelParams {
  String reference;
  String description;
  int weight;
  int width;
  int height;
  int depth;
}
