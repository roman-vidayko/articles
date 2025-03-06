/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.service;


import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;
import static com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidayko.model.Agreement;
import com.vidayko.model.UserService;
import com.vidayko.model.external.customerapi.CustomerService;
import com.vidayko.repository.PeopleRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerApiServiceTest {

  private static final ObjectMapper mapper = new ObjectMapper()
      .configure(FAIL_ON_IGNORED_PROPERTIES, false)
      .configure(ALLOW_SINGLE_QUOTES, true)
      .configure(ORDER_MAP_ENTRIES_BY_KEYS, true)
      .configure(SORT_PROPERTIES_ALPHABETICALLY, true)
      .setSerializationInclusion(NON_NULL);

  @Autowired
  private CustomerApiService fixture;

  @MockitoBean
  private PeopleRepository peopleRepository;

  @MockitoBean
  private CustomerApiAdapter customerApiAdapter;

  @Test
  public void retrieveServices_test() throws IOException {

    final RetrieveServicesTestCase[] testcases = mapper.reader().readValue(
        stringOf(
            "testcases/com/vidayko/service/CustomerApiServiceTest/RetrieveServicesTestCases.json"),
        RetrieveServicesTestCase[].class);

    for (RetrieveServicesTestCase testCase : testcases) {

      doReturn(testCase.customerServices).when(customerApiAdapter).getCustomerServices(any());
      Assertions.assertEquals(testCase.expected, fixture.retrieveServices(testCase.agreement));

    }
  }

  @Data
  static class RetrieveServicesTestCase {

    List<CustomerService> customerServices;
    Agreement agreement;
    List<UserService> expected;
  }

  public String stringOf(String path) throws IOException {
    return IOUtils.toString(
        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(path)),
        StandardCharsets.UTF_8);
  }

}