package com.vidayko;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_ORDER_MAP_BY_INCOMPARABLE_KEY;
import static com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.*;

public class TestUtils {

  private final static ObjectMapper mapper = new ObjectMapper()
      .findAndRegisterModules()
      .configure(FAIL_ON_IGNORED_PROPERTIES, false)
      .configure(ALLOW_SINGLE_QUOTES, true)
      .configure(ORDER_MAP_ENTRIES_BY_KEYS, true)
      .configure(SORT_PROPERTIES_ALPHABETICALLY, true)
      .disable(FAIL_ON_EMPTY_BEANS)
      .disable(FAIL_ON_ORDER_MAP_BY_INCOMPARABLE_KEY)
      .setSerializationInclusion(NON_NULL);

  public static <T> String readFromFile(Class<T> type, String path) throws IOException {
    return readFromFile(type.getClassLoader(), path);
  }

  public static String readFromFile(ClassLoader classLoader, String path) throws IOException {
    return IOUtils.toString(
        Objects.requireNonNull(classLoader.getResourceAsStream(path)),
        StandardCharsets.UTF_8);
  }

  public static byte[] readBytesFromFile(Class<?> clazz, String path) throws IOException {
    try (InputStream is = clazz.getClassLoader().getResourceAsStream(path)) {
      return is.readAllBytes();
    }
  }

  public static <T> T[] readJson(Class<T[]> clazz, String pathString) throws IOException {
    return mapper.readValue(readFromFile(clazz, pathString), clazz);
  }
}
