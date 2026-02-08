package com.vidayko.tax.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class Operation {

  @JsonProperty("operation")
  private OperationType type;

  @JsonProperty("unit-cost")
  private BigDecimal cost;

  private long quantity;

  public boolean isBuy() {
    return type.equals(OperationType.BUY);
  }

  public boolean isSell() {
    return type.equals(OperationType.SELL);
  }
}
