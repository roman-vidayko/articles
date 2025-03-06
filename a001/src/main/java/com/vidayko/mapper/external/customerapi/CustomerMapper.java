/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.mapper.external.customerapi;

import com.vidayko.context.external.customerapi.CustomerAgreementContextPojo;
import com.vidayko.context.external.customerapi.CustomerContext;
import com.vidayko.mapper.Mapper;
import com.vidayko.mapper.MapperLocator;
import com.vidayko.model.Agreement;
import com.vidayko.model.external.customerapi.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper implements Mapper<CustomerContext, Customer> {

  @Lazy
  @Autowired
  private MapperLocator mapperLocator;

  @Override
  public Customer map(CustomerContext source) {
    final Agreement agreement = source.getAgreement();
    return new Customer(
        agreement.getUser().getUserName(),
        agreement.getUser().getFirstName(),
        agreement.getUser().getLastName(),
        agreement.getUser().getDob(),
        mapperLocator.map(new CustomerAgreementContextPojo(agreement)));
  }
}
