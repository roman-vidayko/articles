/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpringMapperLocator implements MapperLocator {

  private final Map<Class<Source>, Mapper<? extends Source, ?>> mappers = new HashMap<>();

  @Autowired
  public SpringMapperLocator(List<Mapper<? extends Source, ?>> mappers) {
    mappers.forEach(this::register);
  }

  private void register(Mapper<? extends Source, ?> mapper) {
    mappers.put(getSourceType(mapper), mapper);
  }

  @Override
  public <T> T map(Source source) {
    Optional<Mapper<Source, T>> mapper = locateMapper(source);
    if (mapper.isEmpty()) {
      throw new IllegalArgumentException("No mapper available for " + source.getClass());
    }
    return mapper.get().map(source);
  }

  private <T> Optional<Mapper<Source, T>> locateMapper(Source source) {
    final Optional<Class<?>> sourceInterface = Arrays.stream(source.getClass().getInterfaces())
        .findFirst();
    if (sourceInterface.isPresent()) {
      if (mappers.containsKey(sourceInterface.get())) {
        return Optional.ofNullable((Mapper<Source, T>) mappers.get(sourceInterface.get()));
      } else {
        return Optional.ofNullable((Mapper<Source, T>) mappers.get(source.getClass()));
      }
    }
    return Optional.empty();
  }

  @SuppressWarnings("unchecked")
  private Class<Source> getSourceType(Mapper<? extends Source, ?> mapper) {
    Type[] interfaces = mapper.getClass().getGenericInterfaces();
    for (Type type : interfaces) {
      if (type instanceof ParameterizedType parameterizedType) {
        return (Class<Source>) parameterizedType.getActualTypeArguments()[0];
      }
    }
    throw new IllegalStateException("The source type is not supported");
  }
}
