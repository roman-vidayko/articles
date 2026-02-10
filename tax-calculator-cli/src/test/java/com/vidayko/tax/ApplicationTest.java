package com.vidayko.tax;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import java.io.*;

import static com.vidayko.TestUtils.readBytesFromFile;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

  @Test
  void integration_test() throws Exception {

    byte[] inputBytes = readBytesFromFile(this.getClass(),
        "testcases/com/vidayko/tax/ApplicationTest/input.txt");

    byte[] expectedBytes = readBytesFromFile(this.getClass(),
        "testcases/com/vidayko/tax/ApplicationTest/output.txt");

    InputStream originalIn = System.in;
    PrintStream originalOut = System.out;

    ByteArrayInputStream in = new ByteArrayInputStream(inputBytes);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    System.setIn(in);
    System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));

    try {
      Application.main(new String[0]);
    } finally {
      System.setIn(originalIn);
      System.setOut(originalOut);
    }

    byte[] actualBytes = out.toByteArray();
    assertArrayEquals(expectedBytes, actualBytes);
  }

}
