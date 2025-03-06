/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.mapper;

public interface MapperLocator {

  <T> T map(Source source);
}
