/*
 * Copyright (c) 2025 Roman Vidayko
 * www.linkedin.com/in/roman-vidayko
 */

package com.vidayko.mapper;

import com.vidayko.context.UserServiceContext;
import com.vidayko.model.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceMapper implements Mapper<UserServiceContext, UserService> {

  @Override
  public UserService map(UserServiceContext source) {
    return new UserService(
        source.getService().getServiceId(),
        source.getService().getServiceName(),
        source.getService().getServiceDescription());
  }

}
