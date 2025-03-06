/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.junk.withmapper;

import com.vidayko.model.Agreement;
import com.vidayko.model.UserService;
import com.vidayko.model.external.customerapi.Customer;
import com.vidayko.model.external.customerapi.CustomerService;
import com.vidayko.service.CustomerApiAdapter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerApiServiceWithMapper {

  @Autowired
  private CustomerApiAdapter customerApiAdapter;

  List<UserService> retrieveServices(Agreement agreement) {

    // Mapping to remote API datatype
    final Customer customer = StaticMapper.mapCustomer(agreement);

    // Calling remote API
    final List<CustomerService> customerServices = customerApiAdapter.getCustomerServices(
        customer);

    // Mapping to local datatype from remote API datatype
    return customerServices.stream()
        .map(StaticMapper::mapUserService)
        .collect(Collectors.toList());
  }
}
