package com.vidayko.tax.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationType {

  BUY("buy"), SELL("sell");

  @JsonValue
  String stringValue;

  OperationType(String stringValue) {
    this.stringValue = stringValue;
  }
}