/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.service;

import com.vidayko.context.UserServiceContextPojo;
import com.vidayko.context.external.customerapi.CustomerContextPojo;
import com.vidayko.mapper.MapperLocator;
import com.vidayko.model.*;
import com.vidayko.model.external.customerapi.Customer;
import com.vidayko.model.external.customerapi.CustomerService;
import com.vidayko.repository.PeopleRepository;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerApiService {

  @Autowired
  private MapperLocator mapperLocator;

  @Autowired
  private PeopleRepository peopleRepository;

  @Autowired
  private CustomerApiAdapter customerApiAdapter;

  public List<UserService> getServices(String userId) {
    final List<Agreement> agreements = peopleRepository.agreements(userId);
    return agreements.stream().map(this::retrieveServices).flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  List<UserService> retrieveServices(Agreement agreement) {

    // Mapping to remote API datatype
    final Customer customer = mapperLocator.map(new CustomerContextPojo(agreement));

    // Calling remote API
    final List<CustomerService> customerServices = customerApiAdapter.getCustomerServices(
        customer);

    // Mapping to local datatype from remote API datatype
    return customerServices.stream()
        .map(service ->
            (UserService) mapperLocator.map(new UserServiceContextPojo(service)))
        .collect(Collectors.toList());

  }
}
