package com.vidayko.tax.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.*;

@NoArgsConstructor
public class TaxRecord {

  @Getter
  private BigDecimal tax;

  public TaxRecord(BigDecimal tax) {
    this.tax = tax == null
        ? null
        : tax.setScale(1, RoundingMode.HALF_UP);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TaxRecord)) return false;

    final TaxRecord other = (TaxRecord) o;
    return  (this.tax == null && other.tax == null)
        || (this.tax != null && other.tax != null
        && this.tax.compareTo(other.tax) == 0);
  }

  @Override
  public int hashCode() {
    return tax == null ? 0 : tax.stripTrailingZeros().hashCode();
  }
}