/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.repository;

import com.vidayko.model.Agreement;
import java.util.List;

public interface PeopleRepository {

  List<Agreement> agreements(String userId);
}
