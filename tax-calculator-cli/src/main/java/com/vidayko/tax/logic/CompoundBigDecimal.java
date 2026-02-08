package com.vidayko.tax.logic;

import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Objects;
import lombok.Getter;

public class CompoundBigDecimal implements Comparable<CompoundBigDecimal> {

  @JsonValue
  @Getter
  private BigDecimal value;

  private final int SCALE = 2;

  private final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

  public static final CompoundBigDecimal ZERO = new CompoundBigDecimal(new BigDecimal("0.0")).scale();

  public CompoundBigDecimal(String literal) {
    this.value = new BigDecimal(literal);
    scale();
  }

  public CompoundBigDecimal(BigDecimal value) {
    this.value = value;
    scale();
  }

  public CompoundBigDecimal(long value) {
    this.value = BigDecimal.valueOf(value);
    scale();
  }

  public CompoundBigDecimal(double value) {
    this.value = BigDecimal.valueOf(value);
    scale();
  }

  private CompoundBigDecimal scale() {
    value.setScale(SCALE, ROUNDING_MODE);
    return this;
  }

  public CompoundBigDecimal add(CompoundBigDecimal other) {
    return new CompoundBigDecimal(this.value.add(other.value)).scale();
  }

  public CompoundBigDecimal subtract(CompoundBigDecimal other) {
    return new CompoundBigDecimal(this.value.subtract(other.value)).scale();
  }

  public CompoundBigDecimal multiply(CompoundBigDecimal other) {
    return new CompoundBigDecimal(this.value.multiply(other.value)).scale();
  }

  public CompoundBigDecimal divide(CompoundBigDecimal other) {
    return new CompoundBigDecimal(this.value.divide(other.value, SCALE, ROUNDING_MODE)).scale();
  }

  public CompoundBigDecimal abs() {
    return new CompoundBigDecimal(this.value.abs());
  }

  @Override
  public int compareTo(CompoundBigDecimal other) {
    return Comparator.<BigDecimal>naturalOrder().compare(this.value, other.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CompoundBigDecimal)) return false;

    final CompoundBigDecimal other = (CompoundBigDecimal) o;
    return  (this.value == null && other.value == null)
        || (this.value != null && other.value != null
        && this.value.compareTo(other.value) == 0);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

}
