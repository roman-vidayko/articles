/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.service;

import com.vidayko.model.external.customerapi.Customer;
import com.vidayko.model.external.customerapi.CustomerService;
import java.util.List;

public interface CustomerApiAdapter {

  List<CustomerService> getCustomerServices(Customer customer);
}
