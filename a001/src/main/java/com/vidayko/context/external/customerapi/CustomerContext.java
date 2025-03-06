/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.context.external.customerapi;

import com.vidayko.mapper.Source;
import com.vidayko.model.*;

public interface CustomerContext extends Source {

  Agreement getAgreement();
}
