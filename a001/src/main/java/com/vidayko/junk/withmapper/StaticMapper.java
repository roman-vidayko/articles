/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.junk.withmapper;

import com.vidayko.model.Agreement;
import com.vidayko.model.UserService;
import com.vidayko.model.external.customerapi.Customer;
import com.vidayko.model.external.customerapi.CustomerAgreement;
import com.vidayko.model.external.customerapi.CustomerService;

public class StaticMapper {

  public static CustomerAgreement mapCustomerAgreement(Agreement agreement) {
    return new CustomerAgreement(
        agreement.getUser().getUserId(),
        agreement.getAgreementId(),
        agreement.getAgreementDate());
  }

  public static Customer mapCustomer(Agreement agreement){
    return new Customer(
        agreement.getUser().getUserName(),
        agreement.getUser().getFirstName(),
        agreement.getUser().getLastName(),
        agreement.getUser().getDob(),
        mapCustomerAgreement(agreement));
  }

  public static UserService mapUserService(CustomerService service) {
    return new UserService(
        service.getServiceId(),
        service.getServiceName(),
        service.getServiceDescription());
  }
}
