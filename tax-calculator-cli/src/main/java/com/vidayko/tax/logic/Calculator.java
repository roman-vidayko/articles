package com.vidayko.tax.logic;

import com.vidayko.tax.model.Operation;
import com.vidayko.tax.model.TaxRecord;
import java.util.*;

public class Calculator {

  private final CompoundBigDecimal taxRate;
  private final CompoundBigDecimal taxFreeThreshold;
  private CompoundBigDecimal quantityHeld;
  private CompoundBigDecimal weightedAveragePrice;
  private CompoundBigDecimal accumulatedLoss;

  public Calculator(double taxRate, long taxFreeThreshold) {
    this.taxRate = new CompoundBigDecimal(taxRate);
    this.taxFreeThreshold = new CompoundBigDecimal(taxFreeThreshold);
    init();
  }

  private void init() {
    quantityHeld = CompoundBigDecimal.ZERO;
    weightedAveragePrice = CompoundBigDecimal.ZERO;
    accumulatedLoss = CompoundBigDecimal.ZERO;
  }

  public synchronized List<TaxRecord> calculate(List<Operation> operations) {
    init();
    List<TaxRecord> results = new ArrayList<>();
    for (Operation operation : operations) {
      if (operation.isBuy()) {
        results.add(new TaxRecord(handleBuy(operation).getValue()));
      } else if (operation.isSell()) {
        results.add(new TaxRecord(handleSell(operation).getValue()));
      }
    }
    return results;
  }

  private CompoundBigDecimal handleBuy(Operation op) {
    final CompoundBigDecimal price = new CompoundBigDecimal(op.getCost());
    final CompoundBigDecimal quantity = new CompoundBigDecimal(op.getQuantity());
    updateWeightedAveragePrice(quantity, price);
    quantityHeld = quantityHeld.add(quantity);

    return CompoundBigDecimal.ZERO;
  }

  private CompoundBigDecimal handleSell(Operation op) {
    final CompoundBigDecimal quantity = new CompoundBigDecimal(op.getQuantity());
    final CompoundBigDecimal price = new CompoundBigDecimal(op.getCost());

    final CompoundBigDecimal totalAmount = price.multiply(quantity);
    final CompoundBigDecimal profitOrLoss = price.subtract(weightedAveragePrice).multiply(quantity);

    final CompoundBigDecimal taxForOperation = calculateTaxForSell(totalAmount, profitOrLoss);

    quantityHeld = quantityHeld.subtract(quantity);
    if (quantityHeld.equals(CompoundBigDecimal.ZERO)) {
      weightedAveragePrice = CompoundBigDecimal.ZERO;
    }

    return taxForOperation;
  }

  private void updateWeightedAveragePrice(
      CompoundBigDecimal newQuantity,
      CompoundBigDecimal newPrice
  ) {
    if (quantityHeld.equals(CompoundBigDecimal.ZERO)) {
      weightedAveragePrice = newPrice;
    }

    final CompoundBigDecimal currentTotalCost = weightedAveragePrice.multiply(quantityHeld);
    final CompoundBigDecimal newTotalCost = newPrice.multiply(newQuantity);
    final CompoundBigDecimal combinedQuantity = quantityHeld.add(newQuantity);

    weightedAveragePrice = currentTotalCost.add(newTotalCost).divide(combinedQuantity);
  }

  private CompoundBigDecimal calculateTaxForSell(
      CompoundBigDecimal totalAmount,
      CompoundBigDecimal profitOrLoss
  ) {

    if (profitOrLoss.compareTo(CompoundBigDecimal.ZERO) < 0) {
      accumulatedLoss = accumulatedLoss.add(profitOrLoss.abs());
      return CompoundBigDecimal.ZERO;
    }

    if (profitOrLoss.compareTo(CompoundBigDecimal.ZERO) == 0) {
      return CompoundBigDecimal.ZERO;
    }

    if (totalAmount.compareTo(taxFreeThreshold) <= 0) {
      return CompoundBigDecimal.ZERO;
    }

    if (accumulatedLoss.compareTo(CompoundBigDecimal.ZERO) <= 0) {
      return profitOrLoss.multiply(taxRate);
    } else if (accumulatedLoss.compareTo(profitOrLoss) >= 0) {
      accumulatedLoss = accumulatedLoss.subtract(profitOrLoss);
      return CompoundBigDecimal.ZERO;
    } else {
      CompoundBigDecimal taxableProfit = profitOrLoss.subtract(accumulatedLoss);
      accumulatedLoss = CompoundBigDecimal.ZERO;
      return taxableProfit.multiply(taxRate);
    }
  }

}
