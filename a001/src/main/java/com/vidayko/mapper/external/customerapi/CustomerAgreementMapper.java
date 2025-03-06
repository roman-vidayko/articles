/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.mapper.external.customerapi;

import com.vidayko.context.external.customerapi.CustomerAgreementContext;
import com.vidayko.mapper.Mapper;
import com.vidayko.model.external.customerapi.CustomerAgreement;
import org.springframework.stereotype.Service;

@Service
public class CustomerAgreementMapper implements
    Mapper<CustomerAgreementContext, CustomerAgreement> {

  @Override
  public CustomerAgreement map(CustomerAgreementContext source) {
    return new CustomerAgreement(
        source.getAgreement().getUser().getUserId(),
        source.getAgreement().getAgreementId(),
        source.getAgreement().getAgreementDate());
  }

}
