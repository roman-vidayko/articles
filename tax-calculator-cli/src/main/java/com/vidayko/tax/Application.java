package com.vidayko.tax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.vidayko.tax.model.Operation;
import com.vidayko.tax.model.TaxRecord;
import com.vidayko.tax.logic.Calculator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Application {

  private static final ObjectMapper MAPPER = new ObjectMapper().configure(
      DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

  private final static Calculator calculator = new Calculator(0.20, 20000);

  public static void main(String[] args) throws Exception {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
      reader.lines().filter(line -> !line.trim().isEmpty()).map(Application::processLine)
          .forEach(Application::outputLine);
      System.out.flush();
    }
  }

  private static List<TaxRecord> processLine(String cu) {
    final List<Operation> operations;
    try {
      operations = MAPPER.readValue(cu, new TypeReference<>() {
      });
      return calculator.calculate(operations);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private static void outputLine(List<TaxRecord> taxes) {
    try {
      System.out.println(MAPPER.writeValueAsString(taxes).replace("\":", "\": "));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}
