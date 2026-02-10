package com.vidayko.tax.logic;

import com.vidayko.TestUtils;
import com.vidayko.tax.model.Operation;
import com.vidayko.tax.model.TaxRecord;
import java.io.IOException;
import java.util.ArrayList;
import lombok.Data;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.assertj.core.api.Assertions;

class CalculatorTest {

  @Test
  void calculate_test() throws IOException {
    final CalculateTestCase[] testcases = TestUtils.readJson(CalculateTestCase[].class,
        "testcases/com/vidayko/tax/logic/CalculatorTest/CalculateTestCases.json");

    for (CalculateTestCase testCase : testcases) {
      final Calculator calculator = new Calculator(testCase.taxRate, testCase.taxFreeThreshold);
      List<List<TaxRecord>> actual = new ArrayList<>();
      for (List<Operation> batch : testCase.input) {
        actual.add(calculator.calculate(batch));
      }
      Assertions.assertThat(actual)
          .usingRecursiveComparison()
          .isEqualTo(testCase.expected);
    }
  }

  @Data
  static class CalculateTestCase {
    String id;
    double taxRate;
    long taxFreeThreshold;
    List<List<Operation>> input;
    List<List<TaxRecord>> expected;
  }
}
