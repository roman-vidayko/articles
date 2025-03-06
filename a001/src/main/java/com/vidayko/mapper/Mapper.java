/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.mapper;

/**
 * @param <Source>> Source
 * @param <T>       Target
 */
public interface Mapper<Source, T> {

  T map(Source source);
}
