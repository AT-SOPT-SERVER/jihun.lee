package org.sopt.global.common.cache.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.sopt.global.common.exception.BaseException;

public class CacheNotFoundException extends BaseException {
  public CacheNotFoundException() {
    super(NOT_FOUND, ErrorMessage.CACHE_NOT_FOUND.getMessage());
  }
}
