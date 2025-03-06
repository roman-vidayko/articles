/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.context;

import com.vidayko.mapper.Source;
import com.vidayko.model.external.customerapi.CustomerService;

public interface UserServiceContext extends Source {

  CustomerService getService();
}
