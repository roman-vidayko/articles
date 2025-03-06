/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.junk.nomapper;

import com.vidayko.model.Agreement;
import com.vidayko.model.UserService;
import com.vidayko.model.external.customerapi.Customer;
import com.vidayko.model.external.customerapi.CustomerAgreement;
import com.vidayko.model.external.customerapi.CustomerService;
import com.vidayko.service.CustomerApiAdapter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerApiServiceNoMapper {

  @Autowired
  private CustomerApiAdapter customerApiAdapter;

  List<UserService> retrieveServices(Agreement agreement) {

    final CustomerAgreement customerAgreement = new CustomerAgreement(
        agreement.getUser().getUserId(),
        agreement.getAgreementId(),
        agreement.getAgreementDate());

    final Customer customer = new Customer(
        agreement.getUser().getUserName(),
        agreement.getUser().getFirstName(),
        agreement.getUser().getLastName(),
        agreement.getUser().getDob(),
        customerAgreement);

    // Calling remote API
    final List<CustomerService> customerServices = customerApiAdapter.getCustomerServices(
        customer);

    // Mapping to local datatype from remote API datatype
    return customerServices.stream()
        .map(service -> new UserService(
                  service.getServiceId(),
                  service.getServiceName(),
                  service.getServiceDescription())
            )
        .collect(Collectors.toList());
  }
}
